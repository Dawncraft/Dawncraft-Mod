package io.github.dawncraft.core.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class DawnServerHooks
{
    public static Teleporter getTeleporter(Entity entity, int oldDimension, WorldServer fromWorld, WorldServer toWorld)
    {
        if (oldDimension < -1 || oldDimension > 1)
            return fromWorld.getDefaultTeleporter();
        return toWorld.getDefaultTeleporter();
    }
    
    public static Teleporter getTeleporter(EntityPlayerMP player, int dimension, WorldServer toWorld)
    {
        int oldDimension = player.dimension;
        if (oldDimension < -1 || oldDimension > 1)
            return MinecraftServer.getServer().worldServerForDimension(oldDimension).getDefaultTeleporter();
        return toWorld.getDefaultTeleporter();
    }
}
