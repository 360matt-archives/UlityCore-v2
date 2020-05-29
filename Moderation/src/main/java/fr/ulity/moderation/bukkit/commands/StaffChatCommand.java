package fr.ulity.moderation.bukkit.commands;


import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


public class StaffChatCommand extends CommandManager {

    public StaffChatCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "staffchat");
        addDescription(Lang.get("commands.staffchat.description"));
        addUsage(Lang.get("commands.staffchat.usage"));
        addPermission("ulity.mod.staffchat");

        addOneTabbComplete(-1, "ulity.mod.staffchat", "staffchat");
        addOneTabbComplete(-1, "ulity.mod.staffchat", "sc");

        addAliases("sc");

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            Bukkit.broadcast(Lang.get("commands.staffchat.output")
                    .replaceAll("%player%", sender.getName())
                    .replaceAll("%message%", Text.fullColor(args)), "ulity.mod");

            return true;
        }
        return false;
    }


}
