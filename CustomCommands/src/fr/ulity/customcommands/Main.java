package fr.ulity.customcommands;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.Config;
import fr.ulity.core.api.bukkit.InitializerBukkit;
import fr.ulity.core.utils.TextV2;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable () {
        plugin = this;

        InitializerBukkit init = new InitializerBukkit(this);
        init.requireVersion("2.5");

        if (init.ok) {
            Config commands = new Config("commands", "addons/CustomCommands");
            CreateTemplate.make(commands);


            for (String sec : commands.singleLayerKeySet()) {
                FlatFileSection section = commands.getSection(sec);

                PassInfoCommand passed = new PassInfoCommand();
                passed.name = section.getString("name");
                passed.description = TextV2.getColored(section.getString("description"));
                passed.text = TextV2.getColored(section.getString("text"));
                passed.type = section.getString("type");
                passed.permissionMessage = TextV2.getColored(section.getString("permissionMessage"));
                passed.usage = TextV2.getColored(section.getString("usage"));
                passed.command = section.getString("command");

                new InstanceCommand(passed);
            }
        }
    }

    @Override
    public void onDisable () {

    }

}
