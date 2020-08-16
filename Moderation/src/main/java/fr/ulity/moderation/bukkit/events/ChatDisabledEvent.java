package fr.ulity.moderation.bukkit.events;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatDisabledEvent implements Listener {
    @EventHandler
    private static void blockChat (AsyncPlayerChatEvent e) {
        if (!Api.temp.getBoolean("moderation.chat")) {
            Player p = e.getPlayer();
            if (!p.hasPermission("ulity.mod.chat") && !p.hasPermission("ulity.mod.bypass")) {
                e.setCancelled(true);
                LangBukkit.prepare("commands.chat.expressions.error_chat_is_disabled").sendPlayer(p);
            }
        }

    }
}
