package net.listenia.survie.surviecore.animations;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import net.listenia.survie.surviecore.SurvieCore;
import net.listenia.survie.surviecore.functions.GetSpawn;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Death {
    private static int count = 0;
    private static int task;



    public void run(Player player, EntityDamageEvent.DamageCause cause, double damage){
        for (double k = 0.0; k<=2; k=k+0.5)
            FastParticle.spawnParticle(player.getWorld(), ParticleType.REDSTONE, player.getLocation().clone().add(0, k, 0), 100, Color.RED);

        FastParticle.spawnParticle(player.getWorld(), ParticleType.EXPLOSION_LARGE, player.getLocation().add(0, 1, 0), 100, Color.RED);

        player.sendTitle("§cTu est mort !", "R.I.P.", 1, 20*3, 1);

        player.teleport(player.getLocation().add(0, 1,0));
        // forcer le vecteur en bougeant d'un bloc vers le haut

        count = 30;
        // nombre de loop

        Location deathLoc = player.getLocation();
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvieCore.plugin, () -> {
            if (!player.isOnline())
                Bukkit.getScheduler().cancelTask(task);

            else if (count == 0) {
                Location spawnLoc = GetSpawn.getSpawnLocation();
                if (spawnLoc != null)
                    player.teleport(spawnLoc);

                Bukkit.getScheduler().cancelTask(task);
            }
            else {
                FastParticle.spawnParticle(player.getWorld(), ParticleType.EXPLOSION_LARGE, deathLoc.add(0, 1, 0), 10, Color.RED);
                FastParticle.spawnParticle(player.getWorld(), ParticleType.REDSTONE, deathLoc.add(0, 1, 0), 10, Color.RED);
                FastParticle.spawnParticle(player, ParticleType.EXPLOSION_LARGE, deathLoc, 100);

                count--;
            }
        }, 20L, 0);
        // animation fly + tp au spawn

    }

}