package com.github.wdawning.dawncraft.item;

import net.minecraft.util.ResourceLocation;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.common.CreativeTabsLoader;

public class ItemRecord extends net.minecraft.item.ItemRecord
{
    protected ItemRecord(String name)
    {
		super(name);
        this.setCreativeTab(CreativeTabsLoader.tabColourEgg);
    }

    public ResourceLocation getRecordResource(String name)
    {
        return new ResourceLocation(dawncraft.MODID + ":" + "dawncraft." + name);
    }
}
