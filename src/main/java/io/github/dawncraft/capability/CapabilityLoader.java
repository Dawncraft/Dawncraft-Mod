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
    /** A capability to handle extra data for player. */
    @CapabilityInject(IPlayer.class)
    public static Capability<IPlayer> player;
    
    public CapabilityLoader(FMLPreInitializationEvent event)
    {
        register(IPlayer.class, CapabilityPlayer.Common.class, new CapabilityPlayer.Storage());
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
