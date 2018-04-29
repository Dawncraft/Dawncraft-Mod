package io.github.dawncraft.util;

import java.util.List;

import io.github.dawncraft.entity.EntitySittable;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityUtils
{
    public static boolean hasSittable(World world, BlockPos pos)
    {
        List<EntitySittable> list = world.getEntitiesWithinAABB(EntitySittable.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).expand(1D, 1D, 1D));
        for (EntitySittable sittable : list)
        {
            if (sittable.blockPos.equals(pos))
            {
                return sittable.riddenByEntity != null;
            }
        }
        return false;
    }

    // TODO 坐下有bug
    public static boolean sitAtPosition(World world, BlockPos pos, Entity entity, double yOffset)
    {
        if(!EntityUtils.hasSittable(world, pos))
        {
            EntitySittable sittable = new EntitySittable(world, pos, yOffset);
            world.spawnEntityInWorld(sittable);
            entity.mountEntity(sittable);
            return true;
        }
        return false;
    }
}
