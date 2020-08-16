package fr.ulity.core.addons.packutils.bukkit.methods;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.Api;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SpawnMethods {
    public static Location getSpawnLocation () {
        if (Api.data.contains("spawn")) {
            FlatFileSection locData = Api.data.getSection("spawn");

            World world = Bukkit.getWorld(locData.getString("world"));

            double x = locData.getDouble("x");
            double y = locData.getDouble("y");
            double z = locData.getDouble("z");

            return (world != null) ? new Location(world, x, y, z) : null;
        }
        return null;
    }

    public static void setSpawnLocation (Player player) {
        FlatFileSection locData = Api.data.getSection("spawn");

        Location playerLoc = player.getLocation();
        locData.set("world", playerLoc.getWorld().getName());
        locData.set("x", playerLoc.getX());
        locData.set("y", playerLoc.getY());
        locData.set("z", playerLoc.getZ());
    }
}
