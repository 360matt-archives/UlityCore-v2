package fr.ulity.core.addons.superrtp.bukkit.events;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.addons.superrtp.bukkit.MainBukkitRTP;
import fr.ulity.core.addons.superrtp.bukkit.api.SuperRtpApi;
import fr.ulity.core.api.Cooldown;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class InventoryEventRTP implements Listener {


    @EventHandler
    private static void onInventory (InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals(Lang.get(player, "commands.rtp.expressions.menu_title"))) {

            if (e.getClick().isRightClick())
                e.setCancelled(true);
            else if (e.getClick().isLeftClick()) {
                e.setCancelled(true);

                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().getItemMeta() != null) {
                        String title = Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getDisplayName();
                        String x = MainBukkitRTP.items.get(title);

                        if (MainBukkitRTP.items.containsKey(title)) {
                            // si l'item clické est bien un item de RTP
                            FlatFileSection section = MainBukkitRTP.config.getSection("gui." + x);

                            boolean staffBypass = MainBukkitRTP.config.getBoolean("global.staff_bypass") && player.hasPermission("ulity.superrtp.bypass");

                            String permission = section.getString("extra.permission");
                            if (permission == null || staffBypass || player.hasPermission(permission)) {
                                // si le joueur est autorisé à utiliser ce item

                                int cooldown = section.getInt("extra.cooldown");
                                Cooldown cooldownObj = new Cooldown("rtp_gui_" + x, player.getName());
                                cooldownObj.setPlayer(player);

                                if (staffBypass || cooldown == 0 || !cooldownObj.isInitialized() || cooldownObj.isEnded()) {
                                    // si le joueur n'a pas un délai en cours

                                    int cost = section.getInt("extra.cost");

                                    MainBukkitRTP.ObtainEco obtain = MainBukkitRTP.getEco();

                                    if (obtain.available) { // si l'économie est disponible
                                        if (staffBypass || obtain.eco.getBalance(player) >= cost) {
                                            // si joueur = staff ou qu'il a suffisament d'argent


                                            if (section.singleLayerKeySet().contains("randomWorld")) {
                                                // si la propriété "randomWorld" existe bien

                                                String[] worlds = section.singleLayerKeySet("randomWorld").toArray(new String[]{});
                                                int randomNum = ThreadLocalRandom.current().nextInt(0, worlds.length);
                                                String worldName = worlds[randomNum];


                                                List<String> requiredProperties = Arrays.asList("min.x", "min.z", "max.x", "max.z");
                                                if (section.keySet("randomWorld." + worldName).containsAll(requiredProperties)) {
                                                    // si les propriétés sont bien crée

                                                    World world = Bukkit.getWorld(worldName);
                                                    if (world != null) {

                                                        // ENFIN ! ... il ne peut plus rien nous arriver ...


                                                        if (!staffBypass) {
                                                            // seulement si le joueur n'est pas en status bypass/staff ...

                                                            player.closeInventory();
                                                            obtain.eco.withdrawPlayer(player, cost);
                                                            cooldownObj.applique(cooldown);
                                                            // ... on applique les obligations du joueurs (retrait d'argent, cooldolwn, etc... )
                                                        }

                                                        SuperRtpApi.playerRTP(player, world, x, true);


                                                    } else {
                                                        // si le monde n'existe pas, n'est pas chargé
                                                        player.sendMessage(Lang.get(player, "super_RTP.fails.world_missing")
                                                                .replaceAll("%title%", title)
                                                                .replaceAll("%world%", worldName));

                                                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                                            onlinePlayer.sendMessage(Lang.get(onlinePlayer, "super_RTP.staff_error.unknown_world")
                                                                    .replaceAll("%title%", title)
                                                                    .replaceAll("%world%", worldName));
                                                        }
                                                    }


                                                } else {
                                                    // si la propriété "randomWorld" est mal configurée
                                                    player.sendMessage(Lang.get(player, "super_RTP.fails.location_no_set")
                                                            .replaceAll("%title%", title));

                                                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                                        onlinePlayer.sendMessage(Lang.get(onlinePlayer, "super_RTP.staff_error.location_no_set")
                                                                .replaceAll("%title%", title));
                                                    }
                                                }


                                            } else {
                                                // si la propriété "randomWorld" n'existe pas
                                                player.sendMessage(Lang.get(player, "super_RTP.fails.location_no_set")
                                                        .replaceAll("%title%", title));

                                                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                                    onlinePlayer.sendMessage(Lang.get(onlinePlayer, "super_RTP.staff_error.location_no_set")
                                                            .replaceAll("%title%", title));
                                                }
                                            }


                                        } else { // si le joueur n'a pas assez de money
                                            player.sendMessage(Lang.get(player, "super_RTP.err_messages.no_money")
                                                    .replaceAll("%left%", String.valueOf(cost - obtain.eco.getBalance(player)))
                                                    .replaceAll("%title%", title));
                                        }
                                    } else { // si l'économie n'est pas disponible
                                        player.sendMessage(Lang.get(player, "super_RTP.fails.economy_missing")
                                                .replaceAll("%title%", title));

                                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                            onlinePlayer.sendMessage(Lang.get(player, "super_RTP.staff_error.economy_not_supported")
                                                    .replaceAll("%title%", title));
                                        }
                                    }


                                } else { // si un cooldown est en cours pour le joueur
                                    player.sendMessage(Lang.get(player, "super_RTP.err_messages.no_finished_cooldown")
                                            .replaceAll("%left%", cooldownObj.getTimeLeft().text));
                                }

                            } else { // si le joueur n'a pas la permission
                                player.sendMessage(Lang.get(player, "super_RTP.err_messages.no_permission")
                                        .replaceAll("%title%", title));
                            }
                        }
                    }
                }

            }

        }

    }


}
