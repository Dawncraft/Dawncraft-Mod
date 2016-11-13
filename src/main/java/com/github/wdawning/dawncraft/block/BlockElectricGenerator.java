package com.github.wdawning.dawncraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.github.wdawning.dawncraft.dawncraft;
import com.github.wdawning.dawncraft.client.gui.GuiLoader;
import com.github.wdawning.dawncraft.common.CreativeTabsLoader;
import com.github.wdawning.dawncraft.tileentity.generator.TileEntityHeatGenerator;

public class BlockElectricGenerator extends BlockContainer
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool BURNING = PropertyBool.create("burning");
    /**
     * The type of electric generator.
     */
    public int generatorType;
    
    public BlockElectricGenerator(String unlocalizedName, int type)
    {
        super(BlockLoader.MACHINE);
        this.setUnlocalizedName(unlocalizedName);
        this.setGeneratorType(type);
        this.setHardness(2.5F);
        this.setStepSound(Block.soundTypeMetal);
        this.setCreativeTab(CreativeTabsLoader.tabEnergy);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING,
                Boolean.FALSE));
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
        if (!worldIn.isRemote)
        {
            int id;
            TileEntity tileentity = worldIn.getTileEntity(pos);
            
            switch (getGeneratorType())
            {
            default:
            case 0:
                id = GuiLoader.GUI_HEAT_GENERATOR;
                break;
            }
            
            playerIn.openGui(dawncraft.instance, id, worldIn, pos.getX(), pos.getY(), pos.getZ());
            
            return true;
        }
        
        return true;
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
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        switch (getGeneratorType())
        {
        default:
        case 0:
            return new TileEntityHeatGenerator();
        }
    }
    
    @Override
    public int getRenderType()
    {
        return 3;
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
    
    /**
     * @param type
     *            0=heat, 1=fluid, 2=solar, 3=wind, 4=nuclear, 5=magic
     */
    public void setGeneratorType(int type)
    {
        this.generatorType = type;
    }
    
    /**
     * @return 0=heat, 1=fluid, 2=solar, 3=wind, 4=nuclear, 5=magic
     */
    public int getGeneratorType()
    {
        return this.generatorType;
    }
    
    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        
        if (active)
        {
            worldIn.setBlockState(pos, BlockLoader.electricityGeneratorHeat.getDefaultState()
                    .withProperty(FACING, iblockstate.getValue(FACING)).withProperty(BURNING, Boolean.TRUE), 3);
        }
        else
        {
            worldIn.setBlockState(pos,
                    BlockLoader.electricityGeneratorHeat.getDefaultState()
                            .withProperty(FACING, iblockstate.getValue(FACING)).withProperty(BURNING, Boolean.FALSE),
                    3);
        }
        
        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
}
