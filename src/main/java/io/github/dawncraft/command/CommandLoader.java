package io.github.dawncraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandResultStats;

import java.util.List;

import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.util.DawnEnumHelper;

/**
 * Register commands.
 *
 * @author QingChenW
 */
public class CommandLoader
{
    public static final CommandResultStats.Type AFFECTED_SKILLS = DawnEnumHelper.addCommandResultType("AFFECTED_SKILLS", "AffectedSkills");
    
    public static void initReflections()
    {
        if (AFFECTED_SKILLS == null)
        {
            LogLoader.logger().error("Cannot add CommandResultStats.Type AFFECTED_SKILLS.");
        }
    }
    
    public static void initCommands(List<CommandBase> commands)
    {
        commands.add(new CommandMagic());
        commands.add(new CommandLearn());
        commands.add(new CommandForget());
        commands.add(new CommandTalent());
    }
}
