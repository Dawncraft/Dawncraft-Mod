package io.github.dawncraft.network;

import io.github.dawncraft.capability.CapabilityInit;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.client.ClientProxy;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.EnumSpellAction;
import io.github.dawncraft.skill.SkillStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Will be sent when player change his spell action.
 *
 * @author QingChenW
 */
public class MessagePlayerSpelling implements IMessage
{
    private EnumSpellAction spellAction;
    private int spellCount;

    public MessagePlayerSpelling() {}

    public MessagePlayerSpelling(EnumSpellAction type, int count)
    {
        this.spellAction = type;
        this.spellCount = count;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.spellAction = EnumSpellAction.values()[buf.readByte()];
        this.spellCount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(this.spellAction.ordinal());
        buf.writeInt(this.spellCount);
    }

    public static class Handler implements IMessageHandler<MessagePlayerSpelling, IMessage>
    {
        @Override
        public IMessage onMessage(final MessagePlayerSpelling message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT)
            {
                Minecraft.getMinecraft().addScheduledTask(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        EntityPlayer player = Minecraft.getMinecraft().player;
                        IPlayerMagic playerMagic = player.getCapability(CapabilityInit.PLAYER_MAGIC, null);
                        if (message.spellAction != EnumSpellAction.NONE)
                        {
                            int slotId = ClientProxy.getInstance().getIngameGUIDawn().skillIndex;
                            if (slotId >= 0 && slotId <= SkillInventoryPlayer.getHotbarSize())
                            {
                                SkillStack skillStack = playerMagic.getSkillInventory().getSkillStackInSlot(ClientProxy.getInstance().getIngameGUIDawn().skillIndex);
                                if (skillStack != null)
                                {
                                    playerMagic.setSkillInSpell(message.spellAction, skillStack, message.spellCount);

                                    String action = I18n.format(message.spellAction.getUnlocalizedName(), skillStack.getDisplayName());
                                    int count = message.spellAction == EnumSpellAction.PREPARE ? skillStack.getTotalPrepare() : skillStack.getMaxDuration();
                                    int color = Minecraft.getMinecraft().fontRenderer.getColorCode('a');
                                    ClientProxy.getInstance().getIngameGUIDawn().setAction(action, count, color);
                                }
                            }
                        }
                        else
                        {
                            playerMagic.clearSkillInSpell();
                            ClientProxy.getInstance().getIngameGUIDawn().setSpellIndex(-1);
                        }
                    }
                });
            }
            return null;
        }
    }
}
