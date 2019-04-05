package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.entity.player.PlayerUtils;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.EnumSpellAction;
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
                        IPlayerMagic playerCap = serverPlayer.getCapability(CapabilityLoader.playerMagic, null);
                        if (message.slot >= 0 && message.slot < SkillInventoryPlayer.getHotbarSize())
                        {
                            SkillStack skillStack = playerCap.getInventory().getStackInSlot(message.slot);
                            if(skillStack != null)
                            {
                                if(playerCap.getCooldownTracker().getPublicCooldownCount() <= 0)
                                {
                                    playerCap.setSpellIndex(message.slot);
                                    playerCap.setSkillInSpell(skillStack);
                                    serverPlayer.markPlayerActive();
                                }
                                else
                                {
                                    playerCap.clearSkillInSpell();
                                    PlayerUtils.globleCooldown(serverPlayer);
                                }
                            }
                            else if(playerCap.getSpellAction() != EnumSpellAction.NONE)
                            {
                                playerCap.clearSkillInSpell();
                                PlayerUtils.cancel(serverPlayer);
                            }
                        }
                        else
                        {
                            LogLoader.logger().warn(serverPlayer.getName() + " tried to set an invalid spelled skill");
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
                        IPlayerMagic playerCap = clientPlayer.getCapability(CapabilityLoader.playerMagic, null);
                        if (message.slot >= 0 && message.slot < SkillInventoryPlayer.getHotbarSize())
                        {
                            playerCap.setSpellIndex(message.slot);
                        }
                        else
                        {
                            LogLoader.logger().warn(clientPlayer.getName() + " tried to set an invalid spelled skill");
                        }
                    }
                });
            }
            return null;
        }
    }
}
