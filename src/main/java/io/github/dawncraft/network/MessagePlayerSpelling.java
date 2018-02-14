package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.skill.EnumSpellResult;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 处理玩家施法动作
 *
 * @author QingChenW
 */
public class MessagePlayerSpelling implements IMessage
{
    private EnumSpellResult spellAction;
    private int spellCount;
    private int cooldownCount;
    
    public MessagePlayerSpelling() {}
    
    public MessagePlayerSpelling(EnumSpellResult type, int count, int cooldown)
    {
        this.spellAction = type;
        this.spellCount = count;
        this.cooldownCount = cooldown;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.spellAction = EnumSpellResult.values()[buf.readShort()];
        this.spellCount = buf.readInt();
        this.cooldownCount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeShort(this.spellAction.ordinal());
        buf.writeInt(this.spellCount);
        buf.writeInt(this.cooldownCount);
    }
    
    public static class Handler implements IMessageHandler<MessagePlayerSpelling, IMessage>
    {
        @Override
        public IMessage onMessage(final MessagePlayerSpelling message, MessageContext ctx)
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
                            IMagic magic = player.getCapability(CapabilityLoader.magic, null);
                            magic.setSpellAction(message.spellAction);
                            magic.setSkillInSpellCount(message.spellCount);
                            magic.setPublicCooldownCount(message.cooldownCount);
                        }
                    }
                });
            }
            return null;
        }
    }
}
