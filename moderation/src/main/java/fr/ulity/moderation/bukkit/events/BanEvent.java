package fr.ulity.moderation.bukkit.events;

import fr.ulity.core.api.Lang;
import fr.ulity.moderation.bukkit.api.Ban;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BanEvent implements Listener {

    @EventHandler
    public static void onLogin (PlayerLoginEvent e) {
        Player p = e.getPlayer();

        if (p.hasPermission("ulity.mod.ban"))
            return;

        Ban playerBan = new Ban(p.getName());

        if (playerBan.isBan()){
            String messageBanned = Lang.get("commands.ban.expressions.you_are_banned")
                    .replaceAll("%staff%", playerBan.responsable)
                    .replaceAll("%reason%", playerBan.reason)
                    .replaceAll("%timeLeft%", playerBan.expire_text);

            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, messageBanned);

        }
    }
}
