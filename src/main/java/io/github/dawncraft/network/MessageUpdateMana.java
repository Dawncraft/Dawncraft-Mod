package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.capability.IPlayerThirst;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Update player's mana and thirst to client
 *
 * @author QingChenW
 */
public class MessageUpdateMana implements IMessage
{
    private float mana;
    private int drinkLevel;
    private float saturationLevel;

    public MessageUpdateMana() {}
    
    public MessageUpdateMana(float mana)
    {
        this(mana, 0, 0.0F);
    }
    
    public MessageUpdateMana(float mana, int drinkLevel, float saturation)
    {
        this.mana = mana;
        this.drinkLevel = drinkLevel;
        this.saturationLevel = saturation;
    }
    
    @SideOnly(Side.CLIENT)
    public float getMana()
    {
        return this.mana;
    }

    @SideOnly(Side.CLIENT)
    public int getDrinkLevel()
    {
        return this.drinkLevel;
    }

    @SideOnly(Side.CLIENT)
    public float getSaturationLevel()
    {
        return this.saturationLevel;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.mana = buf.readFloat();
        this.drinkLevel = buf.readInt();
        this.saturationLevel = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeFloat(this.mana);
        buf.writeInt(this.drinkLevel);
        buf.writeFloat(this.saturationLevel);
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
                        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.playerMagic, null);
                        playerMagic.setMana(message.getMana());
                        IPlayerThirst playerThirst = player.getCapability(CapabilityLoader.playerThirst, null);
                        if (playerThirst.getDrinkStats() != null)
                        {
                            playerThirst.getDrinkStats().setDrinkLevel(message.getDrinkLevel());
                            playerThirst.getDrinkStats().setDrinkSaturationLevel(message.getSaturationLevel());
                        }
                    }
                });
            }
            return null;
        }
    }
}
