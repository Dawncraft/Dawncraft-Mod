package io.github.dawncraft.block;

import io.github.dawncraft.block.base.BlockMachineBase;
import io.github.dawncraft.creativetab.CreativeTabsLoader;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

/**
 * @author QingChenW
 *
 */
public class BlockComputerCase extends BlockMachineBase
{
    public BlockComputerCase.ComputerCaseType type;
    
    public BlockComputerCase(ComputerCaseType caseType)
    {
        super();
        this.type = caseType;
        this.setHardness(3.0f);
        this.setResistance(5.0f);
        this.setHarvestLevel("ItemPickaxeBase", this.type.getTool());
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
        if(worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.NORTH || worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.SOUTH)
        {
            this.setBlockBounds(0.25F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
        }
        else if(worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.WEST || worldIn.getBlockState(pos).getValue(FACING) == EnumFacing.EAST)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 0.75F);
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        // TODO 电脑机箱的te,别忘了233
        return null;
    }
    
    public enum ComputerCaseType
    {
        SIMPLE(0, 1), PRO(1, 2), SUPER(2, 2);
        
        private int _id;
        private int _tool;
        
        ComputerCaseType(int id, int tool)
        {
           this._id = id;
           this._tool = tool;
        }
        
        public int getId()
        {  
            return _id;  
        }
        
        public int getTool()
        {  
            return _tool;  
        }
    }
}
