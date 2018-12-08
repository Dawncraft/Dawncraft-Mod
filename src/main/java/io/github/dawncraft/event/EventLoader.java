package io.github.dawncraft.event;

import io.github.dawncraft.capability.CapabilityEvent;
import io.github.dawncraft.enchantment.EnchantmentEvent;
import io.github.dawncraft.potion.PotionEvent;
import io.github.dawncraft.world.WorldEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Register some common events.
 *
 * @author QingChenW
 */
public class EventLoader
{
    public EventLoader(FMLInitializationEvent event)
    {
        register(new EventHandler(event));
        register(new EnchantmentEvent(event));
        register(new PotionEvent(event));
        register(new CapabilityEvent(event));
        register(new WorldEventHandler(event));
    }

    private static void register(Object target)
    {
        MinecraftForge.EVENT_BUS.register(target);
    }
}
