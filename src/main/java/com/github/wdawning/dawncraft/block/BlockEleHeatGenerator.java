package com.github.wdawning.dawncraft.block;

import java.util.List;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.creativetab.CreativeTabsLoader;
import com.github.wdawning.dawncraft.tileentity.TileEntityEleHeatGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEleHeatGenerator extends BlockContainer
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool BURNING = PropertyBool.create("burning");
    
    public BlockEleHeatGenerator()
    {
        super(BlockLoader.MACHINE);
        this.setUnlocalizedName("heatGenerator");
        this.setHardness(2.5F);
        this.setStepSound(Block.soundTypeMetal);
        this.setCreativeTab(CreativeTabsLoader.tabEnergy);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)
                .withProperty(BURNING, Boolean.FALSE));
    }
    
    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, FACING, BURNING);
    }
    
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer)
    {
        IBlockState origin = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return origin.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityEleHeatGenerator)
            {
                playerIn.openGui(dawncraft.instance, ((TileEntityEleHeatGenerator) tileentity).getGuiID(), worldIn, pos.getX(), pos.getY(), pos.getZ());
            }

            return true;
        }
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityEleHeatGenerator)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityEleHeatGenerator)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
        }
        
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 3);
        Boolean burning = Boolean.valueOf((meta & 4) != 0);
        return this.getDefaultState().withProperty(FACING, facing).withProperty(BURNING, burning);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facing = ((EnumFacing) state.getValue(FACING)).getHorizontalIndex();
        int burning = ((Boolean) state.getValue(BURNING)).booleanValue() ? 4 : 0;
        return facing | burning;
    }
    
    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (active)
        {
            worldIn.setBlockState(pos, BlockLoader.heatGenerator.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(BURNING, Boolean.TRUE), 3);
        }
        else
        {
            worldIn.setBlockState(pos, BlockLoader.heatGenerator.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(BURNING, Boolean.FALSE), 3);
        }

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
    
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityEleHeatGenerator();
	}
	
    @Override
    public int getRenderType()
    {
        return 3;
    }
}
