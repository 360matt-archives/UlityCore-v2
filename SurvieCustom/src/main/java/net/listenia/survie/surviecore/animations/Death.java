package net.listenia.survie.surviecore.animations;

import de.leonhard.storage.sections.FlatFileSection;
import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import fr.ulity.core.api.Api;
import net.listenia.survie.surviecore.SurvieCore;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class Death {
    private static int count = 0;
    private static int task;

    public static Location getSpawnLocation () {
        if (Api.data.isSet("spawn")) {
            FlatFileSection locData = Api.data.getSection("spawn");

            World world = Bukkit.getWorld(locData.getString("world"));
            System.out.println(world.getName());

            double x = locData.getDouble("x");
            double y = locData.getDouble("y");
            double z = locData.getDouble("z");

            return new Location(world, x, y, z);
        }
        return null;
    }

    public static void run(Player player, EntityDamageEvent.DamageCause cause, double damage){
        GameMode oldGameMode = player.getGameMode();

        for (double k = 0.0; k<=2; k=k+0.5)
            FastParticle.spawnParticle(player.getWorld(), ParticleType.REDSTONE, player.getLocation().clone().add(0, k, 0), 100, Color.RED);

        FastParticle.spawnParticle(player.getWorld(), ParticleType.EXPLOSION_LARGE, player.getLocation().add(0, 1, 0), 100, Color.RED);

        player.sendTitle("§cTu est mort !", "R.I.P.", 1, 20*3, 1);

        player.setGameMode(GameMode.SPECTATOR);
        player.setHealth(20);

        Location oldLocation = player.getLocation();
        oldLocation.setY(oldLocation.getY() + 1);
        player.teleport(oldLocation);
        // forcer le vecteur en bougeant d'un bloc vers le haut

        count = 30;
        // nombre de loop

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(SurvieCore.plugin, () -> {
            if (!player.isOnline())
                Bukkit.getScheduler().cancelTask(task);

            else if (count == 0) {
                EntityDamageEvent ede = new EntityDamageEvent(player, cause, damage);
                Bukkit.getPluginManager().callEvent(ede);
                if (!ede.isCancelled()) {
                    ede.getEntity().setLastDamageCause(ede);
                    player.setHealth(0);
                }

                Location spawnLoc = getSpawnLocation();
                if (spawnLoc != null)
                    player.teleport(spawnLoc);

                player.setGameMode(oldGameMode);

                Bukkit.getScheduler().cancelTask(task);
            }
            else {
                Vector unitVector = new Vector(0, 1, 0);
                unitVector.multiply(2);
                player.setVelocity(unitVector);

                FastParticle.spawnParticle(player.getWorld(), ParticleType.EXPLOSION_LARGE, player.getLocation().add(0, 1, 0), 10, Color.RED);
                FastParticle.spawnParticle(player.getWorld(), ParticleType.REDSTONE, player.getLocation().add(0, 1, 0), 10, Color.RED);
                FastParticle.spawnParticle(player, ParticleType.EXPLOSION_LARGE, player.getLocation(), 100);

                count--;
            }
        }, 20L, 0);
        // animation fly + tp au spawn

    }

}