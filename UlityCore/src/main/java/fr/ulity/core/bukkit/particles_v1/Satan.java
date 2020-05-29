package fr.ulity.core.bukkit.particles_v1;

import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.bukkit.particles_v1.utils.MathUtils;
import fr.ulity.core.bukkit.particles_v1.utils.UtilParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;


public final class Satan {
    // code by: https://www.spigotmc.org/members/julien0312.80619/
    // je suis pas un batard je fournie mes sources
    // toutes les particles viennent de cette personne

    public static ArrayList<ArmorStand> as = new ArrayList<>();

    @SuppressWarnings("deprecation")
    public static void run(Player player) {
        Location loc = player.getLocation();
        ItemStack skull = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setOwner(player.getName());
        skull.setItemMeta(skullMeta);

        ArmorStand armor = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        armor.setVisible(false);
        armor.setCustomName("§c§l" + player.getName());
        armor.setCustomNameVisible(true);
        armor.setHelmet(skull);
        armor.setGravity(false);
        as.add(armor);

        new BukkitRunnable() {
            int i = 0;

            public void run() {
                i++;

                for (int i = 0; i < 2; i++) {
                    UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 2.5D, MathUtils.randomRange(-1.0F, 1.0F)), Particle.SMOKE_LARGE, 1, new Vector(0, 0, 0), 0.0F);
                    UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 2.5D, MathUtils.randomRange(-1.0F, 1.0F)), Particle.SMOKE_LARGE, 1, new Vector(0, 0, 0), 0.0F);
                    UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 2.5D, MathUtils.randomRange(-1.0F, 1.0F)), Particle.SMOKE_LARGE, 1, new Vector(0, 0, 0), 0.0F);
                    UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 2.7D, MathUtils.randomRange(-1.0F, 1.0F)), Particle.SMOKE_LARGE, 1, new Vector(0, 0, 0), 0.0F);
                }

                UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-0.8F, 0.8F), 2.7D, MathUtils.randomRange(-0.8F, 0.8F)), Particle.SMOKE_LARGE, 1, new Vector(0, 0, 0), 0.0F);
                UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-0.8F, 0.8F), 2.7D, MathUtils.randomRange(0.8F, -0.8F)), Particle.SMOKE_LARGE, 1, new Vector(0, 0, 0), 0.0F);
                UtilParticle.sendParticle(loc.clone().add(MathUtils.randomRange(-0.8F, 0.8F), 2.7D, MathUtils.randomRange(-0.8F, 0.8F)), Particle.DRIP_LAVA, 1, new Vector(0, 0, 0), 0.0F);

                if (i == 100) {
                    as.remove(armor);
                    armor.remove();
                    cancel();
                }
            }
        }.runTaskTimer(MainBukkit.plugin, 1, 0);
    }
}