package com.github.wdawning.dawncraft.command;

import java.util.List;

import com.github.wdawning.dawncraft.extend.ExtendedPlayer;
import com.github.wdawning.dawncraft.network.MessageMagic;
import com.github.wdawning.dawncraft.network.NetworkLoader;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class CommandMagic extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "magic";
    }

	@Override
	public String getCommandUsage(ICommandSender sender) {
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
			entityPlayerMP.addChatMessage(new ChatComponentTranslation("commands.magic.view", 
					ExtendedPlayer.get(entityPlayerMP).getMana()));
		}
		else if(args[0].equals("set"))
		{
			int i = parseInt(args[1], 0);
			if(i >= 0 && i <= 20)
			{
				ExtendedPlayer.get(entityPlayerMP).setMana(i);
				entityPlayerMP.addChatMessage(new ChatComponentTranslation("commands.magic.set", 
						ExtendedPlayer.get(entityPlayerMP).getMana()));
				
                NetworkLoader.instance.sendTo(new MessageMagic(i), entityPlayerMP);
			}
			else
			{
		        throw new WrongUsageException("commands.magic.usage", new Object[0]);
			}
		}
		else if(args[0].equals("reset"))
		{
			ExtendedPlayer.get(entityPlayerMP).replenishMana();
			entityPlayerMP.addChatMessage(new ChatComponentTranslation("commands.magic.reset"));
			
            NetworkLoader.instance.sendTo(new MessageMagic(20), entityPlayerMP);
		}
		else
		{
	        throw new WrongUsageException("commands.magic.usage", new Object[0]);
		}
		}
		else
		{
	        throw new WrongUsageException("commands.magic.usage", new Object[0]);
		}
	}
	
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, new String[] {"view", "set", "reset"});
        }
        return null;
    }
}
