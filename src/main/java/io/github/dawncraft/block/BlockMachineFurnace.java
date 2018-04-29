package io.github.dawncraft.block;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.block.BlockMachine;
import io.github.dawncraft.container.GuiLoader;
import io.github.dawncraft.tileentity.TileEntityMachineFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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
public class BlockMachineFurnace extends BlockMachine
{
    public static final PropertyBool BURNING = PropertyBool.create("burning");
    
    public BlockMachineFurnace()
    {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMachineFurnace();
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
        int facing = state.getValue(FACING).getHorizontalIndex();
        int burning = state.getValue(BURNING).booleanValue() ? 4 : 0;
        return facing | burning;
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ,
            int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(BURNING, false);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileEntityMachineFurnace)
                player.openGui(Dawncraft.instance, GuiLoader.GUI_MACHINE_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = world.getTileEntity(pos);

        IItemHandler slots = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

        for (int i = slots.getSlots() - 1; i >= 0; --i)
        {
            if (slots.getStackInSlot(i) != null)
            {
                Block.spawnAsEntity(world, pos, slots.getStackInSlot(i));
                ((IItemHandlerModifiable) slots).setStackInSlot(i, null);
            }
        }
        
        slots = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

        for (int i = slots.getSlots() - 1; i >= 0; --i)
        {
            if (slots.getStackInSlot(i) != null)
            {
                Block.spawnAsEntity(world, pos, slots.getStackInSlot(i));
                ((IItemHandlerModifiable) slots).setStackInSlot(i, null);
            }
        }
        
        super.breakBlock(world, pos, state);
    }

    public static void setBlockState(boolean active, World world, BlockPos pos)
    {
        IBlockState iblockstate = world.getBlockState(pos);
        world.setBlockState(pos, world.getBlockState(pos).withProperty(BURNING, active), 3);
        
        TileEntity tileentity = world.getTileEntity(pos);
        if (tileentity != null)
        {
            tileentity.validate();
            world.setTileEntity(pos, tileentity);
        }
    }
}
