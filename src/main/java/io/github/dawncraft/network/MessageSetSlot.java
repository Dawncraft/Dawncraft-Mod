package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.SkillStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Handles pickin up an SkillStack or dropping one in your inventory or an open (non-creative) container
 *
 * @author QingChenW
 */
public class MessageSetSlot implements IMessage
{
    private int windowId;
    private int slot;
    private SkillStack skillStack;

    public MessageSetSlot() {}
    
    public MessageSetSlot(int windowId, int slot, SkillStack skillStack)
    {
        this.windowId = windowId;
        this.slot = slot;
        this.skillStack = skillStack == null ? null : skillStack.copy();
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
    
    public static class Handler implements IMessageHandler<MessageSetSlot, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageSetSlot message, MessageContext ctx)
        {
            if(ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                        if(player.hasCapability(CapabilityLoader.magic, null))
                        {
                            IMagic magic = player.getCapability(CapabilityLoader.magic, null);

                            if (message.windowId == -1)
                            {
                                ((SkillInventoryPlayer) magic.getInventory()).setSkillStack(message.skillStack);
                            }
                            else
                            {
                                if(message.windowId == 0)
                                {
                                    magic.getInventory().setInventorySlotContents(message.slot, message.skillStack);
                                }
                                // TODO SkillSlot和Container写完后重写这个
                                /*                                boolean flag = false;

                                if (Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative)
                                {
                                    GuiContainerCreative guicontainercreative = (GuiContainerCreative)Minecraft.getMinecraft().currentScreen;
                                    flag = guicontainercreative.getSelectedTabIndex() != CreativeTabs.tabInventory.getTabIndex();
                                }

                                if (message.windowId == 0 && message.slot >= 36 && message.slot < 45)
                                {
                                    ItemStack itemstack = player.inventoryContainer.getSlot(message.slot).getStack();

                                    if (message.skillStack != null && (itemstack == null || itemstack.stackSize < message.skillStack.stackSize))
                                    {
                                        message.skillStack.animationsToGo = 5;
                                    }

                                    player.inventoryContainer.putStackInSlot(message.slot, message.skillStack);
                                }
                                else if (message.windowId == player.openContainer.windowId && (message.slot != 0 || !flag))
                                {
                                    player.openContainer.putStackInSlot(message.slot, message.skillStack);
                                }*/
                            }
                        }
                    }
                });
            }
            return null;
        }
    }
}
