package fr.ulity.deluxegui.events;

import fr.ulity.core.api.Lang;
import fr.ulity.deluxegui.TranslateVars;
import fr.ulity.deluxegui.mechanism.GuiManager;
import fr.ulity.deluxegui.mechanism.structure.Content;
import fr.ulity.deluxegui.mechanism.structure.GuiStructure;
import fr.ulity.deluxegui.mechanism.structure.content.action.Gui;
import fr.ulity.deluxegui.opener.Open;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;


import java.util.HashMap;
import java.util.Map;

public class OpenInv implements Listener {

    @EventHandler
    private void onCommand (PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage()
                .replace("/", "")
                .split(" ")
                [0];

        GuiManager.menus.iterator().forEachRemaining(x -> {
            if (x.open.commands.contains(e.getMessage().replaceAll("/", ""))) {
                e.setCancelled(true);
                Open.open(e.getPlayer(), x.name, new HashMap<>());
            }
        });
    }

    @EventHandler (priority = EventPriority.LOW)
    private void onClose (InventoryCloseEvent e) {

        for (Map.Entry<String, HashMap<String, Object>> xInv : Open.activeInv.entrySet()) {
            if (xInv.getValue().get("inventory").equals(e.getInventory())) {
                Open.activeInv.remove(xInv.getKey());
                break;
            }
        }
    }

    @EventHandler
    private void onClick (InventoryClickEvent e) {

        for (Map.Entry<String, HashMap<String, Object>> xInv : Open.activeInv.entrySet()) {
            if (xInv.getValue().get("inventory").equals(e.getInventory())) {
                HashMap<String, String> vars = ((HashMap<String, String>) xInv.getValue().get("variables"));

                // if Inventory was oppened by DeluxeGUI

                e.setCancelled(true);
                // cancel item moves

                for (GuiStructure x : GuiManager.menus) {
                    if (x.name.equals(xInv.getKey())) {
                        // loop GUI and now found the GUI



                        for (Content xc : x.content) {
                            int place = (((xc.line-1)*9) + xc.column) - 1;
                            if (place == e.getSlot()) {

                                // loop contents and now found the correspondant content

                                if (!e.getWhoClicked().hasPermission(xc.extra.permissions))
                                    Lang.prepare("global.no_perm").sendPlayer((Player) e.getWhoClicked());
                                else {
                                    for (String cmd : xc.action.run)
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), TranslateVars.forText(cmd, vars));
                                    for (String cmd : xc.action.sudo)
                                        Bukkit.dispatchCommand(e.getWhoClicked(), TranslateVars.forText(cmd, vars));

                                   // System.out.println(xc.action.gui);

                                    if (xc.action.gui != null) {
                                        // if GUI action is not null

                                        Gui actionGui = xc.action.gui;

                                        for (GuiStructure loopedGui : GuiManager.menus) {
                                            if (loopedGui.name.equals(xc.action.gui.name)) {
                                                vars.putAll(TranslateVars.pass((Player) e.getWhoClicked(), actionGui.variables));
                                                Open.open((Player) e.getWhoClicked(), actionGui.name, vars);
                                                break;
                                            }
                                        }

                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
    }




}
