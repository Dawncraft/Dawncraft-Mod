package io.github.dawncraft.command;

import java.util.ArrayList;
import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.container.SkillInventoryPlayer;
import io.github.dawncraft.network.MessageWindowSkills;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandForget extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "forget";
    }
    
    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
    
    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.forget.usage";
    }
    
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        EntityPlayerMP entityPlayerMP = args.length == 0 ? getCommandSenderAsPlayer(sender) : getPlayer(sender, args[0]);
        try
        {
            if(entityPlayerMP.hasCapability(CapabilityLoader.magic, null))
            {
                SkillInventoryPlayer inventory = (SkillInventoryPlayer) entityPlayerMP.getCapability(CapabilityLoader.magic, null).getInventory();
                inventory.clear();
                List<SkillStack> list = new ArrayList<SkillStack>();
                for(int i = 0; i < inventory.getSizeInventory(); i++)
                    list.add(inventory.getStackInSlot(i));
                NetworkLoader.instance.sendTo(new MessageWindowSkills(0, list), entityPlayerMP);
                notifyOperators(sender, this, "commands.forget.success", new Object[] {entityPlayerMP.getName(), 0});
            }
        }
        catch(Exception e)
        {
            throw new CommandException("commands.forget.failure", new Object[] {entityPlayerMP.getName()});
        }
    }
    
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, this.getPlayers()) : null;
    }
    
    protected String[] getPlayers()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }
}
