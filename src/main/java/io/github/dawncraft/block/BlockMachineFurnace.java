package io.github.dawncraft.block;

import io.github.dawncraft.dawncraft;
import io.github.dawncraft.block.base.BlockMachineBase;
import io.github.dawncraft.tileentity.TileEntityEnergyHeatGen;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
    public BlockMachineFurnace()
    {
        super();
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityEnergyHeatGen();
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityFurnace)
            {
                playerIn.openGui(dawncraft.instance, 0/* TODO 电炉方块gui的id和te*/, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }
}
