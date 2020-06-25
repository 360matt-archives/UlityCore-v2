package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.HomeMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

import static org.bukkit.Bukkit.getPluginManager;

public class DelhomeCommand extends CommandManager implements Listener {

    public DelhomeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "delhome");
        addDescription(Lang.get("commands.delhome.description"));
        addUsage(Lang.get("commands.delhome.usage"));
        addPermission("ulity.packutils.delhome");

        addArrayTabbComplete(0, "ulity.packutils.delhome", new String[]{},  new String[]{"§Homes"});

        if (MainBukkitPackUtils.enabler.canEnable(getName())) {
            getPluginManager().registerEvents(this, getPlugin());
            registerCommand(commandMap);
        }
    }


    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        String request = e.getBuffer();
        String[] args = request.split(" ");

        if (args[0].replace("/", "").equalsIgnoreCase(getName())){
            if (e.getCompletions().contains("§Homes"))
                e.setCompletions(Arrays.asList(HomeMethods.getHomeListName((Player) e.getSender())));
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.get(sender, "global.player_only"));
            return true;
        } else {
            if (args.length == 1) {
                Player player = (Player) sender;

                if (HomeMethods.isHomeExist(player, args[0])) {
                    HomeMethods.delHome(player, args[0]);
                    player.sendMessage(Lang.get(player, "commands.delhome.expressions.deleted")
                            .replaceAll("%home%", args[0]));
                } else {
                    player.sendMessage(Lang.get(player, "commands.delhome.expressions.unknown_home")
                            .replaceAll("%home%", args[0]));
                }

                return true;
            }
        }


        return false;
    }

}