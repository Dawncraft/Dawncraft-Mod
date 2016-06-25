package WdawningStudio.DawnW.science.block;

import WdawningStudio.DawnW.science.creativetab.CreativeTabsLoader;
import WdawningStudio.DawnW.science.item.ItemLoader;

import java.util.Random;

import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class BlockWchest extends BlockChest //BlockContainer
{
    public BlockWchest()
    {
        super(0);
        this.setUnlocalizedName("superChest");
        this.setCreativeTab(CreativeTabsLoader.tabFurniture);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
        return new TileEntityChest();
	}
}