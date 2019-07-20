package io.github.dawncraft.block;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.block.BlockMachine;
import io.github.dawncraft.container.GuiLoader;
import io.github.dawncraft.tileentity.TileEntityEnergyGenerator;
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
 * Energy generator
 *
 * @author QingChenW
 */
public class BlockEnergyGenerator extends BlockMachine
{
    public EnumGeneratorType type;

    public BlockEnergyGenerator(EnumGeneratorType generatorType)
    {
        super();
        this.type = generatorType;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityEnergyGenerator();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            int id;
            switch (this.type)
            {
            default:
            case HEAT:
                id = GuiLoader.GUI_HEAT_GENERATOR;
                break;
            }
            player.openGui(Dawncraft.instance, id, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = world.getTileEntity(pos);

        if (this.type == EnumGeneratorType.HEAT)
        {
            IItemHandler slots = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

            for (int i = slots.getSlots() - 1; i >= 0; --i)
            {
                if (slots.getStackInSlot(i) != null)
                {
                    Block.spawnAsEntity(world, pos, slots.getStackInSlot(i));
                    ((IItemHandlerModifiable) slots).setStackInSlot(i, null);
                }
            }
        }

        super.breakBlock(world, pos, state);
    }

    /**
     * heat, fluid, solar, wind, nuclear, magic
     *
     * @author QingChenW
     */
    public enum EnumGeneratorType
    {
        HEAT, FLUID, SOLAR, WIND, NUCLEAR, MAGIC;
    }
}
