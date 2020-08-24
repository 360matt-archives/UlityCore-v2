package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Text;
import fr.ulity.moderation.bukkit.MainModBukkit;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ClearchatCmd extends CommandBukkit {
    public ClearchatCmd () {
        super("clc");
        setPermission("ulity.mod.chat");
        setAliases(Arrays.asList("clearchat"));
        addArrayTabbComplete(0, "ulity.mod.clearchat", new String[]{}, Lang.getArray("commands.clearChat.reasons_predefined"));
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        StringBuilder looped = new StringBuilder();
        for (int i = 0; i<100; i++)
            looped.append(" \n");
        MainModBukkit.server.broadcastMessage(looped.toString());

        String reason = ( args.length == 0) ? Lang.get("commands.clearChat.expressions.unknown_reason")
                : new Text(args)
                .setColored()
                .setNewLine()
                .outputString();
        Bukkit.broadcastMessage(
                Lang.prepare("commands.clearChat.expressions.notification")
                        .variable("sender", sender.getName().replace("Console", Lang.get("commands.ClearChat.expressions.console")))
                        .variable("reason", reason)
                        .getOutput()
        );
    }
}
