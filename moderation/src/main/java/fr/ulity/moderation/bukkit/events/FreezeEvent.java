package fr.ulity.moderation.bukkit.events;

import fr.ulity.moderation.bukkit.api.Freeze;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;


public class FreezeEvent implements Listener {

    @EventHandler
    private static void onJoin (PlayerJoinEvent e) {
        Freeze playerFreeze = new Freeze(e.getPlayer().getName());

        if (playerFreeze.isFreeze())
            playerFreeze.recallFreeze();
        else
            playerFreeze.recallUnFreeze();
    }

    @EventHandler
    private static void onChat (AsyncPlayerChatEvent e) {
        e.setCancelled(new Freeze(e.getPlayer().getName()).isFreeze());
    }

    @EventHandler
    private static void onPvP (EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (new Freeze(e.getEntity().getName()).isFreeze())
                e.setCancelled(true);
        }
        else if (e.getDamager() instanceof Player)
            if (new Freeze(e.getDamager().getName()).isFreeze())
                e.setCancelled(true);
    }

    @EventHandler
    private static void onPlace (BlockPlaceEvent e) {
        e.setCancelled(new Freeze(e.getPlayer().getName()).isFreeze());
    }

    @EventHandler
    private static void onCommand (BlockBreakEvent e) {
        e.setCancelled(new Freeze(e.getPlayer().getName()).isFreeze());
    }

    @EventHandler
    private static void onCommand (PlayerInteractEvent e) {
        e.setCancelled(new Freeze(e.getPlayer().getName()).isFreeze());
    }

    @EventHandler
    private static void onCommand (PlayerCommandPreprocessEvent e) {
        e.setCancelled(new Freeze(e.getPlayer().getName()).isFreeze());
    }

    @EventHandler
    private static void onCommand (EntityDamageEvent e) {
        if (e.getEntity() instanceof Player)
            e.setCancelled(new Freeze(e.getEntity().getName()).isFreeze());
    }


}
