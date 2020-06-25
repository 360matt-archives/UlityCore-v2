package fr.ulity.core.api.functions;

import de.leonhard.storage.Json;
import fr.ulity.core.api.Api;

public class Storage extends Json {
    public Storage (String name) { super(name, Api.prefix + "/"); }
    public Storage(String name, String path) { super(name, Api.prefix + "/" + path); }
    public boolean isSet(String key) { return contains(key); }


    public class Temp extends Storage {
        public Temp () { super("temp", "temps"); }
    }


}
