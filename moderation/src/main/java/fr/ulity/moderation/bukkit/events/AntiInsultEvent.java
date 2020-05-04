package fr.ulity.moderation.bukkit.events;

import fr.ulity.core.api.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEvent;

public class AntiInsultEvent implements Listener {

    @EventHandler
    private static void withMessage (AsyncPlayerChatEvent e) {
        String word = hasBlacklistedWord(e.getMessage(), e.getPlayer());
        if (word != null)
            whenDetected(e, word, "chat");
    }


    @EventHandler
    private static void withCommand (PlayerCommandPreprocessEvent e) {
        String word = hasBlacklistedWord(e.getMessage(), e.getPlayer());
        if (word != null)
            whenDetected(e, word, "commands");
    }


    /* not event Handle */
    private static String hasBlacklistedWord (String msg, Player p) {
        for (String x : Lang.getStringArray(p, "module.anti_insult.words"))
            if (msg.toLowerCase().contains(x.toLowerCase()))
                return x;
        for (String x : Lang.getStringArray("module.anti_insult.words"))
            if (msg.toLowerCase().contains(x.toLowerCase()))
                return x;

        return null;
    }


    /* not event Handle */
    private static void whenDetected (Object e, String word, String type) {

        e = (e instanceof AsyncPlayerChatEvent) ? (AsyncPlayerChatEvent) e : (PlayerCommandPreprocessEvent) e;


        Player p = ((PlayerEvent) e).getPlayer();
        if (p.hasPermission("ulity.mod") && Lang.getBoolean("module.anti_insult." + type + ".staff_bypass"))
            return;

        ((Cancellable) e).setCancelled(true);
        p.sendMessage((p.hasPermission("ulity.mod")
            ? Lang.get("module.anti_insult." + type + ".error_message_staff")
            : Lang.get("module.anti_insult." + type + ".error_message"))
                .replaceAll("%word%", word));

    }

}
