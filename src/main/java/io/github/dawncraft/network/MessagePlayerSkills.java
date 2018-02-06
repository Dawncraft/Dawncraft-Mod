package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.container.SkillInventoryPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author QingChenW
 *
 */
public class MessagePlayerSkills implements IMessage
{
    public NBTTagCompound nbt = new NBTTagCompound();
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.nbt = ByteBufUtils.readTag(buf);
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, this.nbt);
    }
    
    public static class Handler implements IMessageHandler<MessagePlayerSkills, IMessage>
    {
        @Override
        public IMessage onMessage(final MessagePlayerSkills message, MessageContext ctx)
        {
            if(ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                        if (player.hasCapability(CapabilityLoader.magic, null))
                        {
                            SkillInventoryPlayer inventory = (SkillInventoryPlayer) player.getCapability(CapabilityLoader.magic, null).getInventory();
                            inventory.readFromNBT(message.nbt.getTagList("Skills", 9));
                        }
                    }
                });
            }
            return null;
        }
    }
}
