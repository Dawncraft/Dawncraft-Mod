package WdawningStudio.DawnW.science.block;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

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
