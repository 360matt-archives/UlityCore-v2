package net.listenia.survie.surviecore.events;

import fr.ulity.core_v3.Core;
import net.listenia.survie.surviecore.animations.Death;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Date;
import java.util.HashMap;

public class DeathEvent implements Listener {
    private static HashMap<String, Long> lastKilled = new HashMap<>();

    @EventHandler
    private static void onDeath (EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            if (lastKilled.containsKey(player.getName()))
                if (lastKilled.get(player.getName()) > new Date().getTime())
                    return;


            if (player.getHealth() - e.getDamage() <= 0) {
                e.setCancelled(true);

                e.getEntity().sendMessage("§eTu es mort ! Pas de chance ...\n§eSi tu souhaite retrouver les coordonnées, tapes §6/retourner");
                Core.temp.set("player." + player.getName() + ".death_lastPosition", player.getLocation());

                lastKilled.put(player.getName(), new Date().getTime() + 10000);

                Death makeDeath = new Death();
                makeDeath.run(player, e.getCause(), e.getDamage());
            }



        }


    }
}
