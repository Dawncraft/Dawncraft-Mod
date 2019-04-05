package io.github.dawncraft.command;

import java.util.ArrayList;
import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
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
        EntityPlayerMP serverPlayer = args.length == 0 ? getCommandSenderAsPlayer(sender) : getPlayer(sender, args[0]);
        try
        {
            SkillInventoryPlayer inventory = serverPlayer.getCapability(CapabilityLoader.playerMagic, null).getInventory();
            List<SkillStack> list = new ArrayList<SkillStack>();
            int count = 0;
            for(int i = 0; i < inventory.getSizeInventory(); i++)
            {
                if(inventory.getStackInSlot(i) != null) count++;
                list.add(null);
            }
            inventory.clear();
            NetworkLoader.instance.sendTo(new MessageWindowSkills(0, list), serverPlayer);
            notifyOperators(sender, this, "commands.forget.success", new Object[] {serverPlayer.getName(), count});
        }
        catch(Exception e)
        {
            throw new CommandException("commands.forget.failure", new Object[] {serverPlayer.getName()});
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
