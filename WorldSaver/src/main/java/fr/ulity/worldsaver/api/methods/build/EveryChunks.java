package fr.ulity.worldsaver.api.methods.build;

import fr.ulity.core.api.Data;
import fr.ulity.worldsaver.api.methods.LimitComma;
import fr.ulity.worldsaver.api.methods.Progress;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EveryChunks {
    public static void addChunks (Data data, Progress progress, World world) {

        Set<String> chunks = data.singleLayerKeySet();

        for (String chunk : chunks) {
            Pattern patternChunk = Pattern.compile("([a-j]?[0-9]*)-([a-j]?[0-9]*)");
            Matcher matcher = patternChunk.matcher(chunk);

            if (matcher.find()) {

                int chunkX = Integer.parseInt(LimitComma.getDevelopped(matcher.group(1)));
                int chunkZ = Integer.parseInt(LimitComma.getDevelopped(matcher.group(2)));


                Chunk chunkObj = world.getChunkAt(chunkX, chunkZ);

                Set<String> blocs = data.singleLayerKeySet(chunk);
                for (String bloc : blocs) {
                    Pattern patternBloc = Pattern.compile("([0-9]*)-([0-9]*)-([0-9]*)");
                    Matcher matcherBloc = patternBloc.matcher(bloc);

                    if (matcherBloc.find()) {
                        int blocX = Integer.parseInt(LimitComma.getDevelopped(matcherBloc.group(1)));
                        int blocY = Integer.parseInt(LimitComma.getDevelopped(matcherBloc.group(2)));
                        int blocZ = Integer.parseInt(LimitComma.getDevelopped(matcherBloc.group(3)));

                        String[] blocList = data.getList(chunk + "." + bloc).toArray(new String[0]);

                        for (String entry : blocList) {
                            Pattern patternEntry = Pattern.compile("([A-za-z]*)#([0-9]*)");
                            Matcher matcherEntry = patternEntry.matcher(entry);


                            if (matcherEntry.find()) {
                                for (int k = 0; k < Integer.parseInt(matcherEntry.group(2)); k++) {
                                    chunkObj.getBlock(blocX, blocY, blocZ).setType(Material.valueOf(matcherEntry.group(1)));
                                    blocY++;
                                }
                            } else {
                                chunkObj.getBlock(blocX, blocY, blocZ).setType(Material.valueOf(entry));
                                blocY++;
                            }
                        }
                    }
                }
            }

            progress.finishedChunk();
        }








    }
}
