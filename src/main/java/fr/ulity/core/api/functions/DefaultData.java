package fr.ulity.core.api.functions;

import de.leonhard.storage.Yaml;
import fr.ulity.core.api.Api;

public class DefaultData extends Yaml {
    public DefaultData() {
        super("config", Api.prefix);

        if (Api.type.equals("bungeecord"))
            isBungee();
        else if (Api.type.equals("bukkit"))
            isBukkit();
    }

    public void isBukkit() {
    }

    public void isBungee() {
    }

}