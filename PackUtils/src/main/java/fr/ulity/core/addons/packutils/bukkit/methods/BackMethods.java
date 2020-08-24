package fr.ulity.core.addons.packutils.bukkit.methods;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core_v3.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BackMethods {
    public static Location getLastLocation (Player player) {
        String prefix = "player." + player.getName() + ".lastLocation";
        if (Core.temp.contains(prefix)) {
            FlatFileSection locData = Core.temp.getSection(prefix);

            World world = Bukkit.getWorld(locData.getString("world"));
            double x = locData.getDouble("x");
            double y = locData.getDouble("y");
            double z = locData.getDouble("z");

            return new Location(world, x, y, z);
        }
        return null;
    }

    public static void setLastLocation (Player player) {
        FlatFileSection locData = Core.temp.getSection("player." + player.getName() + ".lastLocation");

        Location playerLoc = player.getLocation();
        locData.set("world", playerLoc.getWorld().getName());
        locData.set("x", playerLoc.getX());
        locData.set("y", playerLoc.getY());
        locData.set("z", playerLoc.getZ());
    }
}
