package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.container.SkillContainer;
import io.github.dawncraft.skill.SkillStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Handles pick in up a SkillStack or dropping one in your inventory or an open (non-creative) container
 *
 * @author QingChenW
 */
public class MessageSetSkillSlot implements IMessage
{
    private int windowId;
    private int slot;
    private SkillStack skillStack;

    public MessageSetSkillSlot() {}

    public MessageSetSkillSlot(int windowId, int slot, SkillStack stack)
    {
        this.windowId = windowId;
        this.slot = slot;
        this.skillStack = stack == null ? null : stack.copy();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.windowId = buf.readByte();
        this.slot = buf.readShort();
        this.skillStack = DawnByteBufUtils.readSkillStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(this.windowId);
        buf.writeShort(this.slot);
        DawnByteBufUtils.writeSkillStack(buf, this.skillStack);
    }

    public static class Handler implements IMessageHandler<MessageSetSkillSlot, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageSetSkillSlot message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().player;
                        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);

                        if (message.windowId == -1)
                        {
                            playerMagic.getSkillInventory().setSkillStack(message.skillStack);
                        }
                        else
                        {
                            boolean flag = false;

                            if (Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative)
                            {
                                /* 判断是不是创造物品栏
                                GuiContainerCreative guicontainercreative = (GuiContainerCreative) Minecraft.getMinecraft().currentScreen;
                                flag = guicontainercreative.getSelectedTabIndex() != CreativeTabs.tabInventory.getTabIndex();
                                 */
                            }

                            if (message.windowId == 0)
                            {
                                SkillStack stack = playerMagic.getSkillInventoryContainer().getSkillSlot(message.slot).getStack();

                                if (message.slot >= 0 && message.slot < 9)
                                {
                                    if (message.skillStack != null && (stack == null || stack.getLevel() < message.skillStack.getLevel()))
                                    {
                                        message.skillStack.animationsToGo = 5;
                                    }
                                }

                                playerMagic.getSkillInventoryContainer().putSkillStackInSlot(message.slot, message.skillStack);
                            }
                            else if (player.openContainer instanceof SkillContainer && message.windowId == player.openContainer.windowId && !flag)
                            {
                                ((SkillContainer) player.openContainer).putSkillStackInSlot(message.slot, message.skillStack);
                            }
                        }
                    }
                });
            }
            return null;
        }
    }
}
