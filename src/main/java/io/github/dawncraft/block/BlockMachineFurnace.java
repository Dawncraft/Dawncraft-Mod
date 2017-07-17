package io.github.dawncraft.block;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.block.base.BlockMachineBase;
import io.github.dawncraft.tileentity.TileEntityEnergyHeatGen;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author QingChenW
 *
 */
public class BlockMachineFurnace extends BlockMachineBase
{
    public static final PropertyBool BURNING = PropertyBool.create("burning");
    
    public BlockMachineFurnace()
    {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING,
                Boolean.FALSE));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityFurnace)
                playerIn.openGui(dawncraft.instance, 0/* TODO 电炉方块gui的id和te */, worldIn, pos.getX(), pos.getY(),
                        pos.getZ());
        }
        return true;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer)
    {
        IBlockState origin = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return origin.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
        worldIn.updateComparatorOutputLevel(pos, this);

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, FACING, BURNING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        Boolean working = Boolean.valueOf((meta & 4) != 0);
        return this.getDefaultState().withProperty(FACING, facing).withProperty(BURNING, working);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facing = state.getValue(FACING).getHorizontalIndex();
        int working = state.getValue(BURNING).booleanValue() ? 4 : 0;
        return facing | working;
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityEnergyHeatGen();
    }
}
