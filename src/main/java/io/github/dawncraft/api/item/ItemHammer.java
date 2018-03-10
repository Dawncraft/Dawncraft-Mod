package io.github.dawncraft.api.item;

import java.util.Set;

import com.google.common.collect.Sets;

import io.github.dawncraft.block.BlockLoader;
import net.minecraft.block.Block;
import net.minecraft.item.ItemTool;

public class ItemHammer extends ItemTool
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {BlockLoader.superChest});
    
    public ItemHammer(ToolMaterial material)
    {
        super(2.0F, material, EFFECTIVE_ON);
        //        this.toolClass = "hammer";
    }

}
