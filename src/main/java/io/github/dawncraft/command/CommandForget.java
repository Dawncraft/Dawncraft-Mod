package io.github.dawncraft.command;

import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.container.SkillInventoryPlayer;
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
                // TODO 待Skill Container实现之后,你就满血复活啦
                /*                MessageWindowSkills message = new MessageWindowSkills();
                message.nbt.setTag("Skills", inventory.writeToNBT(new NBTTagList()));
                NetworkLoader.instance.sendTo(message, entityPlayerMP);*/
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
