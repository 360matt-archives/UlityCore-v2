package fr.ulity.worldsaver.api.methods.build;

import fr.ulity.core.api.Data;
import fr.ulity.worldsaver.api.methods.Warner;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class ReadJson {

    public static void build (String name, String worldname, Warner warner) {
        Data store = new Data(name, "addons/WorldSaver");
        World world = Bukkit.getWorld(worldname);

        EveryChunks.addChunks(store, world, warner);
    }
}
