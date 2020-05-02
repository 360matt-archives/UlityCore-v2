package fr.ulity.core.api;

import de.leonhard.storage.Yaml;
import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.utils.ListingResources;
import org.apache.commons.io.FileUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lang {
    public static String defaultLang;
    private static Map<String, Config> langC = new HashMap<String, Config>();

    private static ResourcesScanner scaner = new ResourcesScanner();

    public static void reload () {
        defaultLang = Api.config.getString("global.lang");

        reloadCore();

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
        for (Class c : Initializer.lesClasses)
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
            langHandle.setDefault(i, tempoC.getString(i));
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

    private static String withColours (String message) {
        if (Api.type.equals("bukkit"))
            return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
        else
            return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
    }





    /* API use */

    public static String get (String exp){
        return withColours(getLangConf(defaultLang).getString(exp));
    }

    public static String get (String lang, String exp){
        return withColours(getLangConf(lang).getString(exp));
    }

    public static String get (org.bukkit.command.CommandSender sender, String exp){
        // bukkit
        if (sender instanceof org.bukkit.entity.Player)
            return withColours(getLangConf(((org.bukkit.entity.Player) sender).getLocale().toLowerCase()).getString(exp));
        else
            return withColours(getLangConf(defaultLang).getString(exp));
    }

    public static String get (net.md_5.bungee.api.CommandSender sender, String exp){
        // bungee
        if (sender instanceof net.md_5.bungee.api.connection.ProxiedPlayer)
            return withColours(getLangConf(((net.md_5.bungee.api.connection.ProxiedPlayer) sender).getLocale().getLanguage()).getString(exp));
        else
            return withColours(getLangConf(defaultLang).getString(exp));
    }

}
