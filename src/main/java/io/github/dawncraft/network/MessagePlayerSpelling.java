package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.client.event.GuiIngameDawn;
import io.github.dawncraft.entity.magicile.EnumSpellAction;
import io.github.dawncraft.skill.EnumSpellResult;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessagePlayerSpelling implements IMessage
{
    private EnumSpellAction spellAction;
    private int spellCount;
    private int cooldownCount;
    private EnumSpellResult tooltipType;
    
    public MessagePlayerSpelling() {}

    public MessagePlayerSpelling(EnumSpellAction action, int count, int cooldown, EnumSpellResult type)
    {
        this.spellAction = action;
        this.spellCount = count;
        this.cooldownCount = cooldown;
        this.tooltipType = type;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.spellAction = EnumSpellAction.values()[buf.readShort()];
        this.spellCount = buf.readInt();
        this.cooldownCount = buf.readInt();
        this.tooltipType = EnumSpellResult.values()[buf.readShort()];
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeShort(this.spellAction.ordinal());
        buf.writeInt(this.spellCount);
        buf.writeInt(this.cooldownCount);
        buf.writeShort(this.tooltipType.ordinal());
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
                            GuiIngameDawn.getIngameDawnGUI().spellType = message.tooltipType;
                        }
                    }
                });
            }
            return null;
        }
    }
}
