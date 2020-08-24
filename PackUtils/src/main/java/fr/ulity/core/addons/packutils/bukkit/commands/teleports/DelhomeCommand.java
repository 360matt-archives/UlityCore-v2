package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.api.UserHome;
import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static org.bukkit.Bukkit.getPluginManager;

public class DelhomeCommand extends CommandBukkit implements Listener {
    public DelhomeCommand() {
        super("delhome");
        setPermission("ulity.packutils.delhome");
        addArrayTabbComplete(0, "ulity.packutils.delhome", new String[]{},  new String[]{"§Homes"});
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
            if (arg.inRange(1, 1)) {
                UserHome userHome = new UserHome(sender.getName());

                if (userHome.isExist(args[0])) {
                    userHome.remove(args[0]);
                    Lang.prepare("commands.delhome.expressions.deleted")
                            .variable("home", args[0])
                            .sendPlayer(sender);
                } else
                    Lang.prepare("commands.delhome.expressions.unknown_home")
                            .variable("home", args[0])
                            .sendPlayer(sender);

            } else
                setStatus(Status.SYNTAX);
        }
    }

}