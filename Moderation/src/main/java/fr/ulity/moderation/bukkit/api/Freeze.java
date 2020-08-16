package fr.ulity.moderation.bukkit.api;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.Config;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.moderation.bukkit.MainModBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Freeze {
    private static final Config freeze = new Config("freeze", "addons/moderation");

    private final FlatFileSection playerFreeze;
    private final String playername;

    public String reason;
    public String responsable;
    public long timestamp;

    private int task;


    public Freeze (String playername) {
        this.playername = playername;
        playerFreeze = freeze.getSection(playername);

        if (isFreeze()){
            reason = playerFreeze.getString("reason");
            responsable = playerFreeze.getString("responsable");
            timestamp = playerFreeze.getLong("timestamp");
        }
    }

    public void freeze () {
        playerFreeze.set("reason", reason);
        playerFreeze.set("responsable", responsable);
        playerFreeze.set("timestamp", timestamp);

        recallFreeze();
    }


    public void recallFreeze () {
        Player player = Bukkit.getPlayer(this.playername);
        if (player != null) {

            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainModBukkit.plugin, () -> {
                if (!player.isOnline())
                    Bukkit.getScheduler().cancelTask(task);
                else if (!isFreeze()) {
                    Bukkit.getScheduler().cancelTask(task);
                    recallUnFreeze();
                }
                else {
                    StringBuilder looped = new StringBuilder();
                    for (int i = 0; i<100; i++)
                        looped.append(" \n");
                    player.sendMessage(looped.toString());

                    player.sendMessage(LangBukkit.get(player, "commands.freeze.expressions.notification")
                            .replaceAll("%reason%", reason)
                            .replaceAll("%staff%", responsable));
                }
            }, 0L, 3 * 20L);

            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 50));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 50));

            player.leaveVehicle();
            player.setWalkSpeed(-1);
            player.setFlySpeed(0);
        }
    }


    public void unfreeze () {
        freeze.remove(playername);
        Bukkit.getScheduler().cancelTask(task);

        recallUnFreeze();
    }

    public void recallUnFreeze () {
        freeze.remove(playername);
        Player player = Bukkit.getPlayer(this.playername);
        if (player != null) {
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);

            player.setWalkSpeed(0.2F);
            player.setFlySpeed(0.2F);
        }
    }

    public boolean isFreeze () {
        return freeze.get(playername) != null;
    }
}