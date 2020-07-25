package fr.ulity.deluxegui.opener;

import fr.ulity.deluxegui.ConfigMan;
import fr.ulity.deluxegui.DeluxeGUI;
import fr.ulity.deluxegui.TranslateVars;
import fr.ulity.deluxegui.mechanism.GuiManager;
import fr.ulity.deluxegui.mechanism.structure.Content;
import fr.ulity.deluxegui.mechanism.structure.GuiStructure;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;

public class Open {
    public static HashMap<String, HashMap<String, Object>> activeInv = new HashMap<>();


    public static void open (Player player, String name, HashMap<String, String> variables) {
        open(player, name, variables, false);
    }

    public static void open (Player player, String name, HashMap<String, String> variables, boolean force) {
        for (GuiStructure x : GuiManager.menus) {
            if (x.name.equals(name)) {
                if (!force && x.permission != null)
                    if (!player.hasPermission(x.permission)) return;


                Inventory newerInv = Bukkit.createInventory(null, 9*6, x.title);
                if (DeluxeGUI.config.getBoolean("random_glass"))
                    for (int k = 0; k <= (6 * 9) - 1; k++)
                        newerInv.setItem(k, new ItemStack(RandomGlass.getRandom(), 1));

                for (Content xc : x.content) {
                    int place = (((xc.line-1)*9) + xc.column) - 1;

                    ItemStack itemStack = new ItemStack(xc.item.material);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.setDisplayName(xc.item.title);
                    meta.setLore(Collections.singletonList(xc.item.lore));
                    itemStack.setItemMeta(meta);

                    newerInv.setItem(place, itemStack);
                }

                HashMap<String, String> varTemplate = TranslateVars.pass(player, new HashMap<>());
                varTemplate.putAll(variables);

                HashMap<String, Object> hash = new HashMap<>();
                hash.put("inventory", newerInv);
                hash.put("variables", varTemplate);
                activeInv.put(name, hash);

                player.openInventory(newerInv);
                break;
            }
        }
    }

}
