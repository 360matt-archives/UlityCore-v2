package fr.ulity.core.api.bukkit;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.Config;
import fr.ulity.core.bukkit.MainBukkit;
import fr.ulity.core.utils.ListingResources;
import fr.ulity.core.utils.TextV2;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangBukkit {
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
        HashMap<String, Config> addonsLanguages = new HashMap<>();

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

    public static <T> void reload () throws IOException, URISyntaxException {
        defaultLang = Api.config.getString("global.lang");

        for (JavaPlugin p : InitializerBukkit.lesPlugins)
            reloadOneAddon(p);
        reloadOneAddon(MainBukkit.plugin);

        try {
            MainBukkit.plugin.getLogger().info(ReloadExpressions.valueOf(defaultLang).getString());
        } catch (Exception e) {
            MainBukkit.plugin.getLogger().info(ReloadExpressions.EN.getString());
        }
    }

    public static void reloadCore () throws IOException, URISyntaxException {
        reloadOneAddon(MainBukkit.plugin);
    }

    public static String preferedPlayerLangByName (String playername, String clientLang) {
        final String path = "player." + playername + ".lang";
        return (Api.data.contains(path)) ? Api.data.getString(path) : clientLang;
    }


    public static String getLangOfPlayer (Object arg) {
        if (arg instanceof String)
            return (String) arg;
        if (arg instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player player = ((org.bukkit.entity.Player) arg);
            return preferedPlayerLangByName(player.getName(), player.getLocale().toLowerCase().split("_")[0]);
        } else return defaultLang;
    }

    private static Config getOptimalLang (Object arg, String exp) {
        String langIso = getLangOfPlayer(arg);
        Config optimalConfig = null;

        boolean noChange = false;
        for (Map.Entry<String,HashMap<String, Config>> x : langConfigs.entrySet()) {
            for (String y : x.getValue().keySet()) {
                if (y.equals(langIso)) {
                    if (x.getValue().get(y).isSet(exp))
                        return x.getValue().get(y);
                } else if (y.equals(defaultLang)) {
                    if (x.getValue().get(y).isSet(exp)) {
                        optimalConfig = x.getValue().get(y);
                        noChange = true;
                    }
                } else if (!noChange && y.equals("en")) {
                    if (x.getValue().get(y).isSet(exp)) {
                        optimalConfig = x.getValue().get(y);
                        noChange = true;
                    }
                } else if (!noChange) {
                    if (x.getValue().get(y).isSet(exp))
                        optimalConfig = x.getValue().get(y);
                }
            }
        }

        return optimalConfig;
    }

    public static class Prepared {
        private final String exp;
        private final HashMap<String, String> vars = new HashMap<>();
        private String prefix = "";
        private String suffix = "";

        public Prepared (String exp) { this.exp = exp; }

        public Prepared prefix (String prefix) { this.prefix = prefix; return this; }
        public Prepared suffix (String suffix) { this.suffix = suffix; return this; }

        public Prepared variable (String name, String replacement) {
            vars.put(name, replacement);
            return this;
        }

        public void sendPlayer (org.bukkit.entity.Player player) { player.sendMessage(getOutput(player)); }
        public void sendPlayer (org.bukkit.command.CommandSender player) { player.sendMessage(getOutput(player)); }

        public String getOutput (Object lang) {
            String output = LangBukkit.get(lang, exp);
            for (Map.Entry<String,String> x : vars.entrySet())
                output = output.replaceAll("%" + x.getKey() + "%", x.getValue());
            return prefix + output + suffix;
        }

        public String getOutput () { return getOutput(defaultLang); }
    }





    /* API use */

    public static LangBukkit.Prepared prepare (String exp) { return new LangBukkit.Prepared(exp); }

    public static String get (Object lang, String exp){
        Config langFile = getOptimalLang(lang, exp);
        return (langFile != null)
                ? new TextV2(new String[]{langFile.getString(exp)})
                .setColored()
                .setEncoded()
                .outputString()
                : "";
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

        return (langFile != null)
                ? new TextV2(langFile.getList(exp))
                .setEncoded()
                .outputArray()
                : new String[0];
    }
    public static String[] getStringArray (String exp) {
        return getStringArray(defaultLang, exp);
    }

    public static String[] getStringArrayColor (Object lang, String exp) {
        String[] array = getStringArray(lang, exp);

        return new TextV2(array)
                .setEncoded()
                .setColored()
                .outputArray();
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
