package fr.ulity.core.bukkit.particles_v1;

import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.bukkit.particles_v1.utils.UtilParticle;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public final class Squid {
    // code by: https://www.spigotmc.org/members/julien0312.80619/
    // je suis pas un batard je fournie mes sources
    // toutes les particles viennent de cette personne

    public static ArrayList<ArmorStand> as = new ArrayList<>();

    @SuppressWarnings("deprecation")
    public static void run(Player player) {
        Location loc = player.getLocation().add(0, -0.3, 0);

        ArmorStand armor = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0, -1, 0), EntityType.ARMOR_STAND);
        armor.setVisible(false);
        armor.setGravity(false);

        Entity e = player.getWorld().spawnEntity(loc, EntityType.SQUID);
        armor.setPassenger(e);

        as.add(armor);
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                i++;
                Entity passenger = armor.getPassenger();
                armor.eject();
                armor.teleport(armor.getLocation().add(0, 0.5, 0));
                armor.setPassenger(passenger);

                UtilParticle.sendParticle(armor.getLocation().add(0.0, -0.2, 0.0), Particle.FLAME, 1, new Vector(0, 0, 0), 0.0F);

                player.playSound(loc, Sound.ENTITY_CHICKEN_EGG, 1f, 1f);
                if (i == 20) {
                    as.remove(armor);
                    armor.remove();
                    e.remove();

                    UtilParticle.sendParticle(armor.getLocation().add(0.0, 0.5, 0.0), Particle.EXPLOSION_HUGE, 1, new Vector(0, 0, 0), 0.0F);

                    i = 0;
                    cancel();
                }
            }
        }.runTaskTimer(MainBukkit.plugin, 1, 0);
    }
}