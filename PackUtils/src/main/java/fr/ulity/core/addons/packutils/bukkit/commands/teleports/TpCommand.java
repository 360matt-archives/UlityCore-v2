package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.TeleportMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TpCommand extends CommandManager {

    public TpCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tp");
        addDescription(Lang.get("commands.tp.description"));
        addUsage(Lang.get("commands.tp.usage"));
        addPermission("ulity.packutils.tp");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    public static void tpPlayers (CommandSender sender, TeleportMethods teleporting, String origin, String target) {
        Player originPlayer = Bukkit.getPlayer(origin);
        Player targetPlayer = Bukkit.getPlayer(target);

        if (originPlayer == null)
            sender.sendMessage(Lang.get(sender, "global.invalid_player").replaceAll("%player%", origin));
        else if (targetPlayer == null)
            sender.sendMessage(Lang.get(sender, "global.invalid_player").replaceAll("%player%", target));
        else
            teleporting.tpPlayer(originPlayer, targetPlayer);
    }

    public static void tpLoc (CommandSender sender, TeleportMethods teleporting, String origin, Location target) {
        Player originPlayer = Bukkit.getPlayer(origin);
        if (originPlayer == null)
            sender.sendMessage(Lang.get(sender, "global.invalid_player").replaceAll("%player%", origin));
        else {
            target.setWorld(originPlayer.getWorld());
            teleporting.tpCoords(originPlayer, target);
        }


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
       if (args.length >= 1 && args.length <= 4) {
            TeleportMethods teleporting = new TeleportMethods(sender);

            if (args.length == 1 ) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Lang.get(sender, "global.player_only"));
                    return true;
                } else
                    tpPlayers(sender, teleporting, sender.getName(), args[0]);
            } else if (args.length == 2) {
                tpPlayers(sender, teleporting, args[0], args[1]);
            } else {
                int ind = -3 + args.length;

                try {
                    double x = Double.parseDouble(args[ind]);
                    double y = Double.parseDouble(args[ind + 1]);
                    double z = Double.parseDouble(args[ind + 2]);

                    String pseudo = (ind == 0) ? sender.getName() : args[0];
                    tpLoc(sender, teleporting, pseudo, new Location(null, x, y, z));
                } catch (NumberFormatException nfe) {
                    return false;
                }

            }

            return true;
        }
        return false;
    }


}