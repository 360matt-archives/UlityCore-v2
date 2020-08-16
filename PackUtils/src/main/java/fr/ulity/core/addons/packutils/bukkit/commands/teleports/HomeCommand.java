package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.HomeMethods;
import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
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

public class HomeCommand extends CommandManager.Assisted implements Listener {

    public HomeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "home");
        addPermission("ulity.packutils.home");
        addArrayTabbComplete(0, "ulity.packutils.home", new String[]{},  new String[]{"§Homes"});
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
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (requirePlayer()) {
            if (arg.inRange(0, 1)) {
                Player player = (Player) sender;
                String homeName = (args.length == 1) ? args[0] : "home";

                if (HomeMethods.isHomeExist(player, homeName)) {
                    LangBukkit.prepare("commands.home.expressions.prevent_teleport")
                            .variable("home", homeName)
                            .sendPlayer(player);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(MainBukkitPackUtils.plugin, () -> {
                        if (player.isOnline()) {
                            player.teleport(HomeMethods.getHomeLocation(player, homeName));
                            LangBukkit.prepare("commands.home.expressions.teleported")
                                    .variable("home", homeName)
                                    .sendPlayer(player);
                        }
                    }, 20*5L);
                } else if (homeName.equals("home")){
                    String[] list = HomeMethods.getHomeListName(player);
                    String toText = (list.length == 0) ? LangBukkit.get(player, "commands.home.expressions.nothing_list") : Arrays.toString(list);

                    LangBukkit.prepare("commands.home.expressions.home_list")
                            .variable("list", toText.replaceAll("[\\[|\\]]", ""))
                            .sendPlayer(player);

                } else
                    LangBukkit.prepare("commands.home.expressions.unknown_home")
                            .variable("home", homeName)
                            .sendPlayer(player);
            } else
                setStatus(Status.SYNTAX);
        }
    }
}