package fr.ulity.moderation.bukkit.events;

import fr.ulity.moderation.bukkit.MainModBukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InvSeeCancelEvent implements Listener {

    @EventHandler
    private static void onInventory (InventoryClickEvent e) {
        if (e.getInventory().getType().equals(InventoryType.PLAYER) && e.getWhoClicked().getInventory() != e.getInventory())
            e.setCancelled(!MainModBukkit.config.getBoolean("invsee.can_modify"));
    }

}
