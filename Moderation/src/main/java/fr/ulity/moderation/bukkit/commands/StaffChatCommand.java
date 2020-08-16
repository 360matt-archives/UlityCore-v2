package fr.ulity.moderation.bukkit.commands;


import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.core.utils.TextV2;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class StaffChatCommand extends CommandManager.Assisted {
    public StaffChatCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "staffchat");
        addPermission("ulity.mod.staffchat");
        addAliases("sc");
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1)
            Bukkit.broadcast(
                    LangBukkit.prepare("commands.staffchat.output")
                    .variable("player", sender.getName())
                    .variable("mesagge", new TextV2(args).setColored().outputString())
                    .getOutput(),
                    "ulity.mod"
            );
        else
            setStatus(Status.SYNTAX);
    }


}
