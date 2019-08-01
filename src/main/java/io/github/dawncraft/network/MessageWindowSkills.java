package io.github.dawncraft.network;

import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.container.SkillContainer;
import io.github.dawncraft.skill.SkillStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Update the skills in a skill container.
 *
 * @author QingChenW
 */
public class MessageWindowSkills implements IMessage
{
    private int windowId;
    private SkillStack[] skillStacks;

    public MessageWindowSkills() {}

    public MessageWindowSkills(int windowId, List<SkillStack> stacks)
    {
        this.windowId = windowId;
        this.skillStacks = stacks.toArray(new SkillStack[0]);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.windowId = buf.readUnsignedByte();
        this.skillStacks = new SkillStack[buf.readShort()];

        for (int i = 0; i < this.skillStacks.length; ++i)
        {
            this.skillStacks[i] = DawnByteBufUtils.readSkillStack(buf);
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(this.windowId);
        buf.writeShort(this.skillStacks.length);

        for (SkillStack stack : this.skillStacks)
        {
            DawnByteBufUtils.writeSkillStack(buf, stack);
        }
    }

    public static class Handler implements IMessageHandler<MessageWindowSkills, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageWindowSkills message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().player;
                        IPlayerMagic playerMagic = player.getCapability(CapabilityLoader.PLAYER_MAGIC, null);

                        if (message.windowId == 0)
                        {
                            playerMagic.getSkillInventoryContainer().putSkillStacksInSlots(message.skillStacks);
                        }
                        else if (player.openContainer instanceof SkillContainer && message.windowId == player.openContainer.windowId)
                        {
                            ((SkillContainer) player.openContainer).putSkillStacksInSlots(message.skillStacks);
                        }
                    }
                });
            }
            return null;
        }
    }
}
