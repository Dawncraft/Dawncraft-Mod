package com.github.wdawning.dawncraft.client.renderer.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class TileentityRenderLoader
{
    public TileentityRenderLoader()
    {
        
    }
    
    private static void registerTileEntityRender(Class<? extends TileEntity> tileEntityClass,
            TileEntitySpecialRenderer renderer)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
    }
}
