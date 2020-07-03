package fr.ulity.worldsaver.api.methods.save;

import fr.ulity.core.api.Data;
import fr.ulity.worldsaver.api.methods.LimitBlocs;
import fr.ulity.worldsaver.api.methods.LimitID;
import fr.ulity.worldsaver.api.methods.Progress;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.*;

public class EveryChunks {

    public static void addChunk (World world, Chunk chunk, Data data, Progress progress, DataInMemory inMemory) {
        String prefix = LimitID.getReduced(chunk.getX()) + "-" + LimitID.getReduced(chunk.getZ());

        // value: List<String>

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

                Block blockInChunk = chunk.getBlock(x, 1, z);
                int highestY = world.getHighestBlockYAt(blockInChunk.getX(), blockInChunk.getX());


                for (int y = 1; y < highestY + 1; y++) {
                    int initialY = y;
                    List<String> blocs = new ArrayList<>();

                    int lastIndice = 1;
                    while (!chunk.getBlock(x, y, z).isEmpty()) {
                        String currentType = LimitBlocs.getReduced(chunk.getBlock(x, y, z).getType().toString());

                        if (blocs.size() == 0)
                            blocs.add(currentType);
                        else {
                            int lastIndex = blocs.size() - 1;
                            String last = blocs.get(lastIndex);

                            if (last.contains(currentType)) {
                                blocs.remove(lastIndex);
                                blocs.add(currentType + ((lastIndice > 1) ? "#" + lastIndice : ""));
                                lastIndice++;
                            } else {
                                lastIndice = 1;
                                blocs.add(currentType);
                            }
                        }
                        y++;
                    }

                    if (blocs.size() > 0) {
                        String xSave = LimitBlocs.getReduced(String.valueOf(x));
                        String ySave = LimitBlocs.getReduced(String.valueOf(initialY));
                        String zSave = LimitBlocs.getReduced(String.valueOf(z));

                        inMemory.data.put(prefix + "." + xSave + "-" + ySave + "-" + zSave, blocs);
                    }


                }
                progress.finishedVertical();
            }
        }


        progress.finishedChunk();

    }

}
