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

        if (!p.hasPermission("ulity.mod.ban")){
            String messageBanned;

            Ban playerBan = new Ban(p.getName());

            String ip = e.getAddress().getAddress().toString()
                    .replaceAll("/", "")
                    .replaceAll("\\.", "_");

            Ban playerBanIP = new Ban("ip_" + ip);

            if (playerBan.isBan() || playerBanIP.isBan()){
                messageBanned = Lang.get("commands.ban.expressions.you_are_banned")
                        .replaceAll("%staff%", playerBan.responsable)
                        .replaceAll("%reason%", playerBan.reason)
                        .replaceAll("%timeLeft%", playerBan.expire_text);

                e.disallow(PlayerLoginEvent.Result.KICK_BANNED, messageBanned);
            }
       }
    }
}
