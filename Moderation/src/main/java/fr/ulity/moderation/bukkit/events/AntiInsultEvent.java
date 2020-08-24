package fr.ulity.moderation.bukkit.events;

import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEvent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AntiInsultEvent implements Listener {

    @EventHandler
    private static void withMessage (AsyncPlayerChatEvent e) {
        String wordMessage = hasBlacklistedWord(e.getMessage(), e.getPlayer());
        if (wordMessage != null)
            whenDetected(e, wordMessage, "chat");

    }

    @EventHandler
    private static void withCommand (PlayerCommandPreprocessEvent e) {
        String word = hasBlacklistedWord(e.getMessage(), e.getPlayer());
        if (word != null)
            whenDetected(e, word, "commands");
    }

    /* not event Handle */
    private static String hasBlacklistedWord (String msg, Player p) {
        List<String> bl = Stream.concat(
                Arrays.stream(Lang.getArray(p, "module.anti_insult.words")),
                Arrays.stream(Lang.getArray("module.anti_insult.words"))
        )
        .collect(Collectors.toList());

        return bl.stream().filter(x -> msg.toLowerCase().contains(x.toLowerCase())).findFirst().orElse(null);
    }


    /* not event Handle */
    private static void whenDetected (Object e, String word, String type) {
        Player p = ((PlayerEvent) e).getPlayer();
        if (p.hasPermission("ulity.mod") && Lang.getBoolean("module.anti_insult." + type + ".staff_bypass"))
            return;

        ((Cancellable) e).setCancelled(true);
        Lang.prepare("module.anti_insult." + type + ".error_message" + ((p.hasPermission("ulity.mod") ? "_staff" : "" )))
                .variable("word", word)
                .sendPlayer(p);
    }

}
