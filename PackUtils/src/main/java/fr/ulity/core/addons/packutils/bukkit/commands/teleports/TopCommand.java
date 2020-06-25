package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TopCommand extends CommandManager {

    public TopCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "top");
        addDescription(Lang.get("commands.top.description"));
        addUsage(Lang.get("commands.top.usage"));
        addPermission("ulity.packutils.top");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    public static Location getTopLoc (Player player) {
        Location loc = player.getLocation();

        double x = loc.getX();
        double y = loc.getWorld().getHighestBlockAt((int) loc.getX(), (int) loc.getY()).getY();
        double z = (int) loc.getZ();
        return new Location(loc.getWorld(), x, y, z);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 2) {
            if (!(sender instanceof Player))
                sender.sendMessage(Lang.get(sender, "global.player_only"));
            else {

                if (args.length == 0) {
                    Player player = (Player) sender;
                    player.teleport(getTopLoc(player));
                    player.sendMessage(Lang.get(player, "commands.top.expressions.notification"));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(Lang.get(sender, "global.invalid_player")
                                .replaceAll("%player%", args[0]));
                    } else {
                        if (sender.hasPermission("ulity.packutils.top.others")) {
                            target.teleport(getTopLoc(target));
                            target.sendMessage(Lang.get(target, "commands.top.expressions.notification"));
                            sender.sendMessage(Lang.get(sender, "commands.top.expressions.result_other")
                                    .replaceAll("%player%", target.getName()));
                        } else
                            sender.sendMessage(Lang.get(sender, "global.no_perm"));
                    }
                }
            }

            return true;
        }


        return false;
    }

}