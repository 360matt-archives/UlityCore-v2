package fr.ulity.worldsaver.api.methods.save;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.Data;
import fr.ulity.worldsaver.api.methods.LimitComma;
import fr.ulity.worldsaver.api.methods.Progress;
import org.bukkit.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EveryChunks {

    public static void addChunk (Chunk chunk, Data data, Progress progress) {

        FlatFileSection prefix = data.getSection(LimitComma.getReduced(chunk.getX()) + "-" + LimitComma.getReduced(chunk.getZ()));




        for (int x = 0; x < 16; x++) {
            final int xFinal = x;

            for (int z = 0; z < 16; z++) {
                for (int y = 1; y < 256; y++) {
                    int initialY = y;
                    List<String> blocs = new ArrayList<>();

                    while (!chunk.getBlock(xFinal, y, z).isEmpty()) {
                        String currentType = chunk.getBlock(xFinal, y, z).getType().toString();

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
                        prefix.set(xFinal + "-" + initialY + "-" + z, blocs);

                }
                progress.finishedVertical();
            }


        }
        progress.finishedChunk();





    }

}
