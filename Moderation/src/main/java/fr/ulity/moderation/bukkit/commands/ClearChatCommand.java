package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import fr.ulity.moderation.bukkit.MainModBukkit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class ClearChatCommand extends CommandManager.Assisted {

    public ClearChatCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "clearchat");
        setAliases(Arrays.asList("clc", "cc"));
        addPermission("ulity.mod.chat.clear");
        addPermission("ulity.mod.chat");
        addArrayTabbComplete(0, "ulity.mod.clearchat", new String[]{}, Lang.getStringArray("commands.clearChat.reasons_predefined"));
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        StringBuilder looped = new StringBuilder();
        for (int i = 0; i<100; i++)
            looped.append(" \n");
        MainModBukkit.server.broadcastMessage(looped.toString());

        String reason = ( args.length == 0) ? Lang.get("commands.clearChat.expressions.unknown_reason") : Text.fullColor(args);
        Bukkit.broadcastMessage(
                Lang.prepare("commands.clearChat.expressions.notification")
                        .variable("sender", sender.getName().replace("Console", Lang.get("commands.ClearChat.expressions.console")))
                        .variable("reason", reason)
                        .getOutput()
        );
    }
}