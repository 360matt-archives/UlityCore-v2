package fr.ulity.deluxegui.mechanism;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.Config;
import fr.ulity.deluxegui.DeluxeGUI;
import fr.ulity.deluxegui.mechanism.structure.Content;
import fr.ulity.deluxegui.mechanism.structure.GuiStructure;
import fr.ulity.deluxegui.mechanism.structure.open.Item;
import org.bukkit.Material;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class GuiManager {
    public static HashSet<GuiStructure> menus = new HashSet<>();

    public static void loader () {
        menus.clear();

        try (Stream<Path> walk = Files.walk(Paths.get(Api.prefix + "/addons/DeluxeGUI/gui"))) {

            walk.map(Path::toString)
                    .filter(f -> f.contains(".yml"))
                    .forEach(x -> {
                        Config gui = new Config(new File(x));
                        GuiStructure struct = new GuiStructure();

                        try {

                            struct.name = gui.getFile().getName().replaceAll("\\.[A-Za-z]*", "");
                            struct.permission = gui.getString("permission");

                            if (gui.contains("open.cmd"))
                                struct.open.commands = gui.getStringList("open.cmd");
                            if (gui.contains("open.item"))
                                for (String xi : gui.singleLayerKeySet("open.item")) {
                                    Item item = new Item();
                                    item.material = Material.valueOf(gui.getString("open.item." + xi + ".material"));
                                    item.title = gui.getString("open.item." + xi + ".title");
                                    struct.open.items.add(item);
                                }

                            if (gui.contains("content")) {
                                for (String xc : gui.singleLayerKeySet("content")) {
                                    Content content = new Content();
                                    content.line = gui.getInt("content." + xc + ".line");
                                    content.column = gui.getInt("content." + xc + ".column");

                                    if (!gui.contains("content." + xc + ".item"))
                                        continue;

                                    content.item.material = Material.valueOf(gui.getString("content." + xc + ".item.material"));
                                    content.item.title = gui.getString("content." + xc + ".item.title");
                                    content.item.lore = gui.getString("content." + xc + ".item.lore");

                                    content.extra.permissions = gui.getString("content." + xc + ".extra.permission");


                                    if (gui.contains("content." + xc + ".actions")) {
                                        if (gui.contains("content." + xc + ".actions.cmd")){
                                            Object obj = gui.get("content." + xc + ".actions.cmd");
                                            if (obj instanceof List)
                                                content.action.run = (List<String>) obj;
                                            else if (obj instanceof String)
                                                content.action.run = Collections.singletonList((String) obj);
                                        }
                                        if (gui.contains("content." + xc + ".actions.sudo")){
                                            Object obj = gui.get("content." + xc + ".actions.sudo");
                                            if (obj instanceof List)
                                                content.action.sudo = (List<String>) obj;
                                            else if (obj instanceof String)
                                                content.action.sudo = Collections.singletonList((String) obj);
                                        }
                                        if (gui.contains("content." + xc + ".actions.gui")){
                                            HashMap<String, String> vars = new HashMap<>();
                                            for (String vava : gui.singleLayerKeySet("content." + xc + ".actions.gui.variables"))
                                                vars.put(vava, gui.getString("content." + xc + ".actions.gui.variables." + vava));

                                            content.action.gui.name = gui.getString("content." + xc + ".actions.gui.name");
                                            content.action.gui.variables = vars;

                                        }
                                    }
                                    struct.content.add(content);
                                }
                            }

                            menus.add(struct);

                        } catch (Exception e) {
                            DeluxeGUI.plugin.getLogger().warning("Error, GUI at §6" + x + " §ewas not loaded !");
                            DeluxeGUI.plugin.getLogger().warning(e.getMessage());

                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
