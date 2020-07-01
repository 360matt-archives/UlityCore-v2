package fr.ulity.worldsaver.api.methods;

import fr.ulity.worldsaver.WorldSaver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Progress {
    public int totalChunk;
    public int finishedChunk = 0;

    public int totalVertical;
    public int finishedVertical = 0;

    public Progress (int chunk) {
        this.totalChunk = chunk;
        this.totalVertical = chunk*256;
    }

    public Progress (int chunk, Warner warner) {
        this.totalChunk = chunk;
        this.totalVertical = chunk*256;
        new Scheduler().create(warner);
    }

    public void finishedChunk () {  finishedChunk++; }
    public void finishedVertical () {  finishedVertical++; }
    public double getProgress () { return ((finishedChunk / totalChunk) * 100); }

    private class Scheduler {
        int id;
        public void create (Warner warner) {
            if (finishedChunk != totalChunk) {
                id = Bukkit.getScheduler().scheduleSyncRepeatingTask(WorldSaver.plugin, () -> {
                    if (finishedVertical != totalVertical && (!(warner.sender instanceof Player)) || ((Player) warner.sender).isOnline()) {
                        warner.sender.sendMessage(warner.msg
                                .replaceAll("%finishedChunk%", String.valueOf(finishedChunk))
                                .replaceAll("%totalChunk%", String.valueOf(totalChunk))
                                .replaceAll("%finishedVertical%", String.valueOf(finishedVertical))
                                .replaceAll("%totalVertical%", String.valueOf(totalVertical))
                                .replaceAll("%percent%", String.valueOf( Math.round(finishedVertical*100) / totalVertical )));
                    } else
                        Bukkit.getScheduler().cancelTask(id);
                }, 0L, 20L*warner.delay);

            }
        }
    }



}
