package fr.ulity.core.bukkit.particles_v1;

import fr.ulity.core.bukkit.MainBukkit;
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


public final class HeadExplose {
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

        ArmorStand armor = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0, -1, 0), EntityType.ARMOR_STAND);
        armor.setVisible(false);
        armor.setCustomName("§c§l" + player.getName());
        armor.setCustomNameVisible(true);
        armor.setHelmet(skull);
        armor.setGravity(false);

        as.add(armor);
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                i++;
                armor.teleport(armor.getLocation().add(0, 0.5, 0));
                armor.setHeadPose(armor.getHeadPose().add(0.0, 1, 0.0));

                UtilParticle.sendParticle(armor.getLocation().add(0.0, -0.2, 0.0), Particle.FLAME, 1, new Vector(0, 0, 0), 0.0F);

                if (i == 20) {
                    as.remove(armor);
                    armor.remove();

                    UtilParticle.sendParticle(armor.getLocation().add(0.0, 0.5, 0.0), Particle.EXPLOSION_HUGE, 1, new Vector(0, 0, 0), 0.0F);
                    cancel();
                }
            }
        }.runTaskTimer(MainBukkit.plugin, 1, 0);
    }
}