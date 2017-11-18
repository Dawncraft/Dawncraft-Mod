package io.github.dawncraft.event;

import io.github.dawncraft.capability.CapabilityEvent;
import io.github.dawncraft.enchantment.EnchantmentEvent;
import io.github.dawncraft.potion.PotionEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Register some common events.
 *
 * @author QingChenW
 */
// TODO 把event改成Loader专门注册事件,Handler负责处理事件
public class EventLoader
{
    public EventLoader(FMLInitializationEvent event)
    {
        registerEvent(new EventHandler(event));
        registerEvent(new CapabilityEvent(event));
        registerEvent(new EnchantmentEvent(event));
        registerEvent(new PotionEvent(event));
    }
    
    private static void registerEvent(Object target)
    {
        MinecraftForge.EVENT_BUS.register(target);
    }
}
