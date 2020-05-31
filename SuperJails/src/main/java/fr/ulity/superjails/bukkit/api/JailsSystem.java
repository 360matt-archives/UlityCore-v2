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
            FlatFileSection section = jails.getSection("jails." + name);
            section.set("x", loc.getX());
            section.set("y", loc.getY());
            section.set("z", loc.getZ());
            section.set("world", Objects.requireNonNull(loc.getWorld()).getName());
        }

        return response;
    }

    public static Status removeJail (String name) {
        Status response = new Status();

        if (!jails.isSet("jails." + name))
            response.setStatus(false, "no exist");
        else
            jails.remove("jails." + name);

        return response;
    }

    public static boolean exist (String name) {
        return jails.isSet("jails." + name);
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
            FlatFileSection section = jails.getSection("jails." + name);
            World world = Bukkit.getWorld(section.getString("world"));
            Location loc = new Location(world, section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));

            response.data = loc;
        }

        return response;
    }

    public static Status setCustomMessage (String name, String message) {
        Status response = new Status();

        if (!jails.isSet(name))
            response.setStatus(false, "no exist");
        else {
            FlatFileSection section = jails.getSection("jails." + name);
            section.set("message", message);
        }

        return response;
    }


    public static Status getCustomMessage (String name) {
        Status response = new Status();

        if (!jails.isSet(name))
            response.setStatus(false, "no exist");
        else {
            FlatFileSection section = jails.getSection("jails." + name);
            String message = section.getString("message");

            if (message == null)
                response.setStatus(false, "message undefined");
            else
                response.data = message;
        }

        return response;
    }

    public static String[] getAllJails () {
        return jails.singleLayerKeySet("jails").toArray(new String[]{});
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
