package fr.ulity.core.api;

import de.leonhard.storage.Yaml;
import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.utils.ListingResources;
import fr.ulity.core.utils.Text;
import org.apache.commons.io.FileUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lang {
    public static String defaultLang;
    private static Map<String, Config> langC = new HashMap<String, Config>();

    private static ResourcesScanner scaner = new ResourcesScanner();

    public static void reload () {
        defaultLang = Api.config.getString("global.lang");

        reloadCore();
        reloadAddons();

        MainBukkit.plugin.getLogger().info(Objects.equals(defaultLang, "fr")
        ? "§aLes fichiers de langue ont été rechargés"
        : "§aLanguage files have been reloaded"
        );
    }

    public static void reloadCore () {
        Reflections reflections = new Reflections("fr.ulity.core." + Api.type + ".languages.", scaner);
        Set<String> fileNames = reflections.getResources(Pattern.compile(".*\\.yml"));

        for (String x : fileNames){
            x = "/" + x;

            Matcher m = Pattern.compile("/" + Api.type + "/languages/(.*).yml").matcher(x);
            if (m.find()){
                try {
                    addFromReference(Lang.class.getResource(x), m.group(1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void reloadAddons () {
        for (Class c : Initializer.getClazzs())
            reloadOneAddon(c);
    }

    public static void reloadOneAddon (Class clazz) {
        try {
            String path = (clazz.getPackage().getName() + "/languages/").replaceAll("\\.", "/");

            for (String x : ListingResources.getResourceListing(clazz, path)) {
                Matcher m = Pattern.compile("(.*).yml").matcher(x);
                if (m.find())
                    Lang.addFromReference(Objects.requireNonNull(clazz.getClassLoader().getResource(path + x)), m.group(1));
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void addFromReference (URL url, String lang) throws IOException {
        File fileTemp = File.createTempFile("ulitycore_lang_" + lang, ".yml");
        FileUtils.copyURLToFile(url, fileTemp);

        Yaml tempoC = new Yaml(fileTemp.getName(), fileTemp.getParent());
        Config langHandle = getLangConf(lang);
        for (String i : tempoC.keySet())
            langHandle.setDefault(i, tempoC.get(i));
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






    private static Config getBrut (Object arg) {
        if (arg instanceof String)
            return getLangConf((String) arg);
        else if (arg instanceof org.bukkit.entity.Player)
            return getLangConf(((org.bukkit.entity.Player) arg).getLocale().toLowerCase().split("_")[0]);
        else if (arg instanceof net.md_5.bungee.api.connection.ProxiedPlayer)
            return getLangConf(((net.md_5.bungee.api.connection.ProxiedPlayer) arg).getLocale().getLanguage());
        else return getLangConf(defaultLang);
    }





    /* API use */

    public static String get (Object lang, String exp){
        return Text.withColours(getBrut(lang).getString(exp));
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
        return list.toArray(new String[list.size()]);
    }
    public static String[] getStringArray (String exp) {
        return getStringArray(defaultLang, exp);
    }

    public static boolean getBoolean (Object lang, String exp) {
        return getBrut(lang).getBoolean(exp);
    }
    public static boolean getBoolean (String exp) {
        return getBoolean(defaultLang, exp);
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
                value[counter] = Text.withColours(x.toString());
                counter++;
            }

            Cache.set(hash, value);
            return value;
        }
    }
    public static String[] getStringArrayColor (String exp) {
        return getStringArrayColor(defaultLang, exp);
    }


}
