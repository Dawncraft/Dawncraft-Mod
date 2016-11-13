package com.github.wdawning.dawncraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.github.wdawning.dawncraft.common.CreativeTabsLoader;

public class BlockMagnetOre extends Block
{
	public BlockMagnetOre()
    {
        super(Material.rock);
        this.setUnlocalizedName("magnetOre");
        this.setHarvestLevel("ItemPickaxe", 1);
        this.setHardness(3.0f);
        this.setResistance(5.0f);
        this.setStepSound(soundTypeStone);
        this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
    }
}
