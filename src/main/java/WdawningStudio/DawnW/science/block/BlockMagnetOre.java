package WdawningStudio.DawnW.science.block;

import java.util.Random;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
import WdawningStudio.DawnW.science.item.ItemLoader;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

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
