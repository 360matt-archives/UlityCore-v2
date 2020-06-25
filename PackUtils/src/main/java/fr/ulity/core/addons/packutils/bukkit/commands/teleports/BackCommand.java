package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.BackMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BackCommand extends CommandManager {

    public BackCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "back");
        addDescription(Lang.get("commands.back.description"));
        addUsage(Lang.get("commands.back.usage"));
        addPermission("ulity.packutils.back");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.get(sender, "global.player_only"));
            return true;
        } else
            if (args.length == 0) {
                Player player = (Player) sender;

                Location lastLoc = BackMethods.getLastLocation(player);
                if (lastLoc == null)
                    player.sendMessage(Lang.get(player, "commands.back.expressions.nothing"));
                else {
                    if (lastLoc.getWorld() == null)
                        player.sendMessage(Lang.get(player, "commands.back.expressions.unknown_world"));
                    else {
                        player.teleport(lastLoc);
                        player.sendMessage(Lang.get(player, "commands.back.expressions.teleported"));
                        BackMethods.setLastLocation(player);
                    }
                }
                return true;
            }

        return false;
    }

}