package fr.ulity.core.bukkit.particles_v1.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public final class UtilParticle {
    public static void sendParticle(@NotNull Location location, Particle particle, int count, Vector vector, float speed) {
        location.getWorld().spawnParticle(particle, location, count, vector.getX(), vector.getY(), vector.getZ(), speed);
    }

    public static void sendParticle(@NotNull Location location, Particle particle, int count, Vector vector, float speed, Particle.DustOptions dustOptions) {
        location.getWorld().spawnParticle(particle, location, count, vector.getX(), vector.getY(), vector.getZ(), speed, dustOptions);
    }

}