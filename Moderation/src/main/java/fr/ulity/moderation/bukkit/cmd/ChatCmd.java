package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.moderation.bukkit.MainModBukkit;
import fr.ulity.moderation.bukkit.events.ChatDisabledEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPluginManager;

public class ChatCmd extends CommandBukkit {
    private static int warner;

    public ChatCmd () {
        super("chat");
        setPermission("ulity.mod.chat");
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1){
            if (arg.compare(0, "on", "enable", Lang.get("global.enable"), Lang.get(sender,"global.enable"))) {
                Core.temp.set("moderation.chat", true);
                MainModBukkit.server.broadcastMessage(Lang.get("commands.chat.expressions.broadcast_chat_enabled"));
                HandlerList.unregisterAll(new ChatDisabledEvent());
                warning(false);
            } else if (arg.compare(0, "off", "disable", Lang.get("global.disable"), Lang.get(sender,"global.disable"))){
                Core.temp.set("moderation.chat", false);
                MainModBukkit.server.broadcastMessage(Lang.get("commands.chat.expressions.broadcast_chat_disabled"));

                getPluginManager().registerEvents(new ChatDisabledEvent(), MainModBukkit.plugin);
                warning(true);
            }
        } else
            setStatus(Status.SYNTAX);
    }

    private static void warning (boolean stat) {
        if (stat){
            long delay = Lang.getInt("commands.chat.warning_admin.delay_minute")*60*20L;

            warner = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainModBukkit.plugin, () -> {
                for (Player p : Bukkit.getOnlinePlayers())
                    if (p.hasPermission("ulity.mod.chat"))
                        Lang.prepare("commands.chat.warning_admin.message").sendPlayer(p);
            }, delay, delay); // every x minutes
        } else if (warner != 0)
            Bukkit.getScheduler().cancelTask(warner);
    }

}
