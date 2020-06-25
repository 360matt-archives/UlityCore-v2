package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.SpawnMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnCommand extends CommandManager {
    public SpawnCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "spawn");
        addDescription(Lang.get("commands.spawn.description"));
        addUsage(Lang.get("commands.spawn.usage"));
        addPermission("ulity.packutils.spawn");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.get(sender, "global.player_only"));
            return true;
        } else {
            Location spawnLoc = SpawnMethods.getSpawnLocation();

            if (spawnLoc == null)
                sender.sendMessage(Lang.get(sender, "commands.spawn.expressions.not_defined"));
            //else if (spawnLoc.getWorld() == null)
                //sender.sendMessage(Lang.get(sender, "commands.spawn.expressions.unknown_world"));
            else if (args.length <= 1) {
                Player origin;


                if (args.length == 0) {
                    origin = (Player) sender;
                } else {
                    origin = Bukkit.getPlayer(args[0]);
                    if (origin == null) {
                        sender.sendMessage(Lang.get(sender, "global.invalid_player")
                                .replaceAll("%player%", args[0]));
                        return true;
                    } else if (!sender.hasPermission("ulity.packutils.spawn.others")) {
                        sender.sendMessage(Lang.get(sender, "global.no_perm"));
                        return true;
                    }
                }

                origin.sendMessage(Lang.get(sender, "commands.spawn.expressions.prevent_teleport"));
                if (!origin.getName().equals(sender.getName())) {
                    sender.sendMessage(Lang.get(sender, "commands.spawn.expressions.others_result")
                            .replaceAll("%player%", origin.getName()));
                }

                Player finalOrigin = origin;
                Bukkit.getScheduler().scheduleSyncDelayedTask(MainBukkitPackUtils.plugin, () -> {
                    if (finalOrigin.isOnline()) {
                        finalOrigin.teleport(spawnLoc);
                        finalOrigin.sendMessage(Lang.get(finalOrigin, "commands.spawn.expressions.teleported"));
                    }
                }, 20*5L);

                return true;
            } else
                return false;
        }
        return true;
    }
}