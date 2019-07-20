package io.github.dawncraft.command;

import io.github.dawncraft.capability.CapabilityLoader;
import io.github.dawncraft.capability.IPlayerMagic;
import io.github.dawncraft.entity.AttributesLoader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandMagic extends CommandTreeBase
{
    public CommandMagic()
    {
        this.addSubcommand(new CommandBase()
        {
            @Override
            public String getName()
            {
                return "view";
            }

            @Override
            public int getRequiredPermissionLevel()
            {
                return CommandMagic.this.getRequiredPermissionLevel();
            }

            @Override
            public String getUsage(ICommandSender sender)
            {
                return CommandMagic.this.getUsage(sender);
            }

            @Override
            public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
            {
                EntityPlayerMP serverPlayer = CommandBase.getCommandSenderAsPlayer(sender);
                IPlayerMagic playerMagic = serverPlayer.getCapability(CapabilityLoader.playerMagic, null);
                float mana = playerMagic.getMana();
                double maxMana = playerMagic.getMaxMana();
                NBTBase nbt = CapabilityLoader.playerMagic.getStorage().writeNBT(CapabilityLoader.playerMagic, playerMagic, null);
                serverPlayer.sendMessage(new TextComponentTranslation("commands.magic.view", mana, maxMana, nbt.toString()));
            }
        });
        this.addSubcommand(new CommandBase()
        {
            @Override
            public String getName()
            {
                return "set";
            }

            @Override
            public int getRequiredPermissionLevel()
            {
                return CommandMagic.this.getRequiredPermissionLevel();
            }

            @Override
            public String getUsage(ICommandSender sender)
            {
                return CommandMagic.this.getUsage(sender);
            }

            @Override
            public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
            {
                EntityPlayerMP serverPlayer = CommandBase.getCommandSenderAsPlayer(sender);
                IPlayerMagic playerMagic = serverPlayer.getCapability(CapabilityLoader.playerMagic, null);
                int i = parseInt(args[0], 0);
                if (i >= 0 && i <= playerMagic.getMaxMana())
                {
                    playerMagic.setMana((float) AttributesLoader.maxMana.clampValue(i));
                    float mana = playerMagic.getMana();
                    serverPlayer.sendMessage(new TextComponentTranslation("commands.magic.set", mana));
                }
                else throw new WrongUsageException("commands.magic.usage");
            }
        });
        this.addSubcommand(new CommandBase()
        {
            @Override
            public String getName()
            {
                return "max";
            }

            @Override
            public int getRequiredPermissionLevel()
            {
                return CommandMagic.this.getRequiredPermissionLevel();
            }

            @Override
            public String getUsage(ICommandSender sender)
            {
                return CommandMagic.this.getUsage(sender);
            }

            @Override
            public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
            {
                EntityPlayerMP serverPlayer = CommandBase.getCommandSenderAsPlayer(sender);
                IPlayerMagic playerMagic = serverPlayer.getCapability(CapabilityLoader.playerMagic, null);
                int i = parseInt(args[0], 0);
                serverPlayer.getEntityAttribute(AttributesLoader.maxMana).setBaseValue(i);
                float mana = playerMagic.getMaxMana();
                serverPlayer.sendMessage(new TextComponentTranslation("commands.magic.max", mana));
            }
        });
        this.addSubcommand(new CommandBase()
        {
            @Override
            public String getName()
            {
                return "reset";
            }

            @Override
            public int getRequiredPermissionLevel()
            {
                return CommandMagic.this.getRequiredPermissionLevel();
            }

            @Override
            public String getUsage(ICommandSender sender)
            {
                return CommandMagic.this.getUsage(sender);
            }

            @Override
            public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
            {
                EntityPlayerMP serverPlayer = CommandBase.getCommandSenderAsPlayer(sender);
                IPlayerMagic playerMagic = serverPlayer.getCapability(CapabilityLoader.playerMagic, null);
                playerMagic.setMana(playerMagic.getMaxMana());
                serverPlayer.sendMessage(new TextComponentTranslation("commands.magic.reset"));
            }
        });
    }

    @Override
    public String getName()
    {
        return "magic";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands.magic.usage";
    }
}
