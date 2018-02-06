package io.github.dawncraft.capability;

import io.github.dawncraft.Dawncraft;
import net.minecraft.util.ResourceLocation;
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
    public static ResourceLocation res_magic = new ResourceLocation(Dawncraft.MODID + ":" + "magic");
    @CapabilityInject(IMagic.class)
    public static Capability<IMagic> magic;

    public CapabilityLoader(FMLPreInitializationEvent event)
    {
        register(IMagic.class, new CapabilityMagic.Storage(), CapabilityMagic.Implementation.class);
    }
    
    public static <T> void register(Class<T> type, IStorage<T> storage, Class<? extends T> implementation)
    {
        CapabilityManager.INSTANCE.register(type, storage, implementation);
    }
}
