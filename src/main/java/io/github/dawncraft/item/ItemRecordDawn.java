package io.github.dawncraft.item;

import io.github.dawncraft.Dawncraft;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

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
