package fr.ulity.core.addons.superrtp.bukkit;

import java.io.InputStream;

public final class ConfigCopy {
    public static void setDefault () {
        if (!MainBukkitRTP.config.singleLayerKeySet().contains("gui")) {
            InputStream stream_gui = ConfigCopy.class.getResourceAsStream("/fr/ulity/core/addons/superrtp/bukkit/config.yml");
            MainBukkitRTP.config.addDefaultsFromInputStream(stream_gui);
        }

        InputStream stream_global = ConfigCopy.class.getResourceAsStream("/fr/ulity/core/addons/superrtp/bukkit/config_global.yml");
        MainBukkitRTP.config.addDefaultsFromInputStream(stream_global);
    }
}
