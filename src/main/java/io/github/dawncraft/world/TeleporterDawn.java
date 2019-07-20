package io.github.dawncraft.world;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;

public class TeleporterDawn implements ITeleporter
{
    private final WorldServer world;
    private final Random random;

    public TeleporterDawn(WorldServer world)
    {
        this.world = world;
        this.random = new Random(world.getSeed());
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw)
    {
        if (entity instanceof EntityPlayerMP)
            this.placeInPortal(entity, yaw);
        else
            this.placeInExistingPortal(entity, yaw);
    }

    public void placeInPortal(Entity entity, float rotationYaw)
    {
        BlockPos blockPos;
        if (entity.dimension != 0)
        {
            blockPos = this.world.getTopSolidOrLiquidBlock(entity.getPosition());
        }
        else
        {
            blockPos = entity.getServer().worlds[0].getTopSolidOrLiquidBlock(entity.getPosition());
        }
        entity.moveToBlockPosAndAngles(blockPos, entity.rotationYaw, entity.rotationPitch);
    }

    public boolean placeInExistingPortal(Entity entity, float rotationYaw)
    {
        return false;
    }

    public boolean makePortal(Entity entity)
    {
        return false;
    }

    public void removeStalePortalLocations(long time)
    {

    }
}
