package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.skill.Skill;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSpellCooldown implements IMessage
{
    private Skill skill;
    private int ticks;

    public MessageSpellCooldown() {}

    public MessageSpellCooldown(Skill skill, int ticks)
    {
        this.skill = skill;
        this.ticks = ticks;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.skill = Skill.getSkillById(buf.readInt());
        this.ticks = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(Skill.getIdFromSkill(this.skill));
        buf.writeInt(this.ticks);
    }
    
    public static class Handler implements IMessageHandler<MessageSpellCooldown, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageSpellCooldown message, MessageContext ctx)
        {
            if(ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                        if(player.hasCapability(CapabilityLoader.playerMagic, null))
                        {
                            IPlayerMagic playerCap = player.getCapability(CapabilityLoader.playerMagic, null);
                            playerCap.getCooldownTracker().setCooldown(message.skill, message.ticks);
                        }
                    }
                });
            }
            return null;
        }
    }
}
