package io.github.dawncraft.network;

import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.netty.buffer.ByteBuf;

public class MessageOpenSkillInventory implements IMessage
{
    public MessageOpenSkillInventory() {}
    
    @Override
    public void fromBytes(ByteBuf buf) {}
    
    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<MessageOpenSkillInventory, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageOpenSkillInventory message, MessageContext ctx)
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
                        serverPlayer.openContainer = playerMagic.getSkillInventoryContainer();
                    }
                });
            }
            return null;
        }
    }
}
