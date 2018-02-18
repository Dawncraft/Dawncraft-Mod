package io.github.dawncraft.command;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.entity.AttributesLoader;
import io.github.dawncraft.network.MessageUpdateMana;
import io.github.dawncraft.network.NetworkLoader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import java.util.List;

public class CommandMagic extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "magic";
    }
    
    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.magic.usage";
    }
    
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(args.length > 0)
        {
            EntityPlayerMP entityPlayerMP = CommandBase.getCommandSenderAsPlayer(sender);
            if(args[0].equals("view"))
            {
                if(entityPlayerMP.hasCapability(CapabilityLoader.magic, null))
                {
                    IMagic magic = entityPlayerMP.getCapability(CapabilityLoader.magic, null);
                    float mana = magic.getMana();
                    NBTBase nbt = CapabilityLoader.magic.getStorage().writeNBT(CapabilityLoader.magic, magic, null);
                    entityPlayerMP.addChatMessage(new ChatComponentTranslation("commands.magic.view",
                            mana, entityPlayerMP.getEntityAttribute(AttributesLoader.maxMana).getAttributeValue(), nbt.toString()));
                }
            }
            else if(args[0].equals("set"))
            {
                int i = parseInt(args[1], 0);
                if(i >= 0 && i <= entityPlayerMP.getEntityAttribute(AttributesLoader.maxMana).getAttributeValue())
                {
                    if(entityPlayerMP.hasCapability(CapabilityLoader.magic, null))
                    {
                        IMagic magic = entityPlayerMP.getCapability(CapabilityLoader.magic, null);
                        magic.setMana((float) AttributesLoader.maxMana.clampValue(i));
                        float mana = magic.getMana();
                        NetworkLoader.instance.sendTo(new MessageUpdateMana(mana), entityPlayerMP);
                        entityPlayerMP.addChatMessage(new ChatComponentTranslation("commands.magic.set", mana));
                    }
                }
                else throw new WrongUsageException("commands.magic.usage", new Object[0]);
            }
            else if(args[0].equals("max"))
            {
                int i = parseInt(args[1], 0);
                if(entityPlayerMP.hasCapability(CapabilityLoader.magic, null))
                {
                    IMagic magic = entityPlayerMP.getCapability(CapabilityLoader.magic, null);
                    entityPlayerMP.getEntityAttribute(AttributesLoader.maxMana).setBaseValue(i);
                    float mana = magic.getMaxMana();
                    entityPlayerMP.addChatMessage(new ChatComponentTranslation("commands.magic.max", mana));
                }
            }
            else if(args[0].equals("reset"))
            {
                if(entityPlayerMP.hasCapability(CapabilityLoader.magic, null))
                {
                    IMagic magic = entityPlayerMP.getCapability(CapabilityLoader.magic, null);
                    magic.replenish();
                    NetworkLoader.instance.sendTo(new MessageUpdateMana(magic.getMaxMana()), entityPlayerMP);
                    entityPlayerMP.addChatMessage(new ChatComponentTranslation("commands.magic.reset"));
                }
            }
            else throw new WrongUsageException("commands.magic.usage", new Object[0]);
        }
        else throw new WrongUsageException("commands.magic.usage", new Object[0]);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if(args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, new String[] {"view", "set", "max", "reset"});
        }
        return null;
    }
}
