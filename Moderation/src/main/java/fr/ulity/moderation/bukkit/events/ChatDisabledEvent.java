package fr.ulity.moderation.bukkit.events;

import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatDisabledEvent implements Listener {
    @EventHandler
    private static void blockChat (AsyncPlayerChatEvent e) {
        if (!Core.temp.getBoolean("moderation.chat")) {
            Player p = e.getPlayer();
            if (!p.hasPermission("ulity.mod.chat") && !p.hasPermission("ulity.mod.bypass")) {
                e.setCancelled(true);
                Lang.prepare("commands.chat.expressions.error_chat_is_disabled").sendPlayer(p);
            }
        }

    }
}
