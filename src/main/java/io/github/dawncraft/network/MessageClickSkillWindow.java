package io.github.dawncraft.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.IntHashMap;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.container.SkillContainer;
import io.github.dawncraft.skill.SkillStack;
import io.netty.buffer.ByteBuf;

public class MessageClickSkillWindow implements IMessage
{
    /** The id of the window which was clicked. 0 for player inventory. */
    private int windowId;
    /** Id of the clicked slot */
    private int slotId;
    /** Button used */
    private int usedButton;
    /** A unique number for the action, used for transaction handling */
    private short actionNumber;
    /** The skill stack present in the slot */
    private SkillStack clickedSkill;
    /** Inventory operation mode */
    private int mode;
    
    public MessageClickSkillWindow() {}

    public MessageClickSkillWindow(int windowId, int slotId, int usedButton, int mode, SkillStack clickedSkill, short actionNumber)
    {
        this.windowId = windowId;
        this.slotId = slotId;
        this.usedButton = usedButton;
        this.clickedSkill = clickedSkill != null ? clickedSkill.copy() : null;
        this.actionNumber = actionNumber;
        this.mode = mode;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.windowId = buf.readByte();
        this.slotId = buf.readShort();
        this.usedButton = buf.readByte();
        this.actionNumber = buf.readShort();
        this.mode = buf.readByte();
        this.clickedSkill = DawnByteBufUtils.readSkillStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(this.windowId);
        buf.writeShort(this.slotId);
        buf.writeByte(this.usedButton);
        buf.writeShort(this.actionNumber);
        buf.writeByte(this.mode);
        DawnByteBufUtils.writeSkillStack(buf, this.clickedSkill);
    }

    public static class Handler implements IMessageHandler<MessageClickSkillWindow, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageClickSkillWindow message, MessageContext ctx)
        {
            if (ctx.side == Side.SERVER)
            {
                final EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
                serverPlayer.getServerForPlayer().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        IPlayerMagic playerMagic = serverPlayer.getCapability(CapabilityLoader.playerMagic, null);
                        serverPlayer.markPlayerActive();
                        
                        if (!(serverPlayer.openContainer instanceof SkillContainer)) return;
                        SkillContainer container = (SkillContainer) serverPlayer.openContainer;
                        
                        if (container.windowId == message.windowId && container.getCanCraft(serverPlayer))
                        {
                            if (serverPlayer.isSpectator())
                            {
                                List<SkillStack> list = Lists.<SkillStack>newArrayList();
                                for (int i = 0; i < container.inventorySkillSlots.size(); ++i)
                                {
                                    list.add(container.inventorySkillSlots.get(i).getStack());
                                }
                                playerMagic.updateLearningInventory(container, list);
                            }
                            else
                            {
                                SkillStack skillStack = container.skillSlotClick(message.slotId, message.usedButton, message.mode, serverPlayer);
                                
                                if (SkillStack.areSkillStacksEqual(message.clickedSkill, skillStack))
                                {
                                    serverPlayer.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(message.windowId, message.actionNumber, true));
                                    container.detectAndSendChanges();
                                }
                                else
                                {
                                    try
                                    {
                                        Field field = ReflectionHelper.findField(NetHandlerPlayServer.class, "field_147372_n", "field_147372_n");
                                        field.setAccessible(true);
                                        IntHashMap<Short> uidMap = (IntHashMap<Short>) field.get(serverPlayer.playerNetServerHandler);
                                        uidMap.addKey(container.windowId, message.actionNumber);
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                        return;
                                    }
                                    serverPlayer.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(message.windowId, message.actionNumber, false));
                                    serverPlayer.openContainer.setCanCraft(serverPlayer, false);
                                    List<SkillStack> list = Lists.<SkillStack>newArrayList();
                                    
                                    for (int j = 0; j < container.inventorySkillSlots.size(); ++j)
                                    {
                                        list.add(container.inventorySkillSlots.get(j).getStack());
                                    }
                                    
                                    playerMagic.updateLearningInventory(container, list);
                                }
                            }
                        }
                    }
                });
            }
            return null;
        }
    }
}
