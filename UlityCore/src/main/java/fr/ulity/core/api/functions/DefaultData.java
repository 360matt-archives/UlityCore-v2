package fr.ulity.core.api.functions;

import de.leonhard.storage.Yaml;
import fr.ulity.core.api.Api;

public class DefaultData extends Yaml {
    public DefaultData() {
        super("config", Api.corePath);

        if (Api.type.equals(Api.PluginType.BUNGEE))
            isBungee();
        else if (Api.type.equals(Api.PluginType.BUKKIT))
            isBukkit();
    }

    public void isBukkit() {
    }

    public void isBungee() {
    }

}