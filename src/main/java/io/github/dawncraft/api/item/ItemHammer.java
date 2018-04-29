package io.github.dawncraft.api.item;

import java.util.Set;

import com.google.common.collect.Sets;

import io.github.dawncraft.api.block.BlockFurniture;
import io.github.dawncraft.api.block.BlockMachine;
import io.github.dawncraft.block.BlockLoader;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemHammer extends ItemTool
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(BlockLoader.superChest);

    public ItemHammer(ToolMaterial material)
    {
        super(2.0F, material, EFFECTIVE_ON);
    }

    @Override
    public boolean canHarvestBlock(Block block)
    {
        return block instanceof BlockMachine ? this.toolMaterial.getHarvestLevel() >= 1 : block instanceof BlockFurniture;
    }
    
    @Override
    public float getStrVsBlock(ItemStack stack, Block state)
    {
        return this.efficiencyOnProperMaterial;
    }
}
