package WdawningStudio.DawnW.science.block;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMagnet_block extends Block
{
    public BlockMagnet_block()
    {
        super(Material.iron);
        this.setUnlocalizedName("magnetBlock");
//        this.setHarvestLevel(pickaxe, 1);//ERROR...
        this.setHardness(10.0f);
        this.setResistance(10);
        this.setStepSound(soundTypeStone);
        this.setCreativeTab(CreativeTabsLoader.tabMagneticAndElectricity);
    }
}
