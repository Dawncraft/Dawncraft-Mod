package io.github.dawncraft.block;

import io.github.dawncraft.creativetab.CreativeTabsLoader;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

/**
 * @author QingChenW
 *
 */
public class BlockFurnitureSuperChest extends BlockContainer
{
    public BlockFurnitureSuperChest()
    {
        super(Material.wood);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityChest();
    }
}
