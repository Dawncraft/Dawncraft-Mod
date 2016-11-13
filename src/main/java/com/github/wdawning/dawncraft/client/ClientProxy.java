package com.github.wdawning.dawncraft.client;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.github.wdawning.dawncraft.client.gui.GuiEventLoader;
import com.github.wdawning.dawncraft.client.renderer.entity.EntityRenderLoader;
import com.github.wdawning.dawncraft.client.renderer.tileentity.TileentityRenderLoader;
import com.github.wdawning.dawncraft.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        new FluidRenderLoader(event);
    }
    
    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
        new KeyLoader();
        new ItemRenderLoader();
        new EntityRenderLoader();
        new TileentityRenderLoader();
        new GuiEventLoader();
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }
}
