package fr.ulity.core.api.bukkit;

import fr.ulity.core.api.bukkit.LangBukkit;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeBukkit {
    public int seconds;
    public int milliseconds;
    public String text = "";

    private String playerLang = LangBukkit.defaultLang;

    public TimeBukkit(String text) {
        SecondFromText(text);
        this.milliseconds = this.seconds*1000;
        TextFromSeconds(this.seconds);
    }
    public TimeBukkit(String text, Object lang) {
        this.playerLang = LangBukkit.getLangOfPlayer(lang);

        SecondFromText(text);
        this.milliseconds = this.seconds*1000;
        TextFromSeconds(this.seconds);
    }

    public TimeBukkit(int seconds) {
        this.seconds = seconds;
        this.milliseconds = this.seconds*1000;
        TextFromSeconds(this.seconds);
    }
    public TimeBukkit(int seconds, Object player) {
        this.playerLang = LangBukkit.getLangOfPlayer(player);

        this.seconds = seconds;
        this.milliseconds = this.seconds*1000;
        TextFromSeconds(this.seconds);
    }

    private void SecondFromText (String text) {
        final Pattern pattern = Pattern.compile("([0-9]+)([A-z])", Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group(1));
            String multiplier = matcher.group(2);

            this.seconds += ((multiplier.equals("s")) ? number : 0)
                    + ((multiplier.equals("m")) ? number * 60 : 0)
                    + ((multiplier.equals("h")) ? number * 60 * 60 : 0)
                    + ((multiplier.equals("d") || multiplier.equals("j")) ? number * 60 * 60 * 24 : 0)
                    + ((multiplier.equals("w")) ? number * 60 * 60 * 24 * 7 : 0)
                    + ((multiplier.equals("o")) ? number * 60 * 60 * 24 * 31 : 0)
                    + ((multiplier.equals("y")) ? number * 60 * 60 * 24 * 365 : 0);
        }
    }

    private String exp(String exp) {
        return ((exp.equals("separator") ? "" : " ")) + LangBukkit.get(playerLang, "period." + exp);
    }

    private void TextFromSeconds (int seconds) {
        HashMap<String, Integer> degre = new HashMap<>();
        degre.put("year", (int) Math.floor(seconds / (60*60*24*365))); seconds -= degre.get("year")*(60*60*24*365);
        degre.put("month", (int) Math.floor(seconds / (60*60*24*30))); seconds -= degre.get("month")*(60*60*24*30);
        degre.put("day", (int) Math.floor(seconds / (60*60*24))); seconds -= degre.get("day")*(60*60*24);
        degre.put("hour",  (int) Math.floor(seconds / (3600))); seconds -= degre.get("hour")*(3600);
        degre.put("minute", (int) Math.floor(seconds / 60)); seconds -= degre.get("minute")*(60);
        degre.put("second", seconds);

        AtomicBoolean comma = new AtomicBoolean(false);
        degre.forEach((key, value) -> {
            if (value != 0) {
                this.text += ((comma.get()) ? ", " : "") + value + ((value > 1) ? exp(key + "s") : exp(key));
                comma.set(true);
            }
        });
    }
}
