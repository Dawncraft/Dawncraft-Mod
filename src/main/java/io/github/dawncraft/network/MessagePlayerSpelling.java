package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.skill.EnumSpellAction;
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
    private EnumSpellAction spellAction;
    private int spellCount;
    //private int cooldownCount;

    public MessagePlayerSpelling() {}

    public MessagePlayerSpelling(EnumSpellAction type, int count)
    {
        this.spellAction = type;
        this.spellCount = count;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.spellAction = EnumSpellAction.values()[buf.readShort()];
        this.spellCount = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeShort(this.spellAction.ordinal());
        buf.writeInt(this.spellCount);
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
                        IMagic playerCap = player.getCapability(CapabilityLoader.magic, null);
                        playerCap.setSpellAction(message.spellAction);
                        playerCap.setSkillInSpellCount(message.spellCount);
                    }
                });
            }
            return null;
        }
    }
}
