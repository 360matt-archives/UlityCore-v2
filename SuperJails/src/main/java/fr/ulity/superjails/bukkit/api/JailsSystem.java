package fr.ulity.superjails.bukkit.api;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

public final class JailsSystem {
    public static Config jails = new Config("jails", "jails");

    public static Status createJail (String name, Location loc) {
        Status response = new Status();

        if (jails.isSet(name))
            response.setStatus(false, "already exist");
        else {
            FlatFileSection section = jails.getSection(name);
            section.set("x", loc.getX());
            section.set("y", loc.getY());
            section.set("z", loc.getZ());
            section.set("world", Objects.requireNonNull(loc.getWorld()).getName());
        }

        return response;
    }

    public static Status removeJail (String name) {
        Status response = new Status();

        if (!jails.isSet(name))
            response.setStatus(false, "no exist");
        else
            jails.delete(name);

        return response;
    }

    public static boolean exist (String name) {
        return jails.isSet(name);
    }

    public static Status isValid (String name) {
        Status response = new Status();
        String worldname = jails.getString(name + ".world");

        if (!exist(name))
            response.setStatus(false, "no exist");
        else if (Bukkit.getWorld(worldname) == null)
            response.setStatus(false, "world no exist", worldname);

        return response;
    }

    public static Status getLocation (String name) {
        Status response = new Status();

        Status checkValid = isValid(name);
        if (!checkValid.success)
            response.setStatus(false, checkValid.code, checkValid.data);
        else {
            FlatFileSection section = jails.getSection(name);
            World world = Bukkit.getWorld(section.getString("world"));
            Location loc = new Location(world, section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));

            response.setStatus(true, "success", loc);
        }




        return response;
    }




    public static class Status {
        public Boolean success = true;
        public String code = "success";
        public Object data = null;

        private void setStatus(Boolean success, String err) {
            this.success = success;
            this.code = err;
        }

        private void setStatus(Boolean success, String err, Object data) {
            this.success = success;
            this.code = err;
            this.data = data;
        }
    }
}
