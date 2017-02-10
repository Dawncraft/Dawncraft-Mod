package io.github.dawncraft.item;

import io.github.dawncraft.dawncraft;
import net.minecraft.util.ResourceLocation;

public class ItemRecord extends net.minecraft.item.ItemRecord
{
    protected ItemRecord(String name)
    {
		super(name);

    }

    public ResourceLocation getRecordResource(String name)
    {
        return new ResourceLocation(dawncraft.MODID + ":" + dawncraft.MODID  + "." + name);
    }
}
