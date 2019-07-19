package io.github.dawncraft.network;

import java.io.IOException;

import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.client.sound.SoundInit;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSpellFeedback implements IMessage
{
    public boolean useActionbar;
    public ITextComponent textComponent;
    public boolean isFailed;

    public MessageSpellFeedback() {}

    public MessageSpellFeedback(boolean useActionbar, ITextComponent reason, boolean failed)
    {
	this.useActionbar = useActionbar;
	this.textComponent = reason;
	this.isFailed = failed;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
	PacketBuffer pb = new PacketBuffer(buf);
	try
	{
	    this.useActionbar = pb.readBoolean();
	    this.textComponent = pb.readTextComponent();
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
	pb.writeBoolean(this.useActionbar);
	pb.writeTextComponent(this.textComponent);
	pb.writeBoolean(this.isFailed);
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
			    ClientProxy.getInstance().getIngameGUIDawn().setActionMessage(message.textComponent, Minecraft.getMinecraft().fontRenderer.getColorCode(color));
			}
			else
			{
			    Minecraft.getMinecraft().ingameGUI.setOverlayMessage(message.textComponent, false);
			    if (message.isFailed)
			    {
				Minecraft.getMinecraft().getSoundHandler().playSound(SoundInit.createSound(SoundInit.GUI_ERROR));
			    }
			}
		    }
		});
	    }
	    return null;
	}
    }
}
