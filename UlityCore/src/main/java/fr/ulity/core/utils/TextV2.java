package fr.ulity.core.utils;

import fr.ulity.core.api.Api;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextV2 {
    private List<String> input;
    private List<String> outputList = new ArrayList<>();
    private StringBuilder outputStr = new StringBuilder();


    private boolean colored = false;
    private boolean encoded = false;
    private boolean newline = false;
    private int begin = 0;

    public TextV2 (String[] args) {
        input = Arrays.stream(args)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }
    public TextV2 (List<?> args) {
        input = args
                .stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    public TextV2 setColored () { colored = true; return this; }
    public TextV2 setEncoded () { encoded = true; return this; }
    public TextV2 setNewLine () { newline = true; return this; }
    public TextV2 setBeginging (int b) { begin = b; return this; }

    public List<String> generate () {
        for (String x : input) {
            if (begin == 0) {
                if (encoded)
                    x = getConverted(x);
                if (colored)
                    x = getColored(x);
                if (newline)
                    x = x + "\n";

                outputList.add(x);
                outputStr.append(x);
            } else
                begin--;
        }
        return outputList;
    }

    public List<String> outputList () { generate(); return outputList; }
    public String[] outputArray () { generate(); return outputList.toArray(new String[]{}); }
    public String outputString () { generate(); return outputStr.toString().replaceAll("\n$", ""); }







    public static String getColored (String message) {
        if (Api.type.equals(Api.PluginType.BUKKIT))
            return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
        else
            return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
    }
    public static String getConverted (String str) {
        return new String(str.getBytes(), StandardCharsets.UTF_8);
    }

}
