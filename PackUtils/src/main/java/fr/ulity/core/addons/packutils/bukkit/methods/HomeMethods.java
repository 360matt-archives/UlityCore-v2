package fr.ulity.core.addons.packutils.bukkit.methods;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.Api;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HomeMethods {
    public static Location getHomeLocation (Player player, String home) {
        String prefix = "player." + player.getName() + ".home." + home;
        if (Api.data.contains(prefix)) {
            FlatFileSection locData = Api.data.getSection(prefix);

            World world = Bukkit.getWorld(locData.getString("world"));
            double x = locData.getDouble("x");
            double y = locData.getDouble("y");
            double z = locData.getDouble("z");

            return new Location(world, x, y, z);
        }
        return null;
    }

    public static void setHomeLocation (Player player, String home) {
        FlatFileSection locData = Api.data.getSection("player." + player.getName() + ".home." + home);

        Location playerLoc = player.getLocation();
        locData.set("world", playerLoc.getWorld().getName());
        locData.set("x", playerLoc.getX());
        locData.set("y", playerLoc.getY());
        locData.set("z", playerLoc.getZ());
    }

    public static int getHomeCount (Player player) {
        return Api.data.singleLayerKeySet("player." + player.getName() + ".home").size();
    }

    public static boolean isHomeExist (Player player, String home) {
        return Api.data.singleLayerKeySet("player." + player.getName() + ".home").contains(home);
    }

    public static String[] getHomeListName (Player player) {
        return Api.data.singleLayerKeySet("player." + player.getName() + ".home").toArray(new String[0]);
    }

    public static void delHome (Player player, String home) {
        Api.data.remove("player." + player.getName() + ".home." + home);
    }

}
