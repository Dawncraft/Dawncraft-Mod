package WdawningStudio.DawnW.science.block;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMagnet_ore extends Block
{
    public BlockMagnet_ore()
    {
        super(Material.rock);
        this.setUnlocalizedName("magnetOre");
//        this.setHarvestLevel(pickaxe, 1);//ERROR...
        this.setHardness(1.5f);
        this.setResistance(10);
        this.setStepSound(soundTypeStone);
        this.setCreativeTab(CreativeTabsLoader.tabMagneticAndElectricity);
    }
}
