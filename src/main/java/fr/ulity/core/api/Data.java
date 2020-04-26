package fr.ulity.core.api;

import de.leonhard.storage.Json;

public class Data extends Json {
    public Data() {
        super("data", Api.prefix);
    }
}
