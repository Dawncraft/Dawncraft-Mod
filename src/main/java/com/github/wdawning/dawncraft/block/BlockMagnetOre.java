package com.github.wdawning.dawncraft.block;

import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

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
