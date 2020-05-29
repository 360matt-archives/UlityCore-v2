package fr.ulity.core.api.functions;

import de.leonhard.storage.Yaml;
import fr.ulity.core.api.Api;

public class DefaultConfig extends Yaml {
    public DefaultConfig() {
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