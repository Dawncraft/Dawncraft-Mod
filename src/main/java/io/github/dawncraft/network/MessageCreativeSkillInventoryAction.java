package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.skill.SkillStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageCreativeSkillInventoryAction implements IMessage
{
    public int slotId;
    public SkillStack stack;

    public MessageCreativeSkillInventoryAction() {}

    public MessageCreativeSkillInventoryAction(int slotId, SkillStack stack)
    {
        this.slotId = slotId;
        this.stack = stack != null ? stack.copy() : null;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.slotId = buf.readShort();
        this.stack = DawnByteBufUtils.readSkillStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeShort(this.slotId);
        DawnByteBufUtils.writeSkillStack(buf, this.stack);
    }

    public static class Handler implements IMessageHandler<MessageCreativeSkillInventoryAction, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageCreativeSkillInventoryAction message, MessageContext ctx)
        {
            if (ctx.side == Side.SERVER)
            {
                final EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
                serverPlayer.getServer().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        IPlayerMagic playerMagic = serverPlayer.getCapability(CapabilityLoader.PLAYER_MAGIC, null);

                        if (serverPlayer.interactionManager.isCreative() && message.slotId >= 0 && message.slotId < playerMagic.getSkillInventoryContainer().inventorySkillSlots.size())
                        {
                            SkillStack skillStack = message.stack;

                            if (skillStack == null)
                            {
                                playerMagic.getSkillInventoryContainer().putSkillStackInSlot(message.slotId, null);
                            }
                            else
                            {
                                playerMagic.getSkillInventoryContainer().putSkillStackInSlot(message.slotId, skillStack);
                            }

                            playerMagic.getSkillInventoryContainer().setCanCraft(serverPlayer, true);
                        }
                    }
                });
            }
            return null;
        }
    }
}
