package fr.ulity.core.api;

import java.util.HashMap;

public class Cache {
    public static HashMap<Object, Object> cacheContent = new HashMap<>();

    public static boolean isSet (Object key) {
        return cacheContent.containsKey(key);
    }

    public static void set (Object key, Object value) {
        if (!isSet(key))
            cacheContent.put(key, value);
    }

    public static Object get (Object key) {
        return (!isSet(key)) ? cacheContent.get(key) : null;
    }

    public static void unset (Object key, Object value) {
        if (isSet(key))
            cacheContent.remove(key);
    }

    public static void clear () {
            cacheContent.clear();
    }

}
