package io.github.dawncraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import java.util.Random;

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
            blockPos = MinecraftServer.getServer().worldServerForDimension(0).getTopSolidOrLiquidBlock(entity.getPosition());
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
