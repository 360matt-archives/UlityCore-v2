package fr.ulity.moderation.bukkit.events;


import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Time;
import fr.ulity.moderation.api.sanctions.BanIP;
import fr.ulity.moderation.api.sanctions.BanUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Date;

public class BanEvent implements Listener {

    @EventHandler
    public static void onLogin (PlayerLoginEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission("ulity.mod.ban")){
            String messageBanned = null;

            String ip = e.getAddress().getHostAddress()
                    .replaceAll("/", "")
                    .replaceAll("\\.", "_");

            BanUser banUser = new BanUser(p.getName());
            BanIP banIP = new BanIP(ip);

            if (banUser.isBanned()) {
                messageBanned = Lang.prepare("commands.ban.expressions.you_are_banned")
                        .variable("staff", banUser.staff)
                        .variable("reason", banUser.reason)
                        .variable("timeLeft", new Time((int) (banUser.expire.getTime() - new Date().getTime())).text)
                        .getOutput();

            } else if (banIP.isBanned()) {
                messageBanned = Lang.prepare("commands.ban.expressions.you_are_banned")
                        .variable("staff", banIP.staff)
                        .variable("reason", banIP.reason)
                        .variable("timeLeft", new Time((int) (banIP.expire.getTime() - new Date().getTime())).text)
                        .getOutput();
            } else return;

            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, messageBanned);

       }
    }
}
