package com.github.wdawning.dawncraft.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.github.wdawning.dawncraft.extend.ExtendedPlayer;

import io.netty.buffer.ByteBuf;

public class MessageMagic implements IMessage{
	
    public MessageMagic(){}
    
	//private NBTTagCompound data;
	public int mana;
	
	public MessageMagic(int amount)
	{
		this.mana = amount;
	}
	
	public MessageMagic(EntityPlayer entityPlayer,int amount)
	{
		this.mana = amount;
	}

    @Override
    public void fromBytes(ByteBuf buf) {
    	//mana = ByteBufUtils.readVarInt(buf, 20);
    	mana = buf.readInt();
    	//mana = buf.getInt(0);
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
    	//ByteBufUtils.writeVarInt(buf, mana, 20);
    	buf.writeInt(mana);
    	//buf.setInt(0, mana)
    }

    public static class MessageHandler implements IMessageHandler<MessageMagic, IMessage>
    {
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(final MessageMagic message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
    			final EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().thePlayer;
                
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
            			int mana = message.mana;
                    	ExtendedPlayer.get(player).setMana(mana);
                    }
                });
            }
            return null;
        }
    }
}
