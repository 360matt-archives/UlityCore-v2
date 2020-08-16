package fr.ulity.core.api.functions;

import de.leonhard.storage.Yaml;
import fr.ulity.core.api.Api;

public class DefaultConfig extends Yaml {
    public DefaultConfig() {
        super("config", Api.corePath);

        if (Api.type.equals(Api.PluginType.BUNGEE))
            isBungee();
        else if (Api.type.equals(Api.PluginType.BUKKIT))
            isBukkit();
    }

    public void isBukkit() {
        setDefault("global.lang", "fr");

    }

    public void isBungee() {
        setDefault("global.lang", "fr");
    }

}