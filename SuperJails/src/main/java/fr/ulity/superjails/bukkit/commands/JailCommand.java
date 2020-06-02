package fr.ulity.superjails.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.superjails.bukkit.api.JailsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

import static org.bukkit.Bukkit.getPluginManager;

public class JailCommand extends CommandManager implements Listener {

    public JailCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "jail");
        addDescription(Lang.get("commands.jail.description"));

        addUsage(Lang.get("commands.jail.usage"));
        addPermission("ulity.superjails.commands.jail");

        addOneTabbComplete(1, "ulity.superjails.commands.jail", "§AllJails", null);


        getPluginManager().registerEvents(this, getPlugin());

        registerCommand(commandMap);
    }


    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        String request = e.getBuffer();
        String[] args = request.split(" ");

        if (args[0].replace("/", "").equalsIgnoreCase(getName())) {
            if (e.getCompletions().contains("§AllJails"))
                e.setCompletions((Arrays.asList(Arrays.stream(JailsSystem.getAllJails())
                        .filter(v -> e.getSender().hasPermission(JailsSystem.getPermission(v).data.toString())).toArray(String[]::new))));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        return false;
    }


}