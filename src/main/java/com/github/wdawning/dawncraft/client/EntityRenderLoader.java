package com.github.wdawning.dawncraft.client;

import com.github.wdawning.dawncraft.entity.EntityLoader;
import com.github.wdawning.dawncraft.tileentity.TileEntityLoader;

public class EntityRenderLoader
{
    public EntityRenderLoader()
    {
        EntityLoader.registerRenders();
        TileEntityLoader.registerRenders();
    }
}
