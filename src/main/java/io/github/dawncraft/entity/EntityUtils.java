package io.github.dawncraft.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityUtils
{
    public static String getEntityName(Class<? extends Entity> entity)
    {
        EntityEntry entry = EntityRegistry.getEntry(entity);
        return entry == null ? null : entry.getName();
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
