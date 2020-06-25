package fr.ulity.core.addons.packutils.bukkit.commands.players;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class FlyCommand extends CommandManager {

    public FlyCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "fly");
        addDescription(Lang.get("commands.fly.description"));
        addUsage(Lang.get("commands.fly.usage"));
        addPermission("ulity.packutils.fly");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 1) {
            Player target;
            if (args.length == 1) {
                target = Bukkit.getPlayer(args[0]);
                if (!sender.hasPermission("ulity.packutils.fly.others")) {
                    sender.sendMessage(Lang.get("global.no_perm"));
                    return true;
                } else if (target == null) {
                    sender.sendMessage(Lang.get("global.invalid_player")
                            .replaceAll("%player%", args[0]));
                    return true;
                }
            } else {
                if (sender instanceof Player)
                    target = (Player) sender;
                else {
                    sender.sendMessage(Lang.get("global.player_only"));
                    return true;
                }
            }

            target.setAllowFlight(!target.getAllowFlight());

            String status = (target.getAllowFlight()) ? "§a" + Lang.get(target, "global.enabled") : "§c" + Lang.get(target, "global.disabled");
            target.sendMessage(Lang.get(target, "commands.fly.expressions.fly_changed")
                    .replaceAll("%status%", status));

            if (!target.getName().equals(sender.getName())) {
                status = (target.getAllowFlight()) ? Lang.get(sender, "global.enabled") : Lang.get(sender, "global.disabled");
                target.sendMessage(Lang.get(sender, "commands.fly.expressions.result")
                        .replaceAll("%status%", status));
            }

            return true;
        }

        return false;
    }

}