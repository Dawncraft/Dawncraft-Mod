package io.github.dawncraft.item;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

import io.github.dawncraft.Dawncraft;

public class ItemRecordDawn extends ItemRecord
{
    public ItemRecordDawn(String name)
    {
        super(name);
    }

    @Override
    public ResourceLocation getRecordResource(String name)
    {
        return new ResourceLocation(Dawncraft.MODID, name);
    }
}
