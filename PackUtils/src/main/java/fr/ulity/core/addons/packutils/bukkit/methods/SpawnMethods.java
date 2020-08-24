package fr.ulity.core.addons.packutils.bukkit.methods;

import fr.ulity.core_v3.modules.storage.ServerConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SpawnMethods {
    private static final ServerConfig serverConfig = new ServerConfig("spawn");

    public static Location getSpawnLocation () {
        World world = Bukkit.getWorld(serverConfig.getString("world"));

        double x = serverConfig.getDouble("x");
        double y = serverConfig.getDouble("y");
        double z = serverConfig.getDouble("z");
        float pitch = serverConfig.getFloat("pitch");
        float yaw = serverConfig.getFloat("yaw");

        return (world != null) ? new Location(world, x, y, z, yaw, pitch) : null;
    }

    public static void setSpawnLocation (Player player) {
        Location playerLoc = player.getLocation();
        serverConfig.set("world", playerLoc.getWorld().getName());
        serverConfig.set("x", playerLoc.getX());
        serverConfig.set("y", playerLoc.getY());
        serverConfig.set("z", playerLoc.getZ());
        serverConfig.set("pitch", playerLoc.getPitch());
        serverConfig.set("yaw", playerLoc.getYaw());
    }
}
