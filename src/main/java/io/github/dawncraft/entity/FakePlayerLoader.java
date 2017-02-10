package io.github.dawncraft.entity;

import java.lang.ref.WeakReference;
import java.util.UUID;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * Register fake player in the class.
 * 
 * @author QingChenW
 */
public class FakePlayerLoader
{
    private static GameProfile gameProfile;
    private static WeakReference<EntityPlayerMP> fakePlayer;

    public FakePlayerLoader(FMLInitializationEvent event)
    {
        gameProfile = new GameProfile(UUID.randomUUID(), "[Dawncraft]");
        fakePlayer = new WeakReference<EntityPlayerMP>(null);
    }

    public static WeakReference<EntityPlayerMP> getFakePlayer(WorldServer server)
    {
        if (fakePlayer.get() == null)
        {
            fakePlayer = new WeakReference<EntityPlayerMP>(FakePlayerFactory.get(server, gameProfile));
        }
        else
        {
            fakePlayer.get().worldObj = server;
        }
        return fakePlayer;
    }
}
