package fr.ulity.moderation.api.sanctions.freeze;

import fr.ulity.core_v3.messaging.SocketClient;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.moderation.api.sanctions.FreezeUser;
import fr.ulity.moderation.bukkit.MainModBukkit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.json.JSONObject;

public class BukkitFreezeSystem {

    public static void regListener () {
        if (SocketClient.isEnabled()) {
            new SocketClient.SocketListener("freeze") {
                @Override
                public void receive(JSONObject json) {
                    new BukkitFreezeSystem().recallFreeze(json.getString("player"));
                }
            };
            new SocketClient.SocketListener("unfreeze") {
                @Override
                public void receive(JSONObject json) {
                    new BukkitFreezeSystem().recallUnFreeze(json.getString("player"));
                }
            };
        }
    }

    private int task;

    public void recallFreeze (String playername) {
        Player player = Bukkit.getPlayer(playername);
        FreezeUser freezeUser = new FreezeUser(playername);

        if (player != null) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 999999, 50));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 50));

            player.leaveVehicle();
            player.setWalkSpeed(-1);
            player.setFlySpeed(0);

            StringBuilder looped = new StringBuilder();
            for (int i = 0; i<100; i++)
                looped.append(" \n");

            task = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainModBukkit.plugin, () -> {
                if (!player.isOnline())
                    Bukkit.getScheduler().cancelTask(task);
                else if (!freezeUser.isFrozen()) {
                    Bukkit.getScheduler().cancelTask(task);
                    recallUnFreeze(playername);
                }
                else {
                    player.sendMessage(looped.toString());

                    Lang.prepare("commands.freeze.expressions.notification")
                            .variable("reason", freezeUser.reason)
                            .variable("staff", freezeUser.staff)
                            .sendPlayer(player);

                }
            }, 0L, 3 * 20L);
        }
    }


    public void recallUnFreeze (String playername) {
        Player player = Bukkit.getPlayer(playername);
        if (player != null) {
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);

            player.setWalkSpeed(0.2F);
            player.setFlySpeed(0.2F);
        }
    }


}
