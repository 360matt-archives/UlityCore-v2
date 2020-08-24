package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.api.UserHome;
import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import static org.bukkit.Bukkit.getPluginManager;

public class HomeCommand extends CommandBukkit implements Listener {

    public HomeCommand() {
        super("home");
        setPermission("ulity.packutils.home");
        addArrayTabbComplete(0, "ulity.packutils.home", new String[]{},  new String[]{"§Homes"});
        if (MainBukkitPackUtils.enabler.canEnable(getName())) {
            getPluginManager().registerEvents(this, MainBukkitPackUtils.plugin);
            unregister(BukkitAPI.commandMap);
        }
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        String request = e.getBuffer();
        String[] args = request.split(" ");

        if (args[0].replace("/", "").equalsIgnoreCase(getName()))
            if (e.getCompletions().contains("§Homes"))
                e.setCompletions(new UserHome(e.getSender().getName()).getList());
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (requirePlayer()) {
            if (arg.inRange(0, 1)) {
                Player player = (Player) sender;
                String homeName = (args.length == 1) ? args[0] : "home";

                UserHome userHome = new UserHome(sender.getName());

                if (userHome.isExist(homeName)) {
                    Lang.prepare("commands.home.expressions.prevent_teleport")
                            .variable("home", homeName)
                            .sendPlayer(player);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(MainBukkitPackUtils.plugin, () -> {
                        if (player.isOnline()) {
                            player.teleport(userHome.getLocation(homeName));
                            Lang.prepare("commands.home.expressions.teleported")
                                    .variable("home", homeName)
                                    .sendPlayer(player);
                        }
                    }, 20*5L);
                } else if (homeName.equals("home")){
                    List<String> list = userHome.getList();
                    String toText = (list.size() == 0) ? Lang.get(player, "commands.home.expressions.nothing_list") : list.toString();

                    Lang.prepare("commands.home.expressions.home_list")
                            .variable("list", toText.replaceAll("[\\[|\\]]", ""))
                            .sendPlayer(player);

                } else
                    Lang.prepare("commands.home.expressions.unknown_home")
                            .variable("home", homeName)
                            .sendPlayer(player);
            } else
                setStatus(Status.SYNTAX);
        }
    }
}