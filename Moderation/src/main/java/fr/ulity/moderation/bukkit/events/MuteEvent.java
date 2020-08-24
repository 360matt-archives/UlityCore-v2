package fr.ulity.moderation.bukkit.events;


import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.modules.language.PreparedLang;
import fr.ulity.core_v3.utils.Time;
import fr.ulity.moderation.api.sanctions.MuteUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;

public class MuteEvent implements Listener {
    @EventHandler
    private static void onChat (AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        MuteUser playerMute = new MuteUser(p.getName());
        if (playerMute.isMuted()){
            if (!p.hasPermission("ulity.mod.mute")) {
                e.setCancelled(true);

                String isTempmute = ((!playerMute.neverExpire()) ? "temp" : "");
                PreparedLang prepared = Lang.prepare("commands." + isTempmute + "mute.expressions.when_chat_blocked")
                        .variable("staff", playerMute.staff)
                        .variable("reason", playerMute.reason);

                if (!playerMute.neverExpire())
                    prepared.variable("timeLeft", new Time((int) (playerMute.expire.getTime() - new Date().getTime())).text);

                prepared.sendPlayer(p);
            }
        }
    }
}
