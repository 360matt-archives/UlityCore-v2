package fr.ulity.core.api;

import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.utils.ListingResources;
import fr.ulity.core.utils.Text;


import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lang {
    public static String defaultLang;
    private static Map<String, Config> langC = new HashMap<String, Config>();

    public static void reload () throws IOException, URISyntaxException {
        defaultLang = Api.config.getString("global.lang");

        reloadCore();
        reloadAddons();

        MainBukkit.plugin.getLogger().info(Objects.equals(defaultLang, "fr")
        ? "§aLes fichiers de langue ont été rechargés"
        : "§aLanguage files have been reloaded"
        );
    }

    public static void reloadCore () throws IOException, URISyntaxException {
        String path = (MainBukkit.class.getPackage().getName() + "/languages/").replaceAll("\\.", "/");

        for (String x : ListingResources.getResourceListing(MainBukkit.class, path)) {
            Matcher m = Pattern.compile("(.*).yml").matcher(x);
            if (m.find())
                Lang.addFromReference(Objects.requireNonNull(MainBukkit.class.getClassLoader().getResource(path + x)), m.group(1));
        }
    }

    public static void reloadAddons () throws IOException, URISyntaxException {
        for (Class c : Initializer.lesClass)
            reloadOneAddon(c);
    }

    public static void reloadOneAddon (Class clazz) throws IOException, URISyntaxException {
        String path = (clazz.getPackage().getName() + "/languages/").replaceAll("\\.", "/");

        for (String x : ListingResources.getResourceListing(clazz, path)) {
            Matcher m = Pattern.compile("(.*).yml").matcher(x);
            if (m.find())
                Lang.addFromReference(Objects.requireNonNull(clazz.getClassLoader().getResource(path + x)), m.group(1));
        }
    }


    public static void addFromReference (URL url, String lang) throws IOException {
        InputStream input = new URL(url.toString()).openStream();
        Config langHandle = getLangConf(lang);
        langHandle.addDefaultsFromInputStream(input);
    }

    private static Config getLangConf (String lang) {
        if (langC.containsValue(lang))
            return langC.get(lang);
        else {
            Config createdConfig = new Config(lang, "languages");
            langC.put(lang, createdConfig);
            return createdConfig;
        }
    }

    private static String getLangOfPlayer (Object arg) {
        try {
            Class.forName("org.bukkit.entity.Player");

            if (arg instanceof org.bukkit.entity.Player)
                if (Api.data.isSet("player." + ((org.bukkit.entity.Player) arg).getName() + ".lang"))
                    return Api.data.getString("player." + ((org.bukkit.entity.Player) arg).getName() + ".lang");
                else
                    return ((org.bukkit.entity.Player) arg).getLocale().toLowerCase().split("_")[0];
            else
                return Lang.defaultLang;
        } catch (ClassNotFoundException ignored) {
            if (arg instanceof net.md_5.bungee.api.connection.ProxiedPlayer)
                if (Api.data.isSet("player." + ((net.md_5.bungee.api.connection.ProxiedPlayer) arg).getName() + ".lang"))
                    return Api.data.getString("player." + ((org.bukkit.entity.Player) arg).getName() + ".lang");
                else
                    return ((net.md_5.bungee.api.connection.ProxiedPlayer) arg).getLocale().getLanguage();
            else
                return Lang.defaultLang;
        }
    }

    private static Config getBrut (Object arg) {
        if (arg instanceof String)
            return getLangConf((String) arg);
        return getLangConf(getLangOfPlayer(arg));
    }







    /* API use */

    public static String get (Object lang, String exp){
        return Text.withColours(Text.convertEncodage(getBrut(lang).getString(exp)));
    }
    public static String get (String exp){
        return get(defaultLang, exp);
    }

    public static int getInt (Object lang, String exp) {
        return getBrut(lang).getInt(exp);
    }
    public static int getInt (String exp) {
        return getInt(defaultLang, exp);
    }

    public static String[] getStringArray (Object lang, String exp) {
        List<?> list = getBrut(lang).getList(exp);
        return Text.convertEncodage(list.toArray(new String[0]));
    }
    public static String[] getStringArray (String exp) {
        return getStringArray(defaultLang, exp);
    }

    public static String[] getStringArrayColor (Object lang, String exp) {
        Object[] list = getBrut(lang).getList(exp).toArray();
        int hash = Arrays.hashCode(list);

        if (Cache.isSet(hash))
            return (String[]) Cache.get(hash);
        else{
            String[] value = new String[list.length];
            int counter = 0;
            for (Object x : list) {
                value[counter] = Text.withColours(Text.convertEncodage(x.toString()));
                counter++;
            }

            Cache.set(hash, value);
            return value;
        }
    }
    public static String[] getStringArrayColor (String exp) {
        return getStringArrayColor(defaultLang, exp);
    }

    public static boolean getBoolean (Object lang, String exp) {
        return getBrut(lang).getBoolean(exp);
    }
    public static boolean getBoolean (String exp) {
        return getBoolean(defaultLang, exp);
    }

}
