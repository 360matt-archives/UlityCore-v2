package fr.ulity.core.bukkit.particles_v1;

import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.bukkit.particles_v1.utils.UtilParticle;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public final class Tornado {
    // code by: https://www.spigotmc.org/members/julien0312.80619/
    // je suis pas un batard je fournie mes sources
    // toutes les particles viennent de cette personne


    public static void run(Player player) {
        run(player.getLocation());
    }

    public static void run(Location loc) {
        new BukkitRunnable() {
            int angle = 0;

            @Override
            public void run() {
                int max_height = 7;
                double max_radius = 5;
                int lines = 5;
                double height_increasement = 0.25;
                double radius_increasement = max_radius / max_height;
                for (int l = 0; l < lines; l++) {
                    for (double y = 0; y < max_height; y += height_increasement) {
                        double radius = y * radius_increasement;
                        double v = 360 / lines * l + y * 30;
                        double x = Math.cos(Math.toRadians(v - angle)) * radius;
                        double z = Math.sin(Math.toRadians(v - angle)) * radius;
                        UtilParticle.sendParticle(loc.clone().add(x, y, z), Particle.CLOUD, 1, new Vector(0, 0, 0), 0.0F);
                    }
                }
                angle++;
                if (angle == 70) {
                    cancel();
                }
            }
        }.runTaskTimer(MainBukkit.plugin, 2, 0);
    }


}