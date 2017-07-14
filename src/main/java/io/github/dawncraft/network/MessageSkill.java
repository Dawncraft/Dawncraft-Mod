package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMana;
import io.github.dawncraft.magic.SkillLoader;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author QingChenW
 *
 */
public class MessageSkill implements IMessage
{
	public int skillId;
	
    public MessageSkill(int id)
    {
    	skillId = id;
	}
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
    	skillId = ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf, skillId);
    }
    
    public static class Handler implements IMessageHandler<MessageSkill, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageSkill message, MessageContext ctx)
        {
            if(ctx.side == Side.SERVER)
            {
                final EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
                serverPlayer.getServerForPlayer().addScheduledTask(new Runnable()
                {
                	@Override
                    public void run()
                	{
                		boolean result = SkillLoader.heal.spellMagic(serverPlayer.getHeldItem(), serverPlayer.getEntityWorld(), serverPlayer);
                		if(result)
                		{
                			serverPlayer.addChatMessage(new ChatComponentTranslation("magic.yes", I18n.format("magic.heal.name")));
                		}
                		else
                		{
                			serverPlayer.addChatMessage(new ChatComponentTranslation("magic.no", I18n.format("magic.heal.name")));
                		}
                    }
                });
            }
            return null;
        }
    }
}
