package fr.ulity.core.api;

import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import fr.ulity.core.api.functions.DefaultData;

public class Data extends Json {
    public Data() {
        super("data", Api.prefix);
    }


    public Data(String name) {
        super(name, Api.prefix + "/");

        if (name.equals("data"))
            new DefaultData();

    }

    public Data(String name, String path) {
        super(name, Api.prefix + "/" + path);

        if (name.equals("data") && path.equals(""))
            new DefaultData();
    }



    public boolean isSet(String key) {
        return get(key) != null;
    }

    public void delete(String key) {
        set(key, null);
    }

}