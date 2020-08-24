package fr.ulity.core.addons.packutils.api;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.modules.datas.UserData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserHome {
    private UserData userData;
    private String server = Core.servername;

    public UserHome (String playername) { this.userData = new UserData(playername); }

    public UserHome (String playername, String servername) {
        this.userData = new UserData(playername);
        this.server = servername;
    }

    public boolean isExist (String name) { return userData.singleLayerKeySet("homes." + server).contains(name); }
    public int getCount () { return userData.singleLayerKeySet("homes." + server).size(); }
    public List<String> getList () { return new ArrayList<>(userData.singleLayerKeySet("homes." + server)); }

    public void setHome (String name, Location location) {
        FlatFileSection locData = userData.getSection("homes." + server + "." + name);
        locData.set("world", location.getWorld().getName());
        locData.set("x", location.getX());
        locData.set("y", location.getY());
        locData.set("z", location.getZ());
    }

    public Location getLocation (String name) {
        if (isExist(name)) {
            FlatFileSection locData = userData.getSection("homes." + server + "." + name);

            World world = Bukkit.getWorld(locData.getString("world"));
            double x = locData.getDouble("x");
            double y = locData.getDouble("y");
            double z = locData.getDouble("z");

            return new Location(world, x, y, z);
        }
        return null;
    }

    public void remove (String name) { userData.remove("homes." + server + "." + name); }



}
