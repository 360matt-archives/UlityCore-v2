package fr.ulity.moderation.bukkit.events;


import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Time;
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

            BanUser playerBan = new BanUser(p.getName());

            String ip = e.getAddress().getAddress().toString()
                    .replaceAll("/", "")
                    .replaceAll("\\.", "_");

            BanUser playerBanIP = new BanUser("ip_" + ip);

            if (playerBan.isBanned()) {
                messageBanned = Lang.prepare("commands.ban.expressions.you_are_banned")
                        .variable("staff", playerBan.staff)
                        .variable("reason", playerBan.reason)
                        .variable("timeLeft", new Time((int) (playerBan.expire.getTime() - new Date().getTime())).text)
                        .getOutput();

            } else if (playerBanIP.isBanned()) {
                messageBanned = Lang.prepare("commands.ban.expressions.you_are_banned")
                        .variable("staff", playerBanIP.staff)
                        .variable("reason", playerBanIP.reason)
                        .variable("timeLeft", new Time((int) (playerBanIP.expire.getTime() - new Date().getTime())).text)
                        .getOutput();
            } else return;

            e.disallow(PlayerLoginEvent.Result.KICK_BANNED, messageBanned);

       }
    }
}
