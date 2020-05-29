package fr.ulity.core.bukkit.particles_v1;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static fr.ulity.core.bukkit.particles_v1.utils.MathUtils.randomRange;

public final class Redstone {
    // code by: https://www.spigotmc.org/members/julien0312.80619/
    // je suis pas un batard je fournie mes sources
    // toutes les particles viennent de cette personne

    public static void run(Player player) {
        run(player.getLocation());
    }

    public static void run(Location loc) {
        for (double height = 0.0; height < 1.0; height += 0.8) {
            loc.getWorld().playEffect(loc.clone().add(randomRange(-1.0f, 1.0f), height, randomRange(-1.0f, 1.0f)), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
            loc.getWorld().playEffect(loc.clone().add(randomRange(1.0f, -1.0f), height, randomRange(-1.0f, 1.0f)), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        }
    }
}