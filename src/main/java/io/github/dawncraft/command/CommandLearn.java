package io.github.dawncraft.command;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class CommandLearn extends CommandBase
{
    @Override
    public String getName()
    {
        return "learn";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands.learn.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length >= 2)
        {
            EntityPlayerMP serverPlayer = getPlayer(server, sender, args[0]);
            IPlayerMagic playerMagic = serverPlayer.getCapability(CapabilityLoader.PLAYER_MAGIC, null);
            Skill skill = getSkillByText(sender, args[1]);
            int level = args.length >= 3 ? parseInt(args[2], 1, skill.getMaxLevel()) : 1;
            SkillStack skillStack = new SkillStack(skill, level);
            if (args.length >= 4)
            {
                try
                {
                    skillStack.setTagCompound(JsonToNBT.getTagFromJson(buildString(args, 3)));
                }
                catch (NBTException exception)
                {
                    throw new CommandException("commands.learn.tagError", exception.getMessage());
                }
            }

            if (playerMagic.getSkillInventory().addSkillStackToInventory(skillStack))
            {
                serverPlayer.world.playSound(null, serverPlayer.posX, serverPlayer.posY, serverPlayer.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((serverPlayer.getRNG().nextFloat() - serverPlayer.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                playerMagic.getSkillInventoryContainer().detectAndSendChanges();
                sender.setCommandStat(CommandInit.AFFECTED_SKILLS, 1);
                notifyCommandListener(sender, this, "commands.learn.success", skillStack.getTextComponent(), level, serverPlayer.getName());
            }
            else throw new CommandException("commands.learn.full", serverPlayer.getName());
        }
        else throw new WrongUsageException("commands.learn.usage");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
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

    public static Skill getSkillByText(ICommandSender sender, String id) throws NumberInvalidException
    {
        Skill skill = Skill.getByNameOrId(id);

        if (skill == null)
        {
            throw new NumberInvalidException("commands.learn.skill.notFound", new ResourceLocation(id));
        }
        else
        {
            return skill;
        }
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
}
