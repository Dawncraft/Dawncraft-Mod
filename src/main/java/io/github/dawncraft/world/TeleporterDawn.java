package io.github.dawncraft.world;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterDawn extends Teleporter
{
    private final WorldServer worldServerInstance;
    private final Random random;

    public TeleporterDawn(WorldServer world)
    {
        super(world);
        this.worldServerInstance = world;
        this.random = new Random(world.getSeed());
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw)
    {
        BlockPos blockPos;
        if (entity.dimension != 0)
        {
            blockPos = this.worldServerInstance.getTopSolidOrLiquidBlock(entity.getPosition());
        }
        else
        {
            blockPos = entity.getServer().worlds[0].getTopSolidOrLiquidBlock(entity.getPosition());
        }
        entity.moveToBlockPosAndAngles(blockPos, entity.rotationYaw, entity.rotationPitch);
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw)
    {
        return false;
    }

    @Override
    public boolean makePortal(Entity entity)
    {
        return false;
    }

    @Override
    public void removeStalePortalLocations(long time)
    {
    }
}
