package io.github.dawncraft.client.renderer.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TileEntityRenderLoader
{
    public TileEntityRenderLoader(FMLPreInitializationEvent event)
    {
        
    }
    
    private static void registerTileEntityRender(Class<? extends TileEntity> tileEntityClass,
            TileEntitySpecialRenderer renderer)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
    }
}
