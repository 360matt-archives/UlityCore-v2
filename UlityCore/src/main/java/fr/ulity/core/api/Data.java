package fr.ulity.core.api;

import fr.ulity.core.api.functions.Storage;

public class Data extends Storage {
    public Data() { super("data"); }
    public Data(String file) { super(file); }
    public Data(String file, String path) { super(file, path); }
}