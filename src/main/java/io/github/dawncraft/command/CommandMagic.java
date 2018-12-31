package io.github.dawncraft.command;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.entity.AttributesLoader;
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
            EntityPlayerMP serverPlayer = CommandBase.getCommandSenderAsPlayer(sender);
            IMagic playerCap = serverPlayer.getCapability(CapabilityLoader.magic, null);
            if(args[0].equals("view"))
            {
                float mana = playerCap.getMana();
                NBTBase nbt = CapabilityLoader.magic.getStorage().writeNBT(CapabilityLoader.magic, playerCap, null);
                serverPlayer.addChatMessage(new ChatComponentTranslation("commands.magic.view",
                        mana, serverPlayer.getEntityAttribute(AttributesLoader.maxMana).getAttributeValue(), nbt.toString()));
            }
            else if(args[0].equals("set"))
            {
                int i = parseInt(args[1], 0);
                if(i >= 0 && i <= serverPlayer.getEntityAttribute(AttributesLoader.maxMana).getAttributeValue())
                {
                    playerCap.setMana((float) AttributesLoader.maxMana.clampValue(i));
                    float mana = playerCap.getMana();
                    serverPlayer.addChatMessage(new ChatComponentTranslation("commands.magic.set", mana));
                }
                else throw new WrongUsageException("commands.magic.usage", new Object[0]);
            }
            else if(args[0].equals("max"))
            {
                int i = parseInt(args[1], 0);
                serverPlayer.getEntityAttribute(AttributesLoader.maxMana).setBaseValue(i);
                float mana = playerCap.getMaxMana();
                serverPlayer.addChatMessage(new ChatComponentTranslation("commands.magic.max", mana));
            }
            else if(args[0].equals("reset"))
            {
                playerCap.setMana(playerCap.getMaxMana());
                serverPlayer.addChatMessage(new ChatComponentTranslation("commands.magic.reset"));
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
