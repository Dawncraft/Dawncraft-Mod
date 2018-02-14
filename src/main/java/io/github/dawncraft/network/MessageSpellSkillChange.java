package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.container.SkillInventoryPlayer;
import io.github.dawncraft.skill.EnumSpellResult;
import io.github.dawncraft.skill.SkillStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * 用于玩家释放魔法
 *
 * @author QingChenW
 */
public class MessageSpellSkillChange implements IMessage
{
    public int slot;
    
    public MessageSpellSkillChange() {}

    public MessageSpellSkillChange(int slot)
    {
        this.slot = slot;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.slot = buf.readByte();
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(this.slot);
    }

    public static class Handler implements IMessageHandler<MessageSpellSkillChange, IMessage>
    {
        @Override
        public IMessage onMessage(final MessageSpellSkillChange message, MessageContext ctx)
        {
            if(ctx.side == Side.SERVER)
            {
                final EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
                serverPlayer.getServerForPlayer().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (serverPlayer.hasCapability(CapabilityLoader.magic, null))
                        {
                            IMagic magic = serverPlayer.getCapability(CapabilityLoader.magic, null);
                            if (message.slot < SkillInventoryPlayer.getHotbarSize())
                            {
                                if(message.slot >= 0)
                                {
                                    SkillStack skillStack = magic.getInventory().getStackInSlot(message.slot);
                                    if(skillStack != null)
                                    {
                                        magic.setSpellAction(EnumSpellResult.SELECT);
                                        magic.setSpellIndex(message.slot);
                                        magic.setSkillInSpell(skillStack);
                                        serverPlayer.markPlayerActive();
                                        return;
                                    }
                                }
                                // 玩家选中了小于0的格子???或选中的格子内没有技能
                                // 但我在客户端做了判断,理论上不会出现这种情况
                                if(magic.getSpellAction() == EnumSpellResult.SELECT || magic.getSpellAction().isSpelling())
                                {
                                    magic.clearSkillInSpell();
                                    NetworkLoader.instance.sendTo(new MessagePlayerSpelling(EnumSpellResult.CANCEL, 0, 0), serverPlayer);
                                }
                            }
                            else
                            {
                                LogLoader.logger().warn(serverPlayer.getName() + " tried to set an invalid spelled skill");
                            }
                        }
                    }
                });
            }
            else if(ctx.side == Side.CLIENT)
            {
                final EntityPlayerSP clientPlayer = Minecraft.getMinecraft().thePlayer;
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (clientPlayer.hasCapability(CapabilityLoader.magic, null))
                        {
                            IMagic magic = clientPlayer.getCapability(CapabilityLoader.magic, null);
                            if (message.slot < SkillInventoryPlayer.getHotbarSize())
                            {
                                magic.setSpellIndex(message.slot);
                            }
                        }
                    }
                });
            }
            return null;
        }
    }
}
