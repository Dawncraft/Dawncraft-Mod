package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.SkillStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * When a player press a skill hotbar key, the message will be sent.
 *
 * @author QingChenW
 */
public class MessageSpellSkillChange implements IMessage
{
    public int slotId;

    public MessageSpellSkillChange() {}
    
    public MessageSpellSkillChange(int slot)
    {
        this.slotId = slot;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.slotId = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeShort(this.slotId);
    }
    
    public static class Handler implements IMessageHandler<MessageSpellSkillChange, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageSpellSkillChange message, MessageContext ctx)
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
                        if (message.slotId < 0)
                        {
                            playerMagic.clearSkillInSpell();
                        }
                        else if (message.slotId < SkillInventoryPlayer.getHotbarSize())
                        {
                            SkillStack skillStack = playerMagic.getSkillInventory().getStackInSlot(message.slotId);
                            if (skillStack != null)
                            {
                                playerMagic.setSkillInSpell(skillStack);
                                serverPlayer.markPlayerActive();
                            }
                        }
                        else
                        {
                            LogLoader.logger().warn(serverPlayer.getName() + " tried to set an invalid spelled skill.");
                        }
                    }
                });
            }
            else if (ctx.side == Side.CLIENT)
            {
                final EntityPlayerSP clientPlayer = Minecraft.getMinecraft().thePlayer;
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        IPlayerMagic playerMagic = clientPlayer.getCapability(CapabilityLoader.playerMagic, null);
                        if (message.slotId < 0)
                        {
                            playerMagic.clearSkillInSpell();
                            ClientProxy.getIngameGUIDawn().setSpellIndex(-1);
                        }
                        else if (message.slotId < SkillInventoryPlayer.getHotbarSize())
                        {
                            SkillStack skillStack = playerMagic.getSkillInventory().getStackInSlot(message.slotId);
                            if (skillStack != null)
                            {
                                playerMagic.setSkillInSpell(skillStack);
                                ClientProxy.getIngameGUIDawn().setSpellIndex(message.slotId);
                            }
                        }
                    }
                });
            }
            return null;
        }
    }
}
