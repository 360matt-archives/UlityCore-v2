package fr.ulity.core.bukkit.particles_v1;

import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.bukkit.particles_v1.utils.MathUtils;
import fr.ulity.core.bukkit.particles_v1.utils.UtilParticle;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public final class Mousse {
    public static void run(Player player) {
        run(player.getLocation());
    }

    public static void run(Location location) {
        Location loc = location.add(0, -0.3, 0);
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                if (i >= 20) {
                    for (int a = 0; a <= (i * 3); a++) {
                        UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-1.0f, 1.0f), i*0.1, MathUtils.randomRange(-1.0f, 1.0f)), Particle.CLOUD, 1, new Vector(0, 0.5, 0), 0.00F);
                        UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-1.0f, 1.0f), i*0.1, MathUtils.randomRange(-1.0f, 1.0f)), Particle.REDSTONE, 3, new Vector(0, 1.5, 0), 0.00F, new Particle.DustOptions(Color.RED, 2));
                    }
                    cancel();
                }
                for (int a = 0; a <= (i * 2); a++) {
                    UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-1.0f, 1.0f), (i*0.1), MathUtils.randomRange(-1.0f, 1.0f)), Particle.CRIT, 1, new Vector(0, 0.3, 0), 0.0F);
                    UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-1.0f, 1.0f), i*0.1, MathUtils.randomRange(-1.0f, 1.0f)), Particle.CRIT, 1, new Vector(0, 0.3, 0), 0.0F);
                    UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-1.0f, 1.0f), i*0.1, MathUtils.randomRange(-1.0f, 1.0f)), Particle.CRIT, 1, new Vector(0, 0.3, 0), 0.0F);
                }

                i++;
            }
        }.runTaskTimer(MainBukkit.plugin, 1, 0);
    }
}