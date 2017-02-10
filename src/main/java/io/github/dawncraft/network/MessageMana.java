package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMana;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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
public class MessageMana implements IMessage
{
    public NBTTagCompound nbt  = new NBTTagCompound();

    @Override
    public void fromBytes(ByteBuf buf)
    {
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, nbt);
    }
    
    public static class Handler implements IMessageHandler<MessageMana, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageMana message, MessageContext ctx)
        {
            if(ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                        if(player.hasCapability(CapabilityLoader.mana, null))
                        {
                            IMana mana = player.getCapability(CapabilityLoader.mana, null);
                            IStorage<IMana> storage = CapabilityLoader.mana.getStorage();
                            
                            storage.readNBT(CapabilityLoader.mana, mana, null, message.nbt);
                        }
                    }
                });
            }
            return null;
        }
    }
}
