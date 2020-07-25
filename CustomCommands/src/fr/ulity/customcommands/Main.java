package fr.ulity.customcommands;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.Config;
import fr.ulity.core.api.Initializer;
import fr.ulity.core.utils.Text;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable () {
        plugin = this;

        Initializer init = new Initializer(this);
        init.requireVersion("2.3.1");

        if (init.ok) {
            Config commands = new Config("commands", "addons/CustomCommands");
            CreateTemplate.make(commands);


            for (String sec : commands.singleLayerKeySet()) {
                FlatFileSection section = commands.getSection(sec);

                PassInfoCommand passed = new PassInfoCommand();
                passed.name = section.getString("name");
                passed.description = Text.withColours(section.getString("description"));
                passed.text = Text.withColours(section.getString("text"));
                passed.type = section.getString("type");
                passed.permissionMessage = Text.withColours(section.getString("permissionMessage"));
                passed.usage = Text.withColours(section.getString("usage"));
                passed.command = section.getString("command");

                new InstanceCommand(passed);
            }
        }
    }

    @Override
    public void onDisable () {

    }

}
