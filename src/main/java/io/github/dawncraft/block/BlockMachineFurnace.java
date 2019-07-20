package io.github.dawncraft.block;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.block.BlockMachine;
import io.github.dawncraft.container.GuiLoader;
import io.github.dawncraft.tileentity.TileEntityMachineFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * Electric furnace
 *
 * @author QingChenW
 */
public class BlockMachineFurnace extends BlockMachine
{
    public BlockMachineFurnace()
    {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(WORKING, false));
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMachineFurnace();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
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
}
