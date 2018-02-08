package io.github.dawncraft.block;

import java.util.List;

import io.github.dawncraft.api.block.BlockFurnitureBase;
import io.github.dawncraft.entity.EntitySittable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author QingChenW
 *
 */
public class BlockFurnitureChair extends BlockFurnitureBase
{
    public BlockFurnitureChair(Material materialIn, SoundType sound)
    {
        super(materialIn, sound);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
            EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!isSomeoneSitting(worldIn, pos.getX(), pos.getY(), pos.getZ()))
        {
            EntitySittable sittable = new EntitySittable(worldIn, pos.getX(), pos.getY(), pos.getZ(), 0.5D);
            worldIn.spawnEntityInWorld(sittable);
            playerIn.mountEntity(sittable);
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
    }
    
    public static boolean isSomeoneSitting(World world, double x, double y, double z)
    {
        List<EntitySittable> list = world.getEntitiesWithinAABB(EntitySittable.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D, 1D, 1D));
        for (EntitySittable sittable : list)
        {
            if (sittable.blockPosX == x && sittable.blockPosY == y && sittable.blockPosZ == z)
            {
                return sittable.riddenByEntity != null;
            }
        }
        return false;
    }
}
