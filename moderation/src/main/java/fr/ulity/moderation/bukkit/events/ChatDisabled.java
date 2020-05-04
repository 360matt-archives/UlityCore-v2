package fr.ulity.moderation.bukkit.events;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatDisabled implements Listener {

    @EventHandler
    private static void blockChat (AsyncPlayerChatEvent e) {
        Player p = e.getPlayer().getPlayer();
        assert p != null;

        if (!Api.temp.getBoolean("moderation.chat")) {
            if (!p.hasPermission("ulity.mod.chat") && !p.hasPermission("ulity.mod.bypass")) {
                e.setCancelled(true);
                p.sendMessage(Lang.get(p, "commands.chat.expressions.error_chat_is_disabled"));
            }
        }

    }


}
