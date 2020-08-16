package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
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

public class ChatCommand extends CommandManager.Assisted  {
    private static int warner;

    public ChatCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "chat");
        addPermission("ulity.mod.chat");
        addArrayTabbComplete(0, "ulity.mod.chat", new String[]{}, new String[] {LangBukkit.get("global.enable"), LangBukkit.get("global.disable")});
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1){
            if (arg.compare(0, "on", "enable", LangBukkit.get("global.enable"), LangBukkit.get(sender,"global.enable"))) {
                Api.temp.set("moderation.chat", true);
                MainModBukkit.server.broadcastMessage(LangBukkit.get("commands.chat.expressions.broadcast_chat_enabled"));
                HandlerList.unregisterAll(new ChatDisabledEvent());
                warning(false);
            } else if (arg.compare(0, "off", "disable", LangBukkit.get("global.disable"), LangBukkit.get(sender,"global.disable"))){
                Api.temp.set("moderation.chat", false);
                MainModBukkit.server.broadcastMessage(LangBukkit.get("commands.chat.expressions.broadcast_chat_disabled"));

                getPluginManager().registerEvents(new ChatDisabledEvent(), MainModBukkit.plugin);
                warning(true);
            }
        } else
            setStatus(Status.SYNTAX);
    }

    private static void warning (boolean stat) {
        if (stat){
            long delay = LangBukkit.getInt("commands.chat.warning_admin.delay_minute")*60*20L;

            warner = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainModBukkit.plugin, () -> {
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.hasPermission("ulity.mod.chat"))
                        LangBukkit.prepare("commands.chat.warning_admin.message").sendPlayer(p);
            }, delay, delay); // every x minutes
        } else if (warner != 0)
            Bukkit.getScheduler().cancelTask(warner);
    }
}