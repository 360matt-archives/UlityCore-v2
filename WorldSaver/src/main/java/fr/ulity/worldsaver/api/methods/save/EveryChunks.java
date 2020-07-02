package fr.ulity.worldsaver.api.methods.save;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.Data;
import fr.ulity.worldsaver.api.methods.LimitBlocs;
import fr.ulity.worldsaver.api.methods.LimitComma;
import fr.ulity.worldsaver.api.methods.Progress;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EveryChunks {

    public static void addChunk (World world, Chunk chunk, Data data, Progress progress) {
        FlatFileSection prefix = data.getSection(LimitComma.getReduced(chunk.getX()) + "-" + LimitComma.getReduced(chunk.getZ()));



        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Block blockInChunk = chunk.getBlock(x, 1, z);
                int highestY = world.getHighestBlockYAt(blockInChunk.getX(), blockInChunk.getX());

                //System.out.println("\nFirst: " + new Date().getTime());
                for (int y = 1; y < highestY + 1; y++) {
                    int initialY = y;
                    List<String> blocs = new ArrayList<>();

                    while (!chunk.getBlock(x, y, z).isEmpty()) {
                        String currentType = LimitBlocs.getReduced(chunk.getBlock(x, y, z).getType().toString());

                        if (blocs.size() == 0)
                            blocs.add(currentType);
                        else {
                            int lastIndex = blocs.size() - 1;
                            String last = blocs.get(lastIndex);

                            Pattern pattern = Pattern.compile("#([0-9]*)?");
                            Matcher matcher = pattern.matcher(last);

                            if (last.equals(currentType)) {
                                blocs.remove(blocs.get(lastIndex));
                                blocs.add(currentType + "#2");
                            } else if (matcher.find()) {
                                blocs.remove(blocs.get(lastIndex));
                                int oldValue = Integer.parseInt(matcher.group(1));
                                blocs.add(currentType + "#" + (oldValue + 1));
                            } else
                                blocs.add(currentType);
                        }
                        y++;
                    }

                    if (blocs.size() > 0)
                        prefix.set(x + "-" + initialY + "-" + z, blocs);

                }
                progress.finishedVertical();
                //System.out.println("Finish: " + new Date().getTime());
            }

        }
        progress.finishedChunk();

    }

}
