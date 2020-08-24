package fr.ulity.core.addons.packutils.bukkit;

import fr.ulity.core_v3.modules.storage.ServerConfig;

import java.util.Arrays;

public class CommandEnabler {
    public static ServerConfig disabledCommands = new ServerConfig("disabled_commands", "");

    public CommandEnabler () {
        disabledCommands.setDefault("disabled", Arrays.asList("OneDisabledCommand", "SecondDisabledCommand"));
    }

    public boolean canEnable (String name) {
        return !disabledCommands.getList("disabled").contains(name);
    }

}
