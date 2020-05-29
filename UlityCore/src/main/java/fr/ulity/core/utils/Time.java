package fr.ulity.core.utils;

import fr.ulity.core.api.Lang;
import org.joda.time.Period;
import org.joda.time.Seconds;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Time {
    public int seconds;
    public int milliseconds;
    public String text = "";

    public Time(String text) {
        SecondFromText(text);
        this.milliseconds = this.seconds*1000;
        TextFromSeconds(this.seconds);
    }

    public Time(int seconds) {
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

    private static String exp(String exp) {
        return ((exp.equals("separator") ? "" : " ")) + Lang.get("period." + exp);
    }

    private void TextFromSeconds (int seconds) {
        Seconds sec = Seconds.seconds(seconds);
        Period period = new Period(sec);

        this.text = new PeriodFormatterBuilder()
                .appendYears()
                .appendSuffix(exp(("year")), exp(("years")))
                .appendSeparator(exp("separator"))
                .appendMonths()
                .appendSuffix(exp(("month")), exp(("months")))
                .appendSeparator(exp("separator"))
                .appendDays()
                .appendSuffix(exp(("day")), exp(("days")))
                .appendSeparator(exp("separator"))
                .appendHours()
                .appendSuffix(exp(("hour")),  exp(("hours")))
                .appendSeparator(exp("separator"))
                .appendMinutes()
                .appendSuffix(exp(("minute")),  exp(("minutes")))
                .appendSeparator(exp("separator"))
                .appendSeconds()
                .appendSuffix(exp(("second")), exp(("seconds")))
                .toFormatter()
                .print(period.normalizedStandard());
    }


}
