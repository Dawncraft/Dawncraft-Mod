package io.github.dawncraft.event;

import io.github.dawncraft.capability.CapabilityEventHandler;
import io.github.dawncraft.enchantment.EnchantmentEventHandler;
import io.github.dawncraft.fluid.FluidEventHandler;
import io.github.dawncraft.potion.PotionEventHandler;
import io.github.dawncraft.util.DawnEnumHelper;
import io.github.dawncraft.world.WorldEventHandler;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.MinecraftForge;

/**
 * Register some common events.
 *
 * @author QingChenW
 */
public class EventLoader
{
    public static final HoverEvent.Action SHOW_SKILL = DawnEnumHelper.addHoverActionType("SHOW_SKILL", "show_skill", true);

    public static void initEvents()
    {
	registerEvent(new GameEventHandler());
	registerEvent(new EnchantmentEventHandler());
	registerEvent(new PotionEventHandler());
	registerEvent(new FluidEventHandler());
	registerEvent(new CapabilityEventHandler());
	registerEvent(new WorldEventHandler());
    }

    private static void registerEvent(Object target)
    {
	MinecraftForge.EVENT_BUS.register(target);
    }
}
