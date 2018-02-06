package io.github.dawncraft.block;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.block.BlockMachineBase;
import io.github.dawncraft.container.GuiLoader;
import io.github.dawncraft.tileentity.TileEntityMachineFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

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
            if (tileentity instanceof TileEntityMachineFurnace)
                playerIn.openGui(Dawncraft.instance, GuiLoader.GUI_MACHINE_FURNACE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        
        IItemHandler slots = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
        
        for (int i = slots.getSlots() - 1; i >= 0; --i)
        {
            if (slots.getStackInSlot(i) != null)
            {
                Block.spawnAsEntity(worldIn, pos, slots.getStackInSlot(i));
                ((IItemHandlerModifiable) slots).setStackInSlot(i, null);
            }
        }

        slots = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        
        for (int i = slots.getSlots() - 1; i >= 0; --i)
        {
            if (slots.getStackInSlot(i) != null)
            {
                Block.spawnAsEntity(worldIn, pos, slots.getStackInSlot(i));
                ((IItemHandlerModifiable) slots).setStackInSlot(i, null);
            }
        }

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
        return new TileEntityMachineFurnace();
    }
    
    public static void setBlockState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        
        if (active)
            worldIn.setBlockState(pos, iblockstate.getBlock().getDefaultState()
                    .withProperty(FACING, iblockstate.getValue(FACING)).withProperty(BURNING, Boolean.TRUE), 3);
        else
            worldIn.setBlockState(pos, iblockstate.getBlock().getDefaultState()
                    .withProperty(FACING, iblockstate.getValue(FACING)).withProperty(BURNING, Boolean.FALSE), 3);
        
        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
}
