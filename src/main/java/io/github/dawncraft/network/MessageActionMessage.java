package io.github.dawncraft.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

import io.github.dawncraft.client.ClientProxy;
import io.netty.buffer.ByteBuf;

public class MessageActionMessage implements IMessage
{
    public IChatComponent chatComponent;
    public EnumChatFormatting foregroundColor;

    public MessageActionMessage() {}
    
    public MessageActionMessage(IChatComponent component, EnumChatFormatting color)
    {
        super();
        this.chatComponent = component;
        this.foregroundColor = color;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        PacketBuffer pb = new PacketBuffer(buf);
        try
        {
            this.chatComponent = pb.readChatComponent();
            this.foregroundColor = pb.readEnumValue(EnumChatFormatting.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        PacketBuffer pb = new PacketBuffer(buf);
        try
        {
            pb.writeChatComponent(this.chatComponent);
            pb.writeEnumValue(this.foregroundColor);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<MessageActionMessage, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageActionMessage message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        ClientProxy.getInstance().getIngameGUIDawn().setActionMessage(message.chatComponent.getFormattedText(), Minecraft.getMinecraft().fontRendererObj.getColorCode(message.foregroundColor.toString().charAt(1)));
                    }
                });
            }
            return null;
        }
    }
}
