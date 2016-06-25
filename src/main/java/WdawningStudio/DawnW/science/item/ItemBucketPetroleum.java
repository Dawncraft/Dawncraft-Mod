package WdawningStudio.DawnW.science.item;

import WdawningStudio.DawnW.science.block.BlockLoader;
import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
import WdawningStudio.DawnW.science.fluid.FluidLoader;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class ItemBucketPetroleum extends ItemBucket
{
    public ItemBucketPetroleum()
    {
        super(BlockLoader.fluidPetroleum);
        this.setContainerItem(Items.bucket);
        this.setUnlocalizedName("bucketPetroleum");
        this.setCreativeTab(CreativeTabsLoader.tabEnergy);
        FluidContainerRegistry.registerFluidContainer(FluidLoader.fluidPetroleum, new ItemStack(this),FluidContainerRegistry.EMPTY_BUCKET);
    }
}