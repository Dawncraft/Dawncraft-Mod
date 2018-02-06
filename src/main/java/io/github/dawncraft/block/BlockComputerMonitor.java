package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockMachineBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author QingChenW
 *
 */
public class BlockComputerMonitor extends BlockMachineBase
{
    public BlockComputerMonitor()
    {
        super();
        this.setStepSound(soundTypePiston);
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
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
        super.setBlockBoundsBasedOnState(worldIn, pos);
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        // TODO 电脑显示器的te,别忘了233
        return null;
    }
}
