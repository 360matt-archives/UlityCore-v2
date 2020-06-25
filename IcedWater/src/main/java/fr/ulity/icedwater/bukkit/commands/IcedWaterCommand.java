package fr.ulity.icedwater.bukkit.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class IcedWaterCommand extends CommandManager {
    public IcedWaterCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "icedwater");
        addDescription(Lang.get("commands.icedwater.description"));
        addUsage(Lang.get("commands.icedwater.usage"));
        addPermission("ulity.icedwater.icedwater");

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.get(sender, "global.player_only"));
                return true;
            }

            String status;
            if (Api.data.getBoolean("player." + sender.getName() + ".icedwater")) {
                Api.data.set("player." + sender.getName() + ".icedwater", false);
                status = "§c" + Lang.get(sender, "global.disabled");
            } else {
                Api.data.set("player." + sender.getName() + ".icedwater", true);
                status = "§a" + Lang.get(sender, "global.enabled");
            }

            sender.sendMessage(Lang.get(sender, "commands.icedwater.expressions.result")
                    .replaceAll("%status%", status));


            return true;
        }

        return false;
    }
}