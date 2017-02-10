package io.github.dawncraft.server.command;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.network.MessageMana;
import io.github.dawncraft.network.NetworkLoader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
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
            int mana;
            MessageMana message;
            
		    if(args[0].equals("view"))
		    {
		        if(entityPlayerMP.hasCapability(CapabilityLoader.mana, null))
		        {
		              mana = entityPlayerMP.getCapability(CapabilityLoader.mana, null).getMana();
		              entityPlayerMP.addChatMessage(new ChatComponentTranslation("commands.magic.view", mana));
		        }
		    }
		    else if(args[0].equals("set"))
		    {
		        int i = parseInt(args[1], 0);
		        if(i >= 0 && i <= 20)
		        {
		            if(entityPlayerMP.hasCapability(CapabilityLoader.mana, null))
		            {
		                 entityPlayerMP.getCapability(CapabilityLoader.mana, null).setMana(i);
		                 mana = entityPlayerMP.getCapability(CapabilityLoader.mana, null).getMana();
		                 message = new MessageMana();
		                 message.nbt.setInteger("mana", mana);
		                 NetworkLoader.instance.sendTo(message, entityPlayerMP);
		                 entityPlayerMP.addChatMessage(new ChatComponentTranslation("commands.magic.set", mana));
	                 }
		        }
		        throw new WrongUsageException("commands.magic.usage", new Object[0]);
		    }
		    else if(args[0].equals("reset"))
		    {
                if(entityPlayerMP.hasCapability(CapabilityLoader.mana, null))
                {
                     entityPlayerMP.getCapability(CapabilityLoader.mana, null).replenish();
                     message = new MessageMana();
                     message.nbt.setInteger("mana", 20);
                     NetworkLoader.instance.sendTo(message, entityPlayerMP);
                     entityPlayerMP.addChatMessage(new ChatComponentTranslation("commands.magic.reset"));
                }
		    }
		}
		else throw new WrongUsageException("commands.magic.usage", new Object[0]);
	}
	
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if(args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, new String[] {"view", "set", "reset"});
        }
        return null;
    }
}
