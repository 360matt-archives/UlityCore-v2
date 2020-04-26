package fr.ulity.core.bukkit.loaders;


import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class IsUpToDate {
    private org.bukkit.plugin.Plugin plugin;
    private int resourceId;

    public IsUpToDate(org.bukkit.plugin.Plugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext())
                    consumer.accept(scanner.next());
            } catch (IOException exception) {
                this.plugin.getLogger().info("     §6Cannot update this plugin :/");
                this.plugin.getLogger().info("§9█§f█§c█  §6Impossible de mettre à jour le plugin :/");
                this.plugin.getLogger().severe(exception.getMessage());
            }
        });
    }

    public void noticeUpdate(){
        getVersion(latest -> {
            String present = plugin.getDescription().getVersion();
            if (!present.equalsIgnoreCase(latest)) {
                this.plugin.getLogger().info("     §6Update available §c" + present + " (old version) §6--> §a" + latest + " (latest version) :)");
                this.plugin.getLogger().info("§9█§f█§c█  §6Une mise à jour est disponible §c" + present + " (ancienne version) §6--> §a" + latest + " (dernière version) :)");
            }
        });
    }

}
