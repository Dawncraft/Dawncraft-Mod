package io.github.dawncraft.item.base;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

public class ItemRecordBase extends ItemRecord
{
    private final String modId;

    public ItemRecordBase(String modid, String name)
    {
        super(name);
        this.modId = modid;
    }

    @Override
    public ResourceLocation getRecordResource(String name)
    {
        return new ResourceLocation(this.modId + ":" + name);
    }
}
