package io.github.dawncraft.block;

import io.github.dawncraft.tileentity.TileEntityMagnetChest;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * @author QingChenW
 **/
public class BlockMagnetChest extends BlockChest
{
    public BlockMagnetChest()
    {
        super(0);
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMagnetChest();
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(entity.isEntityAlive() && !world.isRemote)
        {
            if (entity instanceof EntityItem)
            {
                TileEntityMagnetChest tileEntityMagnetChest = (TileEntityMagnetChest) world.getTileEntity(pos);
                tileEntityMagnetChest.insertStackFromEntity((EntityItem) entity);
            }
        }
    }
}
