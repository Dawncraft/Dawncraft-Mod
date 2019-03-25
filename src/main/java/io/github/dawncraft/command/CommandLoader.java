package io.github.dawncraft.command;

import net.minecraft.command.CommandBase;

import java.util.List;

/**
 * Register commands.
 *
 * @author QingChenW
 */
public class CommandLoader
{
    public static void initCommands(List<CommandBase> commands)
    {
        commands.add(new CommandMagic());
        commands.add(new CommandLearn());
        commands.add(new CommandForget());
        commands.add(new CommandTalent());
    }
}
