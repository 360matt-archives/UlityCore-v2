package fr.ulity.core.api;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Initializer {
    private static ArrayList<Class> lesClasses = new ArrayList<>();

    public static void addClass (@NotNull Class uneClass) {
        lesClasses.add(uneClass);
    }

    public static void removeClass ( Class uneClass) {
        lesClasses.remove(uneClass);
    }

    public static ArrayList<Class> getAllClass () {
        return lesClasses;
    }


}
