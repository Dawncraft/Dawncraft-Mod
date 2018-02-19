package io.github.dawncraft.network;

import io.github.dawncraft.Dawncraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkLoader
{
    public static SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(Dawncraft.MODID);
    private static int nextID = 0;
    
    public NetworkLoader(FMLInitializationEvent event)
    {
        registerMessage(MessageUpdateMana.Handler.class, MessageUpdateMana.class, Side.CLIENT);

        registerMessage(MessageSpellSkillChange.Handler.class, MessageSpellSkillChange.class, Side.CLIENT);
        registerMessage(MessageSpellSkillChange.Handler.class, MessageSpellSkillChange.class, Side.SERVER);
        registerMessage(MessagePlayerSpelling.Handler.class, MessagePlayerSpelling.class, Side.CLIENT);
        registerMessage(MessageSpellCooldown.Handler.class, MessageSpellCooldown.class, Side.CLIENT);

        registerMessage(MessageSetSlot.Handler.class, MessageSetSlot.class, Side.CLIENT);
        registerMessage(MessageWindowSkills.Handler.class, MessageWindowSkills.class, Side.CLIENT);
    }
    
    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
            Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side handlerSide)
    {
        instance.registerMessage(messageHandler, requestMessageType, nextID++, handlerSide);
    }
}
