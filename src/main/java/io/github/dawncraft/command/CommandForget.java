package io.github.dawncraft.command;

import java.util.Collections;
import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.skill.Skill;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandForget extends CommandBase
{
    @Override
    public String getName()
    {
        return "forget";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands.forget.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        EntityPlayerMP serverPlayer = args.length == 0 ? getCommandSenderAsPlayer(sender) : getPlayer(server, sender, args[0]);
        IPlayerMagic playerMagic = serverPlayer.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
        Skill skill = args.length >= 2 ? CommandLearn.getSkillByText(sender, args[1]) : null;
        int level = args.length >= 3 ? parseInt(args[2], 0, skill.getMaxLevel()) : 0;
        int count = args.length >= 4 ? parseInt(args[3], -1) : -1;
        NBTTagCompound tagCompound = null;
        if (args.length >= 5)
        {
            try
            {
                tagCompound = JsonToNBT.getTagFromJson(buildString(args, 4));
            }
            catch (NBTException exception)
            {
                throw new CommandException("commands.forget.tagError", exception.getMessage());
            }
        }

        int removed = playerMagic.getSkillInventory().clearMatchingSkills(skill, level, count, tagCompound);
        playerMagic.getSkillInventoryContainer().detectAndSendChanges();

        if (!serverPlayer.capabilities.isCreativeMode)
        {
            playerMagic.updateHeldSkill();
        }

        sender.setCommandStat(CommandInit.AFFECTED_SKILLS, removed);

        if (removed == 0)
        {
            throw new CommandException("commands.forget.failure", serverPlayer.getName());
        }
        else
        {
            if (count == 0)
            {
                sender.sendMessage(new TextComponentTranslation("commands.forget.testing", serverPlayer.getName(), removed));
            }
            else
            {
                notifyCommandListener(sender, this, "commands.forget.success", serverPlayer.getName(), removed);
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        else if (args.length == 2)
        {
            return getListOfStringsMatchingLastWord(args, Skill.REGISTRY.getKeys());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
}
