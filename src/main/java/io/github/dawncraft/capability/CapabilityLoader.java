package io.github.dawncraft.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Register capabilities.
 *
 * @author QingChenW
 */
public class CapabilityLoader
{
    /** A capability to handle thirst for player. */
    @CapabilityInject(IThirst.class)
    public static Capability<IThirst> thirst;
    /** A capability to handle magic for player. */
    @CapabilityInject(IMagic.class)
    public static Capability<IMagic> magic;

    public static void initCapabilities()
    {
        registerCapability(IThirst.class, CapabilityThirst.Implementation.class, new CapabilityThirst.Storage());
        registerCapability(IMagic.class, CapabilityPlayer.Common.class, new CapabilityPlayer.Storage());
    }
    
    /**
     * Register a capability with its abstract class, the implementation of its abstract class and storage object.
     *
     * @param type The capability's abstract class
     *
     * @param implementation The capability's implementation class
     * @param storage The capability's storage object
     */
    private static <T> void registerCapability(Class<T> type, Class<? extends T> implementation, IStorage<T> storage)
    {
        CapabilityManager.INSTANCE.register(type, storage, implementation);
    }
}
