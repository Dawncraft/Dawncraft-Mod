package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockMachineBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 *
 * @author QingChenW
 */
public class BlockComputerCase extends BlockMachineBase
{
    public BlockComputerCase.ComputerCaseType type;
    
    public BlockComputerCase(ComputerCaseType caseType)
    {
        super();
        this.type = caseType;
        this.setHarvestLevel("ItemPickaxe", this.type.getTool());
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
        if (worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.NORTH
                || worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.SOUTH)
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
        else if (worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.WEST
                || worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.EAST)
            this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 0.75F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        // TODO 电脑机箱的te,别忘了233
        return null;
    }
    
    public enum ComputerCaseType
    {
        SIMPLE(0, 1), ADVANCED(1, 2), SUPER(2, 2);
        
        private int _id;
        private int _tool;
        
        ComputerCaseType(int id, int tool)
        {
            this._id = id;
            this._tool = tool;
        }
        
        public int getId()
        {
            return this._id;
        }
        
        public int getTool()
        {
            return this._tool;
        }
    }
}
