package fr.ulity.core.addons.packutils.bukkit;

import fr.ulity.core.api.Config;

import java.util.Arrays;

public class CommandEnabler {
    public static Config disabledCommands = new Config("disabled_commands", "addons/PackUtils/");

    public CommandEnabler () {
        disabledCommands.setDefault("disabled", Arrays.asList("OneDisabledCommand", "SecondDisabledCommand"));
    }

    public boolean canEnable (String name) {
        return !disabledCommands.getList("disabled").contains(name);
    }

}
