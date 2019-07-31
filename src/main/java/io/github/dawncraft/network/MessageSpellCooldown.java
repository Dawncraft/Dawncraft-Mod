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
    private boolean isGlobal;
    private Skill skill;
    private int tick;

    public MessageSpellCooldown() {}

    /**
     * Send a global cooldown message
     *
     * @param tick
     */
    public MessageSpellCooldown(int tick)
    {
        this.isGlobal = true;
        this.tick = tick;
    }

    /**
     * Send a skill cooldown message
     *
     * @param skill
     * @param tick
     */
    public MessageSpellCooldown(Skill skill, int tick)
    {
        this.isGlobal = false;
        this.skill = skill;
        this.tick = tick;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.isGlobal = buf.readBoolean();
        if (!this.isGlobal)
        {
            this.skill = Skill.getSkillById(buf.readInt());
        }
        this.tick = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(this.isGlobal);
        if (!this.isGlobal)
        {
            buf.writeInt(Skill.getIdFromSkill(this.skill));
        }
        buf.writeInt(this.tick);
    }

    public static class Handler implements IMessageHandler<MessageSpellCooldown, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageSpellCooldown message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().player;
                        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.playerMagic, null);
                        if (message.isGlobal)
                        {
                            playerMagic.getCooldownTracker().setGlobalCooldown(message.tick);
                        }
                        else
                        {
                            if (message.tick > 0)
                            {
                                playerMagic.getCooldownTracker().setCooldown(message.skill, message.tick);
                            }
                            else
                            {
                                playerMagic.getCooldownTracker().removeCooldown(message.skill);
                            }
                        }
                    }
                });
            }
            return null;
        }
    }
}
