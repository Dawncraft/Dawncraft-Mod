package io.github.dawncraft.api.event;

import java.util.List;

import io.github.dawncraft.api.event.entity.LivingRecoverEvent;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class DawnEventFactory
{
    public static float onLivingRecover(EntityLivingBase entity, float amount)
    {
        LivingRecoverEvent event = new LivingRecoverEvent(entity, amount);
        return MinecraftForge.EVENT_BUS.post(event) ? 0 : event.amount;
    }
    
    public static int onSkillSpellStart(EntityPlayer player, SkillStack skillstack, int duration)
    {
        //PlayerUseItemEvent event = new PlayerUseItemEvent.Start(player, item, duration);
        return duration;//MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }
    
    public static int onSkillSpellTick(EntityPlayer player, SkillStack skillstack, int duration)
    {
        //PlayerUseItemEvent event = new PlayerUseItemEvent.Tick(player, item, duration);
        return duration;//MinecraftForge.EVENT_BUS.post(event) ? -1 : event.duration;
    }
    
    public static boolean onSpellSkillStop(EntityPlayer player, SkillStack skillstack, int duration)
    {
        return false;//return MinecraftForge.EVENT_BUS.post(new PlayerUseItemEvent.Stop(player, item, duration));
    }

    public static SkillStack onSpellSkillFinish(EntityPlayer player, SkillStack skillstack, int duration, SkillStack result)
    {
        //PlayerUseItemEvent.Finish event = new PlayerUseItemEvent.Finish(player, item, duration, result);
        //MinecraftForge.EVENT_BUS.post(event);
        return result;
    }
    
    public static boolean onSpellSkillIntoWorld(SkillStack skillstack, EntityPlayer player, World world)
    {
        return false;
    }

    public static void onItemTooltip(SkillStack skillStack, EntityPlayer entityPlayer, List<String> toolTip, boolean showAdvancedItemTooltips)
    {
        /*
        ItemTooltipEvent event = new ItemTooltipEvent(new ItemStack(), entityPlayer, toolTip, showAdvancedItemTooltips);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
         */
    }
}
