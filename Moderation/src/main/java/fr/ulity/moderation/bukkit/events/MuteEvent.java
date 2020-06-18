package fr.ulity.moderation.bukkit.events;

import fr.ulity.core.api.Lang;
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
            if (p.hasPermission("ulity.mod.mute") || p.hasPermission("ulity.mod.mute"))
                return;

            e.setCancelled(true);

            String errMsg = Lang.get(p, "commands." +  (playerMute.expire_text != null ? "temp" : "") + "mute.expressions.when_chat_blocked")
                    .replaceAll("%staff%", playerMute.responsable)
                    .replaceAll("%reason%", playerMute.reason);

            if (playerMute.expire_text != null)
                errMsg = errMsg.replaceAll("%timeLeft%", playerMute.expire_text);

            p.sendMessage(errMsg);

        }
    }

}
