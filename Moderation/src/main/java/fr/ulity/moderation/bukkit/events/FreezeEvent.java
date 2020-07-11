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

    private static boolean isNotMod (Player player) {
        return !player.hasPermission("ulity.mod") && !player.hasPermission("ulity.mod.freeze");
    }

    @EventHandler
    private static void onJoin (PlayerJoinEvent e) {
        Freeze playerFreeze = new Freeze(e.getPlayer().getName());
        if (playerFreeze.isFreeze() && isNotMod(e.getPlayer())) playerFreeze.recallFreeze();
        else playerFreeze.recallUnFreeze();
    }

    @EventHandler
    private static void onChat (AsyncPlayerChatEvent e) {
        e.setCancelled(e.isCancelled() || (new Freeze(e.getPlayer().getName()).isFreeze() && isNotMod(e.getPlayer())));
    }

    @EventHandler
    private static void onPvP (EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (new Freeze(e.getEntity().getName()).isFreeze() && isNotMod((Player) e.getEntity()))
                e.setCancelled(true);
        }
        else if (e.getDamager() instanceof Player)
            if (new Freeze(e.getDamager().getName()).isFreeze() && isNotMod((Player) e.getDamager()))
                e.setCancelled(true);
    }

    @EventHandler
    private static void onPlace (BlockPlaceEvent e) {
        e.setCancelled(e.isCancelled() || (new Freeze(e.getPlayer().getName()).isFreeze() && isNotMod(e.getPlayer())));
    }

    @EventHandler
    private static void onBreak (BlockBreakEvent e) {
        e.setCancelled(e.isCancelled() || (new Freeze(e.getPlayer().getName()).isFreeze() && isNotMod(e.getPlayer())));
    }

     @EventHandler
    private static void onInteract (PlayerInteractEvent e) {
        if (new Freeze(e.getPlayer().getName()).isFreeze() && isNotMod(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    private static void onCommand (PlayerCommandPreprocessEvent e) {
        e.setCancelled(e.isCancelled() || (new Freeze(e.getPlayer().getName()).isFreeze() && isNotMod(e.getPlayer())));
    }

    @EventHandler
    private static void onDamage (EntityDamageEvent e) {
        if (e.getEntity() instanceof Player)
            e.setCancelled(e.isCancelled() || (new Freeze(e.getEntity().getName()).isFreeze() && isNotMod((Player) e.getEntity())));
    }
}
