package fr.ulity.moderation.bukkit.commands;


import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
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
                    Lang.prepare("commands.staffchat.output")
                    .variable("player", sender.getName())
                    .variable("mesagge", Text.fullColor(args))
                    .getOutput(),
                    "ulity.mod"
            );
        else
            setStatus(Status.SYNTAX);
    }


}
