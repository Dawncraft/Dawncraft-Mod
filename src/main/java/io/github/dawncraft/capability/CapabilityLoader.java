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
    @CapabilityInject(IPlayerThirst.class)
    public static Capability<IPlayerThirst> playerThirst;
    /** A capability to handle magic for player. */
    @CapabilityInject(IPlayerMagic.class)
    public static Capability<IPlayerMagic> playerMagic;

    public static void initCapabilities()
    {
        registerCapability(IPlayerThirst.class, CapabilityThirst.Implementation.class, new CapabilityThirst.Storage());
        registerCapability(IPlayerMagic.class, CapabilityMagic.Common.class, new CapabilityMagic.Storage());
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
