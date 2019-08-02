package io.github.dawncraft.command;

import java.util.List;

import io.github.dawncraft.config.LogLoader;
import io.github.dawncraft.util.DawnEnumHelper;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommand;

/**
 * Register some commands.
 *
 * @author QingChenW
 */
public class CommandInit
{
    public static final CommandResultStats.Type AFFECTED_SKILLS = DawnEnumHelper.addCommandResultType("AFFECTED_SKILLS", "AffectedSkills");

    public static void initReflections()
    {
        if (AFFECTED_SKILLS == null)
        {
            LogLoader.logger().error("Cannot add CommandResultStats.Type AFFECTED_SKILLS.");
        }
    }

    public static void registerCommands(List<ICommand> commands)
    {
        commands.add(new CommandMagic());
        commands.add(new CommandLearn());
        commands.add(new CommandForget());
        commands.add(new CommandTalent());
    }
}
