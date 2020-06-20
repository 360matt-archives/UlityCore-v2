package fr.ulity.core.api;

import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.utils.ListingResources;
import fr.ulity.core.utils.Text;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lang {
    public static HashMap<String, HashMap<String, Config>> langConfigs = new HashMap<>();
    public static String defaultLang = Api.config.getString("global.lang");


    private enum ReloadExpressions {
        FR("§aLes fichiers de langue ont été rechargés"),
        EN("§aLanguage files have been reloaded");

        private String exp;
        ReloadExpressions(String exp) {
            this.exp = exp;
        }

        public String getString() {
            return exp;
        }
    }

    public static void reloadOneAddon (JavaPlugin plugin) throws IOException, URISyntaxException {
        HashMap<String, Config> addonsLanguages = new HashMap<String, Config>();

        String path = (plugin.getClass().getPackage().getName() + "/languages/").replaceAll("\\.", "/");
        for (String x : ListingResources.getResourceListing(plugin.getClass(), path)) {
            Matcher m = Pattern.compile("(.*).yml").matcher(x);
            if (m.find()) {
                Config configLooped = new Config(m.group(1), "addons/" + plugin.getName() + "/languages");

                URL urlLooped = plugin.getClass().getClassLoader().getResource(path + x);

                if (urlLooped != null) {
                    InputStream input = new URL(urlLooped.toString()).openStream();
                    configLooped.addDefaultsFromInputStream(input);

                    addonsLanguages.put(m.group(1), configLooped);
                }
            }
        }
        langConfigs.put(plugin.getName(), addonsLanguages);

    }

    public static void reload () throws IOException, URISyntaxException {
        defaultLang = Api.config.getString("global.lang");

        for (JavaPlugin p : Initializer.lesPlugins)
            reloadOneAddon(p);
        reloadOneAddon(MainBukkit.plugin);

        try {
            MainBukkit.plugin.getLogger().info(ReloadExpressions.valueOf(defaultLang).getString());
        } catch (Exception e) {
            MainBukkit.plugin.getLogger().info(ReloadExpressions.EN.getString());
        }
    }

    public static void reloadCore (JavaPlugin plugin) throws IOException, URISyntaxException {
        reloadOneAddon(MainBukkit.plugin);
    }

    public static String preferedPlayerLangByName (String playername, String clientLang) {
        final String path = "player." + playername + ".lang";
        return (Api.data.isSet(path)) ? Api.data.getString(path) : clientLang;
    }


    public static String getLangOfPlayer (Object arg) {
        if (arg instanceof String)
            return (String) arg;
        try {
            Class.forName("org.bukkit.entity.Player");

            if (arg instanceof org.bukkit.entity.Player) {
                org.bukkit.entity.Player player = ((org.bukkit.entity.Player) arg);
                return preferedPlayerLangByName(player.getName(), player.getLocale().toLowerCase().split("_")[0]);
            } else return defaultLang;
        } catch (ClassNotFoundException ignored) {
            try {
                Class.forName("net.md_5.bungee.api.connection.ProxiedPlayer");
                if (arg instanceof net.md_5.bungee.api.connection.ProxiedPlayer) {
                    net.md_5.bungee.api.connection.ProxiedPlayer player = (net.md_5.bungee.api.connection.ProxiedPlayer) arg;
                    return preferedPlayerLangByName(player.getName(), player.getLocale().getLanguage());
                } else return defaultLang;
            } catch (Exception e) {
                System.out.println(e);
                return "";
            }
        }
    }

    private static Config getOptimalLang (Object arg, String exp) {
        String langIso = getLangOfPlayer(arg);
        Config optimalConfig = null;

        boolean noChange = false;
        for (String x : langConfigs.keySet()) {
            for (String y : langConfigs.get(x).keySet()) {
                if (y.equals(langIso)) {
                    if (langConfigs.get(x).get(y).isSet(exp))
                        return langConfigs.get(x).get(y);
                } else if (y.equals(defaultLang)) {
                    if (langConfigs.get(x).get(y).isSet(exp)) {
                        optimalConfig = langConfigs.get(x).get(y);
                        noChange = true;
                    }
                } else if (!noChange && y.equals("en")) {
                    if (langConfigs.get(x).get(y).isSet(exp)) {
                        optimalConfig = langConfigs.get(x).get(y);
                        noChange = true;
                    }
                } else if (!noChange) {
                    if (langConfigs.get(x).get(y).isSet(exp)) {
                        optimalConfig = langConfigs.get(x).get(y);
                    }
                }
            }
        }

        return optimalConfig;
    }





    /* API use */

    public static String get (Object lang, String exp){
        Config langFile = getOptimalLang(lang, exp);
        return (langFile != null) ? Text.withColours(Text.convertEncodage(langFile.getString(exp))) : "";
    }
    public static String get (String exp){
        return get(defaultLang, exp);
    }

    public static int getInt (Object lang, String exp) {
        Config langFile = getOptimalLang(lang, exp);
        return (langFile != null) ? langFile.getInt(exp) : 0;
    }
    public static int getInt (String exp) {
        return getInt(defaultLang, exp);
    }

    public static String[] getStringArray (Object lang, String exp) {
        Config langFile = getOptimalLang(lang, exp);
        return (langFile != null) ? Text.convertEncodage(langFile.getList(exp).toArray(new String[0])) : new String[0];
    }
    public static String[] getStringArray (String exp) {
        return getStringArray(defaultLang, exp);
    }

    public static String[] getStringArrayColor (Object lang, String exp) {
        String[] array = getStringArray(lang, exp);

        if (array.length != 0)
            array = Arrays.stream(array).map(Text::withColours).toArray(String[]::new);

        return array;
    }
    public static String[] getStringArrayColor (String exp) {
        return getStringArrayColor(defaultLang, exp);
    }

    public static boolean getBoolean (Object lang, String exp) {
        Config langFile = getOptimalLang(lang, exp);
        return (langFile != null) && langFile.getBoolean(exp);
    }
    public static boolean getBoolean (String exp) {
        return getBoolean(defaultLang, exp);
    }

}
