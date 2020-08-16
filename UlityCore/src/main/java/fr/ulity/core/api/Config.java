package fr.ulity.core.api;

import de.leonhard.storage.Yaml;
import fr.ulity.core.api.functions.DefaultConfig;

import java.io.File;

public class Config extends Yaml {

    public Config() {
        super("config", Api.corePath + "/");
        new DefaultConfig();
    }

    public Config(String name) {
        super(name, Api.corePath + "/");

        if (name.equals("config"))
            new DefaultConfig();
    }

    public Config(String name, String path) {
        super(name, Api.corePath + "/" + path);

        if (name.equals("config") && path.equals(""))
            new DefaultConfig();
    }

    public Config(File file) {
        super(file);
    }



    public boolean isSet(String key) {
        return get(key) != null;
    }

}