package io.github.dawncraft.api.event;

import java.util.List;

import io.github.dawncraft.api.event.entity.LivingRecoverEvent;
import io.github.dawncraft.api.event.player.PlayerSkillEvent;
import io.github.dawncraft.api.event.player.SkillLearnedEvent;
import io.github.dawncraft.api.event.player.SkillTooltipEvent;
import io.github.dawncraft.container.ISkillInventory;
import io.github.dawncraft.skill.EnumSpellAction;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.client.util.ITooltipFlag;
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

    public static void firePlayerLearningEvent(EntityPlayer player, SkillStack learned, ISkillInventory learnMatrix)
    {
        SkillLearnedEvent event = new SkillLearnedEvent(player, learned, learnMatrix);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static SkillTooltipEvent onSkillTooltip(SkillStack skillStack, EntityPlayer entityPlayer, List<String> toolTip, ITooltipFlag flags)
    {
        SkillTooltipEvent event = new SkillTooltipEvent(skillStack, entityPlayer, toolTip, flags);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    public static int onSkillPrepareStart(EntityPlayer player, SkillStack skillstack, int duration)
    {
        PlayerSkillEvent event = new PlayerSkillEvent.Start(player, EnumSpellAction.PREPARE, skillstack, duration);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }

    public static int onSkillPrepareTick(EntityPlayer player, SkillStack skillstack, int duration)
    {
        PlayerSkillEvent event = new PlayerSkillEvent.Tick(player, EnumSpellAction.PREPARE, skillstack, duration);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }

    public static boolean onSkillPrepareStop(EntityPlayer player, SkillStack skillstack, int duration)
    {
        return MinecraftForge.EVENT_BUS.post(new PlayerSkillEvent.Stop(player, EnumSpellAction.PREPARE, skillstack, duration));
    }

    public static int onSkillSpellStart(EntityPlayer player, SkillStack skillstack, int duration)
    {
        PlayerSkillEvent event = new PlayerSkillEvent.Start(player, EnumSpellAction.SPELL, skillstack, duration);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }

    public static int onSkillSpellTick(EntityPlayer player, SkillStack skillstack, int duration)
    {
        PlayerSkillEvent event = new PlayerSkillEvent.Tick(player, EnumSpellAction.SPELL, skillstack, duration);
        return MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }

    public static boolean onSkillSpellStop(EntityPlayer player, SkillStack skillstack, int duration)
    {
        return MinecraftForge.EVENT_BUS.post(new PlayerSkillEvent.Stop(player, EnumSpellAction.SPELL, skillstack, duration));
    }

    public static SkillStack onSkillSpellFinish(EntityPlayer player, SkillStack skillstack, int duration, SkillStack result)
    {
        PlayerSkillEvent event = new PlayerSkillEvent.Finish(player, EnumSpellAction.SPELL, skillstack, duration, result);
        MinecraftForge.EVENT_BUS.post(event);
        return result;
    }
}
