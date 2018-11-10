package io.github.dawncraft.api.event;

import java.util.List;

import io.github.dawncraft.api.event.entity.LivingRecoverEvent;
import io.github.dawncraft.api.event.player.PlayerSpellSkillEvent;
import io.github.dawncraft.api.event.player.SkillTooltipEvent;
import io.github.dawncraft.skill.SkillStack;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * Dawncraft's event factory class.
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
    
    public static int onSkillSpellStart(EntityPlayer player, SkillStack skillstack, int duration)
    {
        PlayerSpellSkillEvent event = new PlayerSpellSkillEvent.Start(player, skillstack, duration);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }
    
    public static int onSkillSpellTick(EntityPlayer player, SkillStack skillstack, int duration)
    {
        PlayerSpellSkillEvent event = new PlayerSpellSkillEvent.Tick(player, skillstack, duration);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }
    
    public static boolean onSpellSkillStop(EntityPlayer player, SkillStack skillstack, int duration)
    {
        return MinecraftForge.EVENT_BUS.post(new PlayerSpellSkillEvent.Stop(player, skillstack, duration));
    }

    public static SkillStack onSpellSkillFinish(EntityPlayer player, SkillStack skillstack, int duration, SkillStack result)
    {
        PlayerSpellSkillEvent.Finish event = new PlayerSpellSkillEvent.Finish(player, skillstack, duration, result);
        MinecraftForge.EVENT_BUS.post(event);
        return result;
    }
    
    public static boolean onSpellSkillIntoWorld(SkillStack skillstack, EntityPlayer player, World world)
    {
        return false;
    }

    public static SkillTooltipEvent onSkillTooltip(SkillStack skillStack, EntityPlayer entityPlayer, List<String> toolTip, boolean showAdvancedSkillTooltips)
    {
        SkillTooltipEvent event = new SkillTooltipEvent(skillStack, entityPlayer, toolTip, showAdvancedSkillTooltips);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }
}
