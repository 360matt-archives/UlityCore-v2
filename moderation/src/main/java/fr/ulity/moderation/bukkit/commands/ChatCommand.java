package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.moderation.bukkit.MainModBukkit;
import fr.ulity.moderation.bukkit.events.ChatDisabledEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getPluginManager;

public class ChatCommand extends CommandManager  {
    private static int warner;


    public ChatCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "chat");
        addDescription(Lang.get("commands.chat.description"));

        addUsage(Lang.get("commands.chat.usage"));
        addPermission("ulity.mod.chat");

        addOneTabbComplete(-1, "clearchat");
        addArrayTabbComplete(0, "ulity.mod.chat", new String[]{}, new String[] {Lang.get("global.enable"), Lang.get("global.disable")});

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length >= 1){
            if (args[0].equalsIgnoreCase("on")
            || args[0].equalsIgnoreCase("enable")
            || args[0].equalsIgnoreCase(Lang.get("global.enable"))){

                Api.temp.set("moderation.chat", true);
                MainModBukkit.server.broadcastMessage(Lang.get("commands.chat.expressions.broadcast_chat_enabled"));

                HandlerList.unregisterAll(new ChatDisabledEvent());
                warning(false);
                return true;
            }
            else if (args[0].equalsIgnoreCase("off")
            || args[0].equalsIgnoreCase("disable")
            || args[0].equalsIgnoreCase(Lang.get("global.disable"))){

                Api.temp.set("moderation.chat", false);
                MainModBukkit.server.broadcastMessage(Lang.get("commands.chat.expressions.broadcast_chat_disabled"));

                getPluginManager().registerEvents(new ChatDisabledEvent(), MainModBukkit.plugin);
                warning(true);
                return true;
            }
        }

        return false;
    }


    private static void warning (boolean stat) {
        if (stat){
            long delay = Lang.getInt("commands.chat.warning_admin.delay_minute")*60*20L;

            warner = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainModBukkit.plugin, new Runnable() {
                @Override
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers())
                        if (p.hasPermission("ulity.mod.chat"))
                            p.sendMessage(Lang.get("commands.chat.warning_admin.message"));
                }
            }, delay, delay); // every x minutes
        }
        else if (warner != 0)
            Bukkit.getScheduler().cancelTask(warner);

    }
}