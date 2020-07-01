package fr.ulity.worldsaver.api.methods.save;

import fr.ulity.core.api.Data;
import fr.ulity.worldsaver.api.methods.Progress;
import fr.ulity.worldsaver.api.methods.Warner;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

public class CreateJson {
    public static Progress progress;

    public static void generate (String name, String worldname, Warner warner) {
        Data store = new Data(name, "addons/WorldSaver");
        World world = Bukkit.getWorld(worldname);

        int nb = world.getLoadedChunks().length;

        if (warner != null) {
            progress = new Progress(nb, warner);
        } else
            progress = new Progress(nb);

        for (Chunk chunk : world.getLoadedChunks()) {
            Thread newThread = new Thread(() -> {
                    EveryChunks.addChunk(chunk, store, progress);
            });
            newThread.start();
        }
    }
}
