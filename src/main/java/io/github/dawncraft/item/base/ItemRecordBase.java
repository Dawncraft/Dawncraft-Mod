package io.github.dawncraft.item.base;

import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class ItemRecordBase extends ItemRecord
{
    public ItemRecordBase(String name)
    {
        super(name);
    }

    @Override
    public ResourceLocation getRecordResource(String name)
    {
        String prefix;
        ModContainer mcmod = Loader.instance().activeModContainer();
        prefix = mcmod != null ? mcmod.getModId().toLowerCase() : "minecraft";
        return new ResourceLocation(prefix + ":" + name);
    }
}
