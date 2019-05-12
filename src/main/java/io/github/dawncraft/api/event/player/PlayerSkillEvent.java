package io.github.dawncraft.api.event.player;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

import io.github.dawncraft.skill.EnumSpellAction;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.player.EntityPlayer;

public abstract class PlayerSkillEvent extends PlayerEvent
{
    public EnumSpellAction action;
    public final SkillStack skill;
    public int duration;

    private PlayerSkillEvent(EntityPlayer player, EnumSpellAction action, SkillStack skill, int duration)
    {
        super(player);
        this.skill = skill;
        this.duration = duration;
    }

    /**
     * Fired when a player starts 'spelling' a skill, typically when they finish their preparations.
     *
     * Cancel the event, or set the duration or <= 0 to prevent it from processing.
     */
    @Cancelable
    public static class Start extends PlayerSkillEvent
    {
        public Start(EntityPlayer player, EnumSpellAction action, SkillStack skill, int duration)
        {
            super(player, action, skill, duration);
        }
    }

    /**
     * Fired every tick that a player is 'spelling' a skill, see {@link Spell.Start} for info.
     *
     * Cancel the event, or set the duration or <= 0 to cause the player to stop spelling the skill.
     *
     */
    @Cancelable
    public static class Tick extends PlayerSkillEvent
    {
        public Tick(EntityPlayer player, EnumSpellAction action, SkillStack skill, int duration)
        {
            super(player, action, skill, duration);
        }
    }

    /**
     * Fired when a player stops spelling a skill without the spell duration timing out.
     * Example:
     *   Stop spelling a skill
     *   Be interrupted by other skills
     *   Become silent by silent skills
     *
     * Duration on this event is how long the skill had spelled.
     *
     * Canceling this event will prevent the skill from being notified that it has stopped being spelled.
     */
    @Cancelable
    public static class Stop extends PlayerSkillEvent
    {
        public Stop(EntityPlayer player, EnumSpellAction action, SkillStack skill, int duration)
        {
            super(player, action, skill, duration);
        }
    }

    /**
     * Fired after a skill has fully finished being spelled.
     * The skill has been notified that it was spelled, and the skill/result stacks reflect after that state.
     *
     * The result skill stack is the stack that is placed in the player's inventory in replacement of the stack that is currently being spelled.
     *
     */
    public static class Finish extends PlayerSkillEvent
    {
        public SkillStack result;
        public Finish(EntityPlayer player, EnumSpellAction action, SkillStack skill, int duration, SkillStack result)
        {
            super(player, action, skill, duration);
            this.result = result;
        }
    }
}
