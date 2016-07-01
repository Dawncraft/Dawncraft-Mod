package com.github.wdawning.dawncraft.client;

import com.github.wdawning.dawncraft.block.BlockLoader;
import com.github.wdawning.dawncraft.item.ItemLoader;

public class ItemRenderLoader
{
    public ItemRenderLoader()
    {
        ItemLoader.registerRenders();
        BlockLoader.registerRenders();
    }
}