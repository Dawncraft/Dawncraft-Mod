package io.github.dawncraft.command;

import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.entity.player.SkillInventoryPlayer;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

public class CommandLearn extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "learn";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
    
    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.learn.usage";
    }
    
    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length >= 2)
        {
            EntityPlayerMP serverPlayer = getPlayer(sender, args[0]);
            Skill skill = getSkillByText(sender, args[1]);
            int level = args.length >= 3 ? parseInt(args[2], 1, skill.getMaxLevel()) : 1;
            SkillStack skillStack = new SkillStack(skill, level);
            if (args.length >= 4)
            {
                String s = getChatComponentFromNthArg(sender, args, 3).getUnformattedText();
                try
                {
                    skillStack.setTagCompound(JsonToNBT.getTagFromJson(s));
                }
                catch (NBTException nbtexception)
                {
                    throw new CommandException("commands.learn.tagError", nbtexception.getMessage());
                }
            }

            SkillInventoryPlayer inventory = serverPlayer.getCapability(CapabilityLoader.playerMagic, null).getSkillInventory();
            if (inventory.addSkillStackToInventory(skillStack))
            {
                serverPlayer.worldObj.playSoundAtEntity(serverPlayer, "random.pop", 0.2F, ((serverPlayer.getRNG().nextFloat() - serverPlayer.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                serverPlayer.getCapability(CapabilityLoader.playerMagic, null).getSkillInventoryContainer().detectAndSendChanges();
                notifyOperators(sender, this, "commands.learn.success", skillStack.getChatComponent(), Integer.valueOf(level), serverPlayer.getName());
            }
            else throw new CommandException("commands.learn.full", serverPlayer.getName());
        }
        else throw new WrongUsageException("commands.learn.usage", new Object[0]);
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
}
