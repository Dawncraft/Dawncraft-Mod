package io.github.dawncraft.network;

import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.SkillStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 用于更新Skill Container内的技能
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
        
        for (SkillStack skillstack : this.skillStacks)
        {
            DawnByteBufUtils.writeSkillStack(buf, skillstack);
        }
    }

    public static class Handler implements IMessageHandler<MessageWindowSkills, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageWindowSkills message, MessageContext ctx)
        {
            if(ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                        if (player.hasCapability(CapabilityLoader.player, null))
                        {
                            SkillInventoryPlayer inventory = (SkillInventoryPlayer) player.getCapability(CapabilityLoader.player, null).getInventory();
                            
                            if (message.windowId == 0)
                            {
                                // inventory.putStacksInSlots(message.skillStacks);// 未实现
                                for (int i = 0; i < message.skillStacks.length; ++i)
                                {
                                    inventory.setInventorySlotContents(i, message.skillStacks[i]);
                                }
                            }
                            // 未实现
                            /*
                            else if (message.windowId == player.openContainer.windowId)
                            {
                                player.openContainer.putStacksInSlots(message.skillStacks);
                            }
                             */
                        }
                    }
                });
            }
            return null;
        }
    }
}
