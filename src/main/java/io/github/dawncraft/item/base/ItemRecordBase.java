package io.github.dawncraft.item.base;

import io.github.dawncraft.dawncraft;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

public class ItemRecordBase extends ItemRecord
{
    public ItemRecordBase(String name)
    {
		super(name);
    }

    public ResourceLocation getRecordResource(String name)
    {
        return new ResourceLocation(dawncraft.MODID + ":" + dawncraft.MODID  + "." + name);
    }
}
