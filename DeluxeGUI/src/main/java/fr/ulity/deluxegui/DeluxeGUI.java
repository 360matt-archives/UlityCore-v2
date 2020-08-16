package fr.ulity.deluxegui;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.Config;
import fr.ulity.core.api.bukkit.InitializerBukkit;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.deluxegui.events.OpenInv;
import fr.ulity.deluxegui.mechanism.GuiManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class DeluxeGUI extends JavaPlugin {

    public static DeluxeGUI plugin;
    public static Config config;

    @Override
    public void onEnable() {
        plugin = this;

        InitializerBukkit init = new InitializerBukkit(this);
        init.requireVersion("2.5");
        init.reloadLang();

        if (init.ok) {
            getLogger().info(LangBukkit.prepare("deluxegui.loaded1").getOutput());
            getLogger().info(LangBukkit.prepare("deluxegui.loaded2").getOutput());

            config = new Config("config", "/addons/DeluxeGUI");
            ConfigMan.reload();

            try {
                Files.createDirectories(Paths.get(Api.corePath + "/addons/DeluxeGUI"));

                if (!Files.exists(Paths.get(Api.corePath + "/addons/DeluxeGUI/gui"))) {
                    Files.createDirectories(Paths.get(Api.corePath + "/addons/DeluxeGUI/gui"));
                    new Config("example", "addons/DeluxeGUI/gui")
                            .addDefaultsFromInputStream(DeluxeGUI.class.getResourceAsStream("/fr/ulity/deluxegui/template/example.yml"));
                    getLogger().info(LangBukkit.prepare("deluxegui.demo").getOutput());
                }

                GuiManager.loader();

                getServer().getPluginManager().registerEvents(new OpenInv(), this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info(LangBukkit.prepare("deluxegui.shutdown1").getOutput());
        getLogger().info(LangBukkit.prepare("deluxegui.shutdown2").getOutput());
    }
}
