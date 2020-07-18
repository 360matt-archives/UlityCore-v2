package net.listenia.survie.surviecore.events;

import net.listenia.survie.surviecore.SurvieCore;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class OnWarzoneQuit implements Listener {


    private static boolean isInSpawn (Location loc) {
        return loc.getX() >= SurvieCore.config.getOrSetDefault("spawn.min.x", -434)
                && loc.getX() <= SurvieCore.config.getOrSetDefault("spawn.max.x", -299)
                && loc.getZ() >= SurvieCore.config.getOrSetDefault("spawn.min.z", 142)
                && loc.getZ() <= SurvieCore.config.getOrSetDefault("spawn.max.z", 279);
    }


    @EventHandler
    private static void onMove (PlayerMoveEvent e) {
        Location loc1 = e.getFrom();
        Location loc2 = e.getTo();

        if ( ( loc1.getX() != loc2.getX() || loc1.getZ() != loc2.getZ() ) && loc1.getWorld().equals(loc2.getWorld())) {
            if(!isInSpawn(loc1) && isInSpawn(loc2)) {
                for (int i = 0; i <= 8; i++)
                    if (!loc2.clone().subtract(0, i, 0).getBlock().getType().isAir())
                        return;

            if (!e.getPlayer().hasPermission("grade.mod") && !e.getPlayer().hasPermission("mod")) {

                e.setCancelled(true);

                double deltaX = loc1.getX() - loc2.getX();//Get X Delta
                double deltaY = loc1.getY() - loc2.getY();//Get Z delta
                double deltaZ = loc1.getZ() - loc2.getZ();//Get Z delta

                Vector vec = new Vector(deltaX, deltaY, deltaZ);
                vec.normalize();
                e.getPlayer().setVelocity(vec.multiply(0.005 / (Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaZ, 2.0)))));
                e.getPlayer().sendMessage("§7[§cX§7] Tu ne peux pas sauter en venant de la warzone");
            }






            }
        }
    }
}
