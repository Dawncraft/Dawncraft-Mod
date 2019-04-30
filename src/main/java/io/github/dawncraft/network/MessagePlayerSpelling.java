package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
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

    public MessagePlayerSpelling() {}

    public MessagePlayerSpelling(EnumSpellAction type, int count)
    {
        this.spellAction = type;
        this.spellCount = count;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.spellAction = EnumSpellAction.values()[buf.readByte()];
        this.spellCount = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(this.spellAction.ordinal());
        buf.writeInt(this.spellCount);
    }

    public static class Handler implements IMessageHandler<MessagePlayerSpelling, IMessage>
    {
        @Override
        public IMessage onMessage(final MessagePlayerSpelling message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.playerMagic, null);
                        if (message.spellAction != EnumSpellAction.NONE)
                        {
                            playerMagic.setSpellAction(message.spellAction);
                            playerMagic.setSkillInSpellCount(message.spellCount);
                        }
                        else
                        {
                            playerMagic.clearSkillInSpell();
                        }
                    }
                });
            }
            return null;
        }
    }
}
