package fr.ulity.core.api;

import de.leonhard.storage.Json;

import java.io.File;

public class Data extends Json {
    public Data() { super("data", ""); }
    public Data(String file) { super(new File(file)); }
    public Data(String file, String path) { super(file, path); }
    public Data(File file) { super(file); }
}