package io.github.dawncraft.command;

import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.Skill;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.TextComponentTranslation;

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
        IPlayerMagic playerMagic = serverPlayer.getCapability(CapabilityLoader.playerMagic, null);
        Skill skill = args.length >= 2 ? CommandLearn.getSkillByText(sender, args[1]) : null;
        int level = args.length >= 3 ? parseInt(args[2], 0, skill.getMaxLevel()) : 0;
        int count = args.length >= 4 ? parseInt(args[3], -1) : -1;
        NBTTagCompound nbt = null;
        if (args.length >= 5)
        {
            try
            {
                nbt = JsonToNBT.getTagFromJson(buildString(args, 4));
            }
            catch (NBTException nbtexception)
            {
                throw new CommandException("commands.forget.tagError", nbtexception.getMessage());
            }
        }

        SkillInventoryPlayer inventory = playerMagic.getSkillInventory();
        int removed = inventory.clearMatchingSkills(skill, level, count, nbt);
        playerMagic.getSkillInventoryContainer().detectAndSendChanges();
        
        if (!serverPlayer.capabilities.isCreativeMode)
        {
            playerMagic.updateHeldSkill();
        }
        
        sender.setCommandStat(CommandLoader.AFFECTED_SKILLS, removed);

        if (removed == 0)
        {
            throw new CommandException("commands.forget.failure", serverPlayer.getName());
        }
        else
        {
            if (count == 0)
            {
                sender.addChatMessage(new TextComponentTranslation("commands.forget.testing", serverPlayer.getName(), removed));
            }
            else
            {
                notifyOperators(sender, this, "commands.forget.success", serverPlayer.getName(), removed);
            }
        }
    }
    
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, this.getPlayers()) : args.length == 2 ? getListOfStringsMatchingLastWord(args, Skill.skillRegistry.getKeys()) : null;
    }
    
    protected String[] getPlayers()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }
}
