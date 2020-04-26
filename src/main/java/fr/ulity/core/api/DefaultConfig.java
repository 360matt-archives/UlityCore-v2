package fr.ulity.core.api;

import de.leonhard.storage.Yaml;

public class DefaultConfig extends Yaml {
    DefaultConfig() {
        super("config", Api.prefix);

        if (Api.type.equals("bungeecord"))
            isBungee();
        else if (Api.type.equals("bukkit"))
            isBukkit();
    }

    public void isBukkit() {
        setDefault("global.lang", "fr");

    }

    public void isBungee() {
        setDefault("global.lang", "fr");
    }

}