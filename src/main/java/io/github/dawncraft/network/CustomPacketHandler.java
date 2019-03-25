package io.github.dawncraft.network;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;

public class CustomPacketHandler
{
    @EventHandler
    public void onChannelEvent(FMLEventChannel event)
    {
        event.register(this);
    }
    
    @EventHandler
    public void onClientCustomPacketReceived(ClientCustomPacketEvent event)
    {

    }

    @EventHandler
    public void onServerCustomPacketReceived(ServerCustomPacketEvent event)
    {

    }
}
