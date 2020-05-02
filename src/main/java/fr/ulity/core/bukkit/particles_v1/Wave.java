package fr.ulity.core.bukkit.particles_v1;

import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.bukkit.particles_v1.utils.UtilParticle;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public final class Wave {
    // code by: https://www.spigotmc.org/members/julien0312.80619/
    // je suis pas un batard je fournie mes sources
    // toutes les particles viennent de cette personne

    public static void run(Player player) {
        run(player.getLocation());
    }

    public static void run(Location loc) {
        new BukkitRunnable() {
            double t = Math.PI / 4;

            public void run() {
                t = t + 0.1 * Math.PI;
                for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
                    double x = t * Math.cos(theta);
                    double y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
                    double z = t * Math.sin(theta);
                    loc.add(x, y, z);

                    UtilParticle.sendParticle(loc, Particle.DRIP_WATER, 1, new Vector(0, 0, 0), 0.0F);
                    UtilParticle.sendParticle(loc, Particle.SNOW_SHOVEL, 1, new Vector(0, 0, 0), 0.0F);

                    loc.subtract(x, y, z);
                    theta = theta + Math.PI / 64;
                }
                if (t > 8) {
                    cancel();
                }
            }
        }.runTaskTimer(MainBukkit.plugin, 4, 0);
    }
}