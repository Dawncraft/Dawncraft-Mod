package com.github.wdawning.dawncraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.github.wdawning.dawncraft.common.CreativeTabsLoader;

public class BlockMagnetBlock extends Block
{
	public BlockMagnetBlock()
    {
        super(Material.iron);
        this.setUnlocalizedName("magnetBlock");
        this.setHarvestLevel("ItemPickaxe", 1);
        this.setHardness(5.0f);
        this.setResistance(10.0f);
        this.setStepSound(soundTypePiston);
        this.setCreativeTab(CreativeTabsLoader.tabMagnetic);
    }
}
