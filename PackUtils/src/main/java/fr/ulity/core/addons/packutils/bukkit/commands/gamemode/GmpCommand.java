package fr.ulity.core.addons.packutils.bukkit.commands.gamemode;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.GamemodeMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GmpCommand extends CommandManager {

    public GmpCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "gmp");
        addDescription(Lang.get("commands.gmX.description"));
        addUsage(Lang.get("commands.gmX.usage"));
        addPermission("ulity.packutils.gamemode");
        addPermission("ulity.packutils.gmp");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 1) {
            Player target;
            if (args.length == 1) {
                target = Bukkit.getPlayer(args[0]);
                if (!sender.hasPermission("ulity.packutils.gamemode.others")) {
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

            GamemodeMethods.define(target, GameMode.SPECTATOR, sender);
            return true;
        }

        return false;
    }
}