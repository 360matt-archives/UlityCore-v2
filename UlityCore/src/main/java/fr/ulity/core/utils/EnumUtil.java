package fr.ulity.core.utils;


import java.util.List;

public class EnumUtil {
    public static boolean contains(List list, String name) {
        for (Object str : list)
            if (str.toString().equalsIgnoreCase(name))
                return true;
        return false;
    }
}
