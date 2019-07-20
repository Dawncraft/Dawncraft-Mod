package io.github.dawncraft.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityUtils
{
    public static String getEntityStringFromClass(Class<? extends Entity> entity)
    {
        return EntityList.getKey(entity).toString();
    }

    public static boolean hasSittable(World world, BlockPos pos)
    {
        List<EntitySittable> list = world.getEntitiesWithinAABB(EntitySittable.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).expand(1D, 1D, 1D));
        for (EntitySittable sittable : list)
        {
            if (sittable.blockPos.equals(pos))
            {
                return !sittable.getPassengers().isEmpty();
            }
        }
        return false;
    }

    // TODO 坐下有bug
    public static boolean sitAtPosition(World world, BlockPos pos, Entity entity, double yOffset)
    {
        if (!EntityUtils.hasSittable(world, pos))
        {
            EntitySittable sittable = new EntitySittable(world, pos, yOffset);
            world.spawnEntity(sittable);
            entity.startRiding(sittable);
            return true;
        }
        return false;
    }
}
