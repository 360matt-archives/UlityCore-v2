package fr.ulity.core.api;

import de.leonhard.storage.Json;

public class Temp extends Json {
    public Temp() {
        super("data", Api.prefix + "/" + "temps");
    }
}
