package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 用于更新玩家魔法值
 *
 * @author QingChenW
 */
public class MessageUpdateMana implements IMessage
{
    public float mana;

    // 默认的构造函数是必须的，我调试了半天才发现啊啊啊
    public MessageUpdateMana() {}
    
    public MessageUpdateMana(float amount)
    {
        this.mana = amount;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.mana = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeFloat(this.mana);
    }
    
    public static class Handler implements IMessageHandler<MessageUpdateMana, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageUpdateMana message, MessageContext ctx)
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
                            magic.setMana(message.mana);
                        }
                    }
                });
            }
            return null;
        }
    }
}
