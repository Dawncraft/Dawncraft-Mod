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
