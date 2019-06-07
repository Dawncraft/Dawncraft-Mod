package io.github.dawncraft.network;

import io.github.dawncraft.Dawncraft;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkLoader
{
    public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(Dawncraft.MODID);
    private static int nextID = 0;
    
    public static void initNetwork()
    {
        registerMessage(MessageUpdateMana.class, MessageUpdateMana.Handler.class, Side.CLIENT);
        registerMessage(MessageWindowSkills.class, MessageWindowSkills.Handler.class, Side.CLIENT);
        registerMessage(MessageSetSkillSlot.class, MessageSetSkillSlot.Handler.class, Side.CLIENT);
        registerMessage(MessageClickSkillWindow.class, MessageClickSkillWindow.Handler.class, Side.SERVER);
        registerMessage(MessageOpenSkillInventory.class, MessageOpenSkillInventory.Handler.class, Side.SERVER);
        registerMessage(MessageSpellCooldown.class, MessageSpellCooldown.Handler.class, Side.CLIENT);
        registerMessage(MessageSpellSkillChange.class, MessageSpellSkillChange.Handler.class, Side.CLIENT);
        registerMessage(MessageSpellSkillChange.class, MessageSpellSkillChange.Handler.class, Side.SERVER);
        registerMessage(MessagePlayerSpelling.class, MessagePlayerSpelling.Handler.class, Side.CLIENT);
        registerMessage(MessageActionMessage.class, MessageActionMessage.Handler.class, Side.CLIENT);
        
        MinecraftForge.EVENT_BUS.register(new CustomPacketHandler());
    }
    
    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
            Class<REQ> requestMessage, Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Side handlerSide)
    {
        instance.registerMessage(messageHandler, requestMessage, nextID++, handlerSide);
    }
}
