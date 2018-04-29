package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockMachine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Computer monitor
 *
 * @author QingChenW
 */
public class BlockComputerMonitor extends BlockMachine
{
    public EnumMonitorType type;
    
    public BlockComputerMonitor(EnumMonitorType monitorType)
    {
        super();
        this.type = monitorType;
        this.setStepSound(soundTypePiston);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(WORKING, false));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        // TODO 电脑显示器的te,别忘了233
        return null;
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
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
    {
        EnumFacing facing = world.getBlockState(pos).getValue(FACING);

        if (facing.getAxis() == Axis.Z)
            this.setBlockBounds(0.0625F, 0.0F, 0.45F, 0.9375F, 0.95F, 0.55F);
        else if (facing.getAxis() == Axis.X)
            this.setBlockBounds(0.45F, 0.0F, 0.0625F, 0.55F, 0.95F, 0.9375F);
    }
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileEntity tileentity = world.getTileEntity(pos);
            
        }
        return true;
    }
    
    public enum EnumMonitorType
    {
        SIMPLE, ADVANCED, SUPER;
    }
}
