package fr.ulity.core.api;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class Initializer {
    public static ArrayList<JavaPlugin> lesPlugins = new ArrayList<>();

    public static void addPlugin (@NotNull JavaPlugin uneClass) {
        if (!lesPlugins.contains(uneClass))
            lesPlugins.add(uneClass);
        Lang.reloadOneAddon(uneClass.getClass());
    }

    public static ArrayList<Class> getClazzs () {
        ArrayList<Class> lesClasss = new ArrayList<>();

        for (JavaPlugin x : lesPlugins)
            lesClasss.add(x.getClass());

        return lesClasss;
    }

    public static void removeClass ( Class uneClass) {
        lesPlugins.remove(uneClass);
    }

}
