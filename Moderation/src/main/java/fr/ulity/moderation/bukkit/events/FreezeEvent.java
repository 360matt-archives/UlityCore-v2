package fr.ulity.moderation.bukkit.events;

import fr.ulity.moderation.api.sanctions.FreezeUser;
import fr.ulity.moderation.api.sanctions.freeze.BukkitFreezeSystem;
import org.bukkit.Bukkit;
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

    private static boolean isNotMod (Player player) {
        return !player.hasPermission("ulity.mod") && !player.hasPermission("ulity.mod.freeze");
    }

    @EventHandler
    private static void onJoin (PlayerJoinEvent e) {
        FreezeUser playerFreeze = new FreezeUser(e.getPlayer().getName());
        if (playerFreeze.isFrozen() && isNotMod(e.getPlayer()))
            new BukkitFreezeSystem().recallFreeze(e.getPlayer().getName());
        else
            new BukkitFreezeSystem().recallUnFreeze(e.getPlayer().getName());
    }

    @EventHandler
    private static void onChat (AsyncPlayerChatEvent e) {
        e.setCancelled(e.isCancelled() || (new FreezeUser(e.getPlayer().getName()).isFrozen() && isNotMod(e.getPlayer())));
    }

    @EventHandler
    private static void onPvP (EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (new FreezeUser(e.getEntity().getName()).isFrozen() && isNotMod((Player) e.getEntity()))
                e.setCancelled(true);
        }
        else if (e.getDamager() instanceof Player)
            if (new FreezeUser(e.getDamager().getName()).isFrozen() && isNotMod((Player) e.getDamager()))
                e.setCancelled(true);
    }

    @EventHandler
    private static void onPlace (BlockPlaceEvent e) {
        e.setCancelled(e.isCancelled() || (new FreezeUser(e.getPlayer().getName()).isFrozen() && isNotMod(e.getPlayer())));
    }

    @EventHandler
    private static void onBreak (BlockBreakEvent e) {
        e.setCancelled(e.isCancelled() || (new FreezeUser(e.getPlayer().getName()).isFrozen() && isNotMod(e.getPlayer())));
    }

     @EventHandler
    private static void onInteract (PlayerInteractEvent e) {
        if (new FreezeUser(e.getPlayer().getName()).isFrozen() && isNotMod(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    private static void onCommand (PlayerCommandPreprocessEvent e) {
        e.setCancelled(e.isCancelled() || (new FreezeUser(e.getPlayer().getName()).isFrozen() && isNotMod(e.getPlayer())));
    }

    @EventHandler
    private static void onDamage (EntityDamageEvent e) {
        if (e.getEntity() instanceof Player)
            e.setCancelled(e.isCancelled() || (new FreezeUser(e.getEntity().getName()).isFrozen() && isNotMod((Player) e.getEntity())));
    }





}
