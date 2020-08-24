package fr.ulity.core.addons.superrtp.bukkit.commands;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.addons.superrtp.bukkit.MainBukkitRTP;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.datas.UserCooldown;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Text;
import fr.ulity.core_v3.utils.Time;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CommandRTP extends CommandBukkit {
    private static Inventory preInvMenu = null;

    public CommandRTP() {
        super("rtp");
        setPermission("ulity.SuperRTP.commands.rtp");
    }

    public static Inventory randomiseAndCaching (Player player) {
        boolean cacheStat = MainBukkitRTP.config.getBoolean("global.cache_glass_panel");

        if (preInvMenu != null && cacheStat)
            return preInvMenu;
        else {
            Inventory teleportInvMenu = Bukkit.createInventory(null, 6 * 9, Lang.get(player, "commands.rtp.expressions.menu_title"));
            for (int k = 0; k <= (6 * 9) - 1; k++) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, 7 + 1);
                Material randomMaterial = null;
                if (randomNum == 0)
                    randomMaterial = Material.RED_STAINED_GLASS_PANE;
                else if (randomNum == 1)
                    randomMaterial = Material.YELLOW_STAINED_GLASS_PANE;
                else if (randomNum == 2)
                    randomMaterial = Material.LIME_STAINED_GLASS_PANE;
                else if (randomNum == 3)
                    randomMaterial = Material.CYAN_STAINED_GLASS_PANE;
                else if (randomNum == 4)
                    randomMaterial = Material.PURPLE_STAINED_GLASS_PANE;
                else if (randomNum == 5)
                    randomMaterial = Material.GRAY_STAINED_GLASS_PANE;
                else if (randomNum == 6)
                    randomMaterial = Material.ORANGE_STAINED_GLASS_PANE;
                else if (randomNum == 7)
                    randomMaterial = Material.GREEN_STAINED_GLASS_PANE;

                teleportInvMenu.setItem(k, new ItemStack(randomMaterial, 1));
            }

            if (cacheStat)
                preInvMenu = teleportInvMenu;

            return teleportInvMenu;
        }
    }

    @Override
    public void exec (@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (requirePlayer()){
            Player player = (Player) sender;
            Inventory teleportInvMenu = randomiseAndCaching(player);

            for (String x : MainBukkitRTP.config.singleLayerKeySet("gui")) {
                FlatFileSection section = MainBukkitRTP.config.getSection("gui." + x);
                String title = new Text(new String[]{section.getString("title")})
                        .setColored()
                        .setEncoded()
                        .outputString();

                MainBukkitRTP.items.put(title, x);

                boolean staffBypass = MainBukkitRTP.config.getBoolean("global.staff_bypass") && player.hasPermission("ulity.superrtp.bypass");

                Material material;
                try {
                    material = Material.valueOf(section.getString("item.material"));
                }
                catch (Exception e) {
                    material = Material.DIRT;
                    System.out.println(Lang.get(player, "super_RTP.staff_error.invalid_material")
                            .replaceAll("%name%", title)
                            .replaceAll("%material%", section.getString("item.material")));
                }


                List<String> description = new Text(section.getList("description"))
                        .setColored()
                        .setEncoded()
                        .outputList();
                
                double price = section.getDouble("extra.cost");
                if (!staffBypass && price != 0){
                    description.add(" ");
                    description.add(Lang.get(player, "super_RTP.gui.cost_price")
                            .replaceAll("%price%", Double.toString(price)));
                }

                String permission = section.getString("extra.permission");

                if (!staffBypass && !permission.equals("")) {
                    description.add(" ");
                    description.add(Lang.get(player, "super_RTP.gui.permission")
                            .replaceAll("%stat%", (player.hasPermission(permission)) ? "&a✔" : "&c❌"));
                }

                int cooldown = section.getInt("extra.cooldown");
                if (!staffBypass && cooldown != 0){
                    Time cooldownTime = new Time(cooldown, player);

                    description.add(" ");
                    description.add(Lang.get(player, "super_RTP.gui.cooldown")
                            .replaceAll("%cooldown%", cooldownTime.text));

                    UserCooldown cooldownObj = new UserCooldown(player.getName(), "rtp_gui_" + x);

                    if (cooldownObj.isWaiting()) {
                        Lang.prepare("super_RTP.gui.cooldown_left")
                                .variable("cooldown", cooldownObj.getTimeLeft().text)
                                .sendPlayer(player);
                    }
                }

                if (staffBypass) {
                    description.add(" ");
                    description.add(Lang.get("super_RTP.gui.staff_mod"));
                }

                ItemStack itemStack = new ItemStack(material, 1);
                ItemMeta iteamMeta = itemStack.getItemMeta();
                iteamMeta.setDisplayName(title);
                iteamMeta.setLore(description);
                itemStack.setItemMeta(iteamMeta);

                int place = (((section.getInt("item.line")-1)*9) + section.getInt("item.column")) - 1;
                teleportInvMenu.setItem(place, itemStack);
            }

            player.openInventory(teleportInvMenu);
        }
    }
}