package fr.ulity.core.bukkit.particles_v1;

import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.bukkit.particles_v1.utils.UtilParticle;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public final class FrostFlame {
    // code by: https://www.spigotmc.org/members/julien0312.80619/
    // je suis pas un batard je fournie mes sources
    // toutes les particles viennent de cette personne

    public static void run(Player player) {
        run(player.getLocation());
    }

    public static void run(Location loc) {
        new BukkitRunnable() {
            double t = 0.0D;

            public void run() {
                t += 0.3;
                for (double phi = 0.0D; phi <= 6; phi += 1.5) {
                    double x = 0.11D * (12.5 - t) * Math.cos(t + phi);
                    double y = 0.23D * t;
                    double z = 0.11D * (12.5 - t) * Math.sin(t + phi);
                    loc.add(x, y, z);
                    UtilParticle.sendParticle(loc, Particle.FLAME, 1, new Vector(0, 0, 0), 0.0F);
                    loc.subtract(x, y, z);

                    if (t >= 12.5) {
                        loc.add(x, y, z);
                        if (phi > Math.PI) {
                            cancel();
                        }
                    }
                }
            }
        }.runTaskTimer(MainBukkit.plugin, 1L, 1L);
    }

}