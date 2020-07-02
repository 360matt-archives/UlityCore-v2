package fr.ulity.worldsaver.api.methods.save;

import fr.ulity.core.api.Data;
import fr.ulity.worldsaver.api.methods.Progress;
import fr.ulity.worldsaver.api.methods.Warner;
import org.bukkit.Chunk;
import org.bukkit.World;

public class CreateJson {
    public static Progress progress;

    public static void generate (String name, World world, Chunk[] chunks, Warner warner) {
        Data store = new Data(name, "addons/WorldSaver");

        progress = new Progress(chunks.length, warner);
        Thread newThread = new Thread(() -> {
            for (Chunk chunk : chunks)
                EveryChunks.addChunk(world, chunk, store, progress);
        });
        newThread.start();
    }
}
