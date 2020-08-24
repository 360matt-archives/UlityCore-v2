package fr.ulity.core.addons.superrtp.bukkit.events;

import fr.ulity.core.addons.superrtp.bukkit.MainBukkitRTP;
import fr.ulity.core.addons.superrtp.bukkit.api.SuperRtpApi;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class InvincibleRTP implements Listener {

    @EventHandler
    private static void onDamage (EntityDamageEvent e) {
        if (e.getEntityType().equals(EntityType.PLAYER) && MainBukkitRTP.invincible.containsKey(e.getEntity().getName())) {
            Player player = (Player) e.getEntity();

            if ((long) MainBukkitRTP.invincible.get(player.getName()).get("time") >= new Date().getTime()) {
                Set<EntityDamageEvent.DamageCause> typesToCancel = new HashSet<EntityDamageEvent.DamageCause>();
                typesToCancel.add(EntityDamageEvent.DamageCause.SUFFOCATION);
                typesToCancel.add(EntityDamageEvent.DamageCause.VOID);
                typesToCancel.add(EntityDamageEvent.DamageCause.LAVA);
                typesToCancel.add(EntityDamageEvent.DamageCause.FIRE);

                if (typesToCancel.equals(EntityDamageEvent.DamageCause.FALL)) {
                    e.setCancelled(true);
                } else if (typesToCancel.contains(e.getCause())) {
                    e.setCancelled(true);

                    player.setFireTicks(0);
                    player.setFallDistance(0);

                    SuperRtpApi.playerRTP(player, player.getWorld(), (String) MainBukkitRTP.invincible.get(player.getName()).get("x"));

                    BossBar bossBar = Bukkit.createBossBar(Lang.get("super_RTP.fails.unsafe_location"), BarColor.RED, BarStyle.SEGMENTED_10);
                    bossBar.setProgress(1D);
                    bossBar.addPlayer(player);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(MainBukkitRTP.plugin, new Runnable() {
                        @Override
                        public void run() {
                            bossBar.removePlayer(player);
                        }
                    }, 20*15L); //20 Tick (1 Second) delay before run() is called
                }
            } else
                MainBukkitRTP.invincible.remove(player.getName());
        }
    }
}
