package io.github.dawncraft.api.event;

import java.util.List;

import io.github.dawncraft.api.event.entity.LivingRecoverEvent;
import io.github.dawncraft.api.event.player.PlayerSkillEvent;
import io.github.dawncraft.api.event.player.SkillTooltipEvent;
import io.github.dawncraft.skill.SkillStack;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

/**
 * Dawncraft's event factory.
 *
 * @author QingChenW
 */
public class DawnEventFactory
{
    public static float onLivingRecover(EntityLivingBase entity, float amount)
    {
        LivingRecoverEvent event = new LivingRecoverEvent(entity, amount);
        return MinecraftForge.EVENT_BUS.post(event) ? 0 : event.amount;
    }

    public static int onSkillPrepareStart(EntityPlayer player, SkillStack skillstack, int duration)
    {
        PlayerSkillEvent event = new PlayerSkillEvent.Prepare.Start(player, skillstack, duration);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }

    public static int onSkillPrepareTick(EntityPlayer player, SkillStack skillstack, int duration)
    {
        PlayerSkillEvent event = new PlayerSkillEvent.Prepare.Tick(player, skillstack, duration);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }

    public static boolean onSkillPrepareStop(EntityPlayer player, SkillStack skillstack, int duration)
    {
        return MinecraftForge.EVENT_BUS.post(new PlayerSkillEvent.Prepare.Stop(player, skillstack, duration));
    }
    
    public static int onSkillSpellStart(EntityPlayer player, SkillStack skillstack, int duration)
    {
        PlayerSkillEvent event = new PlayerSkillEvent.Spell.Start(player, skillstack, duration);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }

    public static int onSkillSpellTick(EntityPlayer player, SkillStack skillstack, int duration)
    {
        PlayerSkillEvent event = new PlayerSkillEvent.Spell.Tick(player, skillstack, duration);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }

    public static boolean onSkillSpellStop(EntityPlayer player, SkillStack skillstack, int duration)
    {
        return MinecraftForge.EVENT_BUS.post(new PlayerSkillEvent.Spell.Stop(player, skillstack, duration));
    }
    
    public static SkillStack onSkillSpellFinish(EntityPlayer player, SkillStack skillstack, int duration, SkillStack result)
    {
        PlayerSkillEvent event = new PlayerSkillEvent.Spell.Finish(player, skillstack, duration, result);
        MinecraftForge.EVENT_BUS.post(event);
        return result;
    }
    
    public static SkillTooltipEvent onSkillTooltip(SkillStack skillStack, EntityPlayer entityPlayer, List<String> toolTip, boolean showAdvancedSkillTooltips)
    {
        SkillTooltipEvent event = new SkillTooltipEvent(skillStack, entityPlayer, toolTip, showAdvancedSkillTooltips);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
}
