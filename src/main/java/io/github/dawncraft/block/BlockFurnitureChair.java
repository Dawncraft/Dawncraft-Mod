package io.github.dawncraft.block;

import io.github.dawncraft.api.block.BlockFurniture;
import io.github.dawncraft.entity.EntityUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Chair
 *
 * @author QingChenW
 */
public class BlockFurnitureChair extends BlockFurniture
{
    public BlockFurnitureChair(EnumMaterialType type)
    {
        super(type);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return EntityUtils.sitAtPosition(world, pos, player, 0.5D);
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        return EntityUtils.hasSittable(world, pos) ? 15 : 0;
    }
}
