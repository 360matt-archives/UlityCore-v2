package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.SpawnMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetspawnCommand extends CommandManager {
    public SetspawnCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "setspawn");
        addDescription(Lang.get("commands.setspawn.description"));
        addUsage(Lang.get("commands.setspawn.usage"));
        addPermission("ulity.packutils.setspawn");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.get(sender, "global.player_only"));
            return true;
        } else {
            if (args.length == 0) {
                Player player = (Player) sender;
                SpawnMethods.setSpawnLocation(player);

                player.sendMessage(Lang.get(player, "commands.setspawn.expressions.result"));
                return true;
            }
        }
        return false;
    }
}