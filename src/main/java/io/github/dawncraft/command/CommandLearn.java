package io.github.dawncraft.command;

import java.util.List;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IMagic;
import io.github.dawncraft.container.SkillInventoryPlayer;
import io.github.dawncraft.network.MessagePlayerSkills;
import io.github.dawncraft.network.NetworkLoader;
import io.github.dawncraft.skill.Skill;
import io.github.dawncraft.skill.SkillStack;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagList;
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
            EntityPlayerMP entityPlayerMP = getPlayer(sender, args[0]);
            Skill skill = getSkillByText(sender, args[1]);
            
            if(skill != null)
            {
                int level = args.length >= 3 ? parseInt(args[2], 0, skill.getMaxLevel()) : 0;
                SkillStack skillstack = new SkillStack(skill, level);
                if(entityPlayerMP.hasCapability(CapabilityLoader.magic, null))
                {
                    IMagic magic = entityPlayerMP.getCapability(CapabilityLoader.magic, null);
                    SkillInventoryPlayer inventory = (SkillInventoryPlayer) magic.getInventory();
                    if(inventory.addSkillStackToInventory(skillstack))
                    {
                        entityPlayerMP.worldObj.playSoundAtEntity(entityPlayerMP, "random.pop", 0.2F, ((entityPlayerMP.getRNG().nextFloat() - entityPlayerMP.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                        MessagePlayerSkills message = new MessagePlayerSkills();
                        message.nbt.setTag("Skills", inventory.writeToNBT(new NBTTagList()));
                        NetworkLoader.instance.sendTo(message, entityPlayerMP);
                        notifyOperators(sender, this, "commands.learn.success", new Object[] {skillstack.getChatComponent(), Integer.valueOf(level), entityPlayerMP.getName()});
                    }
                    else throw new CommandException("commands.learn.full", new Object[] {entityPlayerMP.getName()});
                }
            }
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
            throw new NumberInvalidException("commands.learn.skill.notFound", new Object[] {new ResourceLocation(id)});
        }
        else
        {
            return skill;
        }
    }
}
