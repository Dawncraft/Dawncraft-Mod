package io.github.dawncraft.network;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.config.LogLoader;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
byte[] array = ...;
ByteBuf buf = Unpooled.wrappedBuffer(array);
FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(buf), "dawncraft");
channel.sendToServer(packet);

 * @author QingChenW
 */
public class CustomPacketHandler
{
    public static final FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(Dawncraft.MODID + "_custom");

    public CustomPacketHandler()
    {
        channel.register(this);
    }

    @EventHandler
    public void onClientPacket(ClientCustomPacketEvent event)
    {
        LogLoader.logger().info(new String(event.getPacket().payload().array()));
    }

    @EventHandler
    public void onServerPacket(ServerCustomPacketEvent event)
    {

    }
}
