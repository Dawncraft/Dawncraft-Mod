package io.github.dawncraft.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

import io.github.dawncraft.Dawncraft;
import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.client.sound.SoundLoader;
import io.netty.buffer.ByteBuf;

public class MessageSpellFeedback implements IMessage
{
    public boolean useActionbar;
    public IChatComponent chatComponent;
    public boolean isFailed;

    public MessageSpellFeedback() {}
    
    public MessageSpellFeedback(boolean useActionbar, IChatComponent component, boolean failed)
    {
        this.useActionbar = useActionbar;
        this.chatComponent = component;
        this.isFailed = failed;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        PacketBuffer pb = new PacketBuffer(buf);
        try
        {
            this.useActionbar = pb.readBoolean();
            this.chatComponent = pb.readChatComponent();
            this.isFailed = pb.readBoolean();
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
            pb.writeBoolean(this.useActionbar);
            pb.writeChatComponent(this.chatComponent);
            pb.writeBoolean(this.isFailed);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<MessageSpellFeedback, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageSpellFeedback message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (message.useActionbar)
                        {
                            char color = !message.isFailed ? 'a' : 'c';
                            ClientProxy.getInstance().getIngameGUIDawn().setActionMessage(message.chatComponent, Minecraft.getMinecraft().fontRendererObj.getColorCode(color));
                        }
                        else
                        {
                            Minecraft.getMinecraft().ingameGUI.setRecordPlaying(message.chatComponent, false);
                            if (message.isFailed)
                            {
                                Minecraft.getMinecraft().getSoundHandler().playSound(SoundLoader.createSound(new ResourceLocation(Dawncraft.MODID, "gui.error")));
                            }
                        }
                    }
                });
            }
            return null;
        }
    }
}
