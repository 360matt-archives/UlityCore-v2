package fr.ulity.deluxegui;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.Config;
import fr.ulity.core.api.Initializer;
import fr.ulity.core.api.Lang;
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

        Initializer init = new Initializer(this);
        init.requireVersion("2.4");
        init.reloadLang();

        if (init.ok) {
            getLogger().info(Lang.prepare("deluxegui.loaded1").getOutput());
            getLogger().info(Lang.prepare("deluxegui.loaded2").getOutput());

            config = new Config("config", "/addons/DeluxeGUI");
            ConfigMan.reload();

            try {
                Files.createDirectories(Paths.get(Api.prefix + "/addons/DeluxeGUI"));

                if (!Files.exists(Paths.get(Api.prefix + "/addons/DeluxeGUI/gui"))) {
                    Files.createDirectories(Paths.get(Api.prefix + "/addons/DeluxeGUI/gui"));
                    new Config("example", "addons/DeluxeGUI/gui")
                            .addDefaultsFromInputStream(DeluxeGUI.class.getResourceAsStream("/fr/ulity/deluxegui/template/example.yml"));
                    getLogger().info(Lang.prepare("deluxegui.demo").getOutput());
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
        getLogger().info(Lang.prepare("deluxegui.shutdown1").getOutput());
        getLogger().info(Lang.prepare("deluxegui.shutdown2").getOutput());
    }
}
