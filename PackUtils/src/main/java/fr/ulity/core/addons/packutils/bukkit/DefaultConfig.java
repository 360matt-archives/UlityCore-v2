package fr.ulity.core.addons.packutils.bukkit;

public class DefaultConfig {

    public static void applique () {
        MainBukkitPackUtils.config.setDefault("homes.max", 2);
        MainBukkitPackUtils.config.setDefault("homes.staff_bypass", false);
    }

}
