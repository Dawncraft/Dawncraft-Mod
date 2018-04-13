package io.github.dawncraft.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
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
    /** A capability to handle thirst for player. */
    @CapabilityInject(IWater.class)
    public static Capability<IWater> water;
    /** A capability to handle magic for player. */
    @CapabilityInject(IMagic.class)
    public static Capability<IMagic> magic;

    public CapabilityLoader(FMLPreInitializationEvent event)
    {
        register(IMagic.class, CapabilityMagic.Implementation.class, new CapabilityMagic.Storage());
    }
    
    /**
     * Register a capability with its abstract class, the implementation of its abstract class and storage object.
     *
     * @param type The capability's abstract class
     *
     * @param implementation The capability's implementation class
     * @param storage The capability's storage object
     */
    public static <T> void register(Class<T> type, Class<? extends T> implementation, IStorage<T> storage)
    {
        CapabilityManager.INSTANCE.register(type, storage, implementation);
    }
}
