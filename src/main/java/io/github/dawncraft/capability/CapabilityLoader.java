package io.github.dawncraft.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Register capabilities.
 * 
 * @author QingChenW
 */
public class CapabilityLoader
{
    @CapabilityInject(IMana.class)
    public static Capability<IMana> mana;

    public CapabilityLoader(FMLPreInitializationEvent event)
    {
        CapabilityManager.INSTANCE.register(IMana.class, new CapabilityMana.Storage(), CapabilityMana.Implementation.class);
    }
}
