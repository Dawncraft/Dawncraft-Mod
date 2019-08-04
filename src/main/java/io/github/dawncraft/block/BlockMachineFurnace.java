package io.github.dawncraft.block;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.api.block.BlockMachine;
import io.github.dawncraft.container.DawnGuiHandler;
import io.github.dawncraft.stats.StatInit;
import io.github.dawncraft.tileentity.TileEntityMachineFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
            {
                player.openGui(Dawncraft.instance, DawnGuiHandler.GUI_MACHINE_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
                player.addStat(StatInit.MACHINE_INTERACTION);
            }
        }
        return true;
    }
}
