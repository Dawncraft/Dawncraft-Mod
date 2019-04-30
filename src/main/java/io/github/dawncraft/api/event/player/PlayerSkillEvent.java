package io.github.dawncraft.api.event.player;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.player.EntityPlayer;

public abstract class PlayerSkillEvent extends PlayerEvent
{
    public final SkillStack skill;
    public int duration;
    
    private PlayerSkillEvent(EntityPlayer player, SkillStack skill, int duration)
    {
        super(player);
        this.skill = skill;
        this.duration = duration;
    }

    public static abstract class Prepare extends PlayerSkillEvent
    {
        public Prepare(EntityPlayer player, SkillStack skill, int duration)
        {
            super(player, skill, duration);
        }

        /**
         * Fired when a player starts 'preparing' a skill, typically when they press spell keys.
         *
         * Cancel the event, or set the duration or <= 0 to prevent it from processing.
         */
        @Cancelable
        public static class Start extends Prepare
        {
            public Start(EntityPlayer player, SkillStack skill, int duration)
            {
                super(player, skill, duration);
            }
        }
        
        /**
         * Fired every tick that a player is 'preparing' a skill, see {@link Prepare.Start} for info.
         *
         * Cancel the event, or set the duration or <= 0 to cause the player to stop preparing the skill.
         *
         */
        @Cancelable
        public static class Tick extends Prepare
        {
            public Tick(EntityPlayer player, SkillStack skill, int duration)
            {
                super(player, skill, duration);
            }
        }

        /**
         * Fired when a player stops preparing a skill without the prepare duration timing out.
         * Example:
         *   Stop preparing a skill
         *   Be interrupted by other skills
         *   Become silent by silent skills
         *
         * Duration on this event is how long the skill had prepared.
         *
         * Canceling this event will prevent the skill from being notified that it has stopped being prepared.
         */
        @Cancelable
        public static class Stop extends Prepare
        {
            public Stop(EntityPlayer player, SkillStack skill, int duration)
            {
                super(player, skill, duration);
            }
        }
    }
    
    public static abstract class Spell extends PlayerSkillEvent
    {
        public Spell(EntityPlayer player, SkillStack skill, int duration)
        {
            super(player, skill, duration);
        }
        
        /**
         * Fired when a player starts 'spelling' a skill, typically when they finish their preparations.
         *
         * Cancel the event, or set the duration or <= 0 to prevent it from processing.
         */
        @Cancelable
        public static class Start extends Spell
        {
            public Start(EntityPlayer player, SkillStack skill, int duration)
            {
                super(player, skill, duration);
            }
        }
        
        /**
         * Fired every tick that a player is 'spelling' a skill, see {@link Spell.Start} for info.
         *
         * Cancel the event, or set the duration or <= 0 to cause the player to stop spelling the skill.
         *
         */
        @Cancelable
        public static class Tick extends Spell
        {
            public Tick(EntityPlayer player, SkillStack skill, int duration)
            {
                super(player, skill, duration);
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
        public static class Stop extends Spell
        {
            public Stop(EntityPlayer player, SkillStack skill, int duration)
            {
                super(player, skill, duration);
            }
        }
        
        /**
         * Fired after a skill has fully finished being spelled.
         * The skill has been notified that it was spelled, and the skill/result stacks reflect after that state.
         *
         * The result skill stack is the stack that is placed in the player's inventory in replacement of the stack that is currently being spelled.
         *
         */
        public static class Finish extends Spell
        {
            public SkillStack result;
            public Finish(EntityPlayer player, SkillStack skill, int duration, SkillStack result)
            {
                super(player, skill, duration);
                this.result = result;
            }
        }
    }
}
