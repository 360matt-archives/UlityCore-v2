package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.HomeMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SethomeCommand extends CommandManager {

    public SethomeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "sethome");
        addDescription(Lang.get("commands.sethome.description"));
        addUsage(Lang.get("commands.sethome.usage"));
        addPermission("ulity.packutils.sethome");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.get(sender, "global.player_only"));
            return true;
        } else {

            if (args.length == 1) {
                Player player = (Player) sender;

                if (!StringUtils.isAlphanumeric(args[0]))
                    sender.sendMessage(Lang.get(sender, "commands.sethome.expressions.alphanumeric_required"));
                else {
                    int max = MainBukkitPackUtils.config.getInt("homes.max");

                    if (HomeMethods.isHomeExist(player, args[0]) || HomeMethods.getHomeCount(player) > max) {
                        if (!(MainBukkitPackUtils.config.getBoolean("homes.staff_bypass") && player.hasPermission("ulity.packutils.home.bypass"))) {
                            sender.sendMessage(Lang.get(sender, "commands.sethome.expressions.limit").replaceAll("%count%", String.valueOf(max)));
                            return true;
                        }
                    }

                    HomeMethods.setHomeLocation(player, args[0]);
                    sender.sendMessage(Lang.get(sender, "commands.sethome.expressions.created")
                            .replaceAll("%home%", args[0]));

                }
                return true;
            }
        }

        return false;
    }

}