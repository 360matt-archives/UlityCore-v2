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

public class HomeCommand extends CommandManager implements Listener {

    public HomeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "home");
        addDescription(Lang.get("commands.home.description"));
        addUsage(Lang.get("commands.home.usage"));
        addPermission("ulity.packutils.home");

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
            if (args.length <= 1) {
                Player player = (Player) sender;

                String homeName = (args.length == 1) ? args[0] : "home";

                if (HomeMethods.isHomeExist(player, homeName)) {
                    player.sendMessage(Lang.get(player, "commands.home.expressions.prevent_teleport")
                            .replaceAll("%home%", homeName));

                    Bukkit.getScheduler().scheduleSyncDelayedTask(MainBukkitPackUtils.plugin, () -> {
                        if (player.isOnline()) {
                            player.teleport(HomeMethods.getHomeLocation(player, homeName));
                            player.sendMessage(Lang.get(player, "commands.home.expressions.teleported")
                                    .replaceAll("%home%", homeName));
                        }
                    }, 20*5L);
                } else if (homeName.equals("home")){
                    String[] list = HomeMethods.getHomeListName(player);
                    String toText = (list.length == 0) ? Lang.get(player, "commands.home.expressions.nothing_list") : Arrays.toString(list);

                    player.sendMessage(Lang.get(player, "commands.home.expressions.home_list")
                            .replaceAll("%list%", toText.replaceAll("[\\[|\\]]", "")));
                } else {
                    player.sendMessage(Lang.get(player, "commands.home.expressions.unknown_home")
                            .replaceAll("%home%", homeName));
                }

                return true;
            }
        }
        return false;
    }

}