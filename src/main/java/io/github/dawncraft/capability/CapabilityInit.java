package io.github.dawncraft.capability;

import java.util.concurrent.Callable;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Register some capabilities.
 *
 * @author QingChenW
 */
public class CapabilityInit
{
    /** A capability to handle thirst for player. */
    @CapabilityInject(IPlayerThirst.class)
    public static Capability<IPlayerThirst> PLAYER_THIRST;
    /** A capability to handle magic for player. */
    @CapabilityInject(IPlayerMagic.class)
    public static Capability<IPlayerMagic> PLAYER_MAGIC;

    public static void initCapabilities()
    {
        registerCapability(IPlayerThirst.class, CapabilityThirst.Implementation::new, new CapabilityThirst.Storage());
        registerCapability(IPlayerMagic.class, CapabilityMagic.Common::new, new CapabilityMagic.Storage());
    }

    /**
     * Register a capability with its abstract class, the implementation of its abstract class and storage object.
     *
     * @param type The capability's abstract class
     *
     * @param factory The capability's implementation factory
     * @param storage The capability's storage object
     */
    private static <T> void registerCapability(Class<T> type, Callable<? extends T> factory, IStorage<T> storage)
    {
        CapabilityManager.INSTANCE.register(type, storage, factory);
    }
}
