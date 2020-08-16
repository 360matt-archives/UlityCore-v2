package fr.ulity.moderation.bukkit.events;

import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.moderation.bukkit.api.Mute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MuteEvent implements Listener {
    @EventHandler
    private static void onChat (AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        Mute playerMute = new Mute(p.getName());
        if (playerMute.isMute()){
            if (!p.hasPermission("ulity.mod.mute")) {
                e.setCancelled(true);

                String isTempmute = (playerMute.expire_text != null ? "temp" : "");
                LangBukkit.Prepared prepared = LangBukkit.prepare("commands." + isTempmute + "mute.expressions.when_chat_blocked")
                        .variable("staff", playerMute.responsable)
                        .variable("reason", playerMute.reason);

                if (playerMute.expire_text != null)
                    prepared.variable("timeLeft", playerMute.expire_text);

                prepared.sendPlayer(p);
            }
        }
    }
}
