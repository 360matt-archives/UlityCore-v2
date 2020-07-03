package fr.ulity.worldsaver.api.methods;

public class LimitID {
    public static String getReduced (int num) {
        String str = String.valueOf(num);

        if (str.startsWith("-0")) return str.replace("-0", "a");
        if (str.startsWith("-1")) return str.replace("-1", "b");
        if (str.startsWith("-2")) return str.replace("-2", "c");
        if (str.startsWith("-3")) return str.replace("-3", "d");
        if (str.startsWith("-4")) return str.replace("-4", "e");
        if (str.startsWith("-5")) return str.replace("-5", "f");
        if (str.startsWith("-6")) return str.replace("-6", "g");
        if (str.startsWith("-7")) return str.replace("-7", "h");
        if (str.startsWith("-8")) return str.replace("-8", "i");
        if (str.startsWith("-9")) return str.replace("-9", "j");

        if (str.equals("15")) return "z";
        if (str.equals("14")) return "y";
        if (str.equals("13")) return "x";
        if (str.equals("12")) return "w";
        if (str.equals("11")) return "v";
        if (str.equals("10")) return "u";

        return str;
    }

    public static String getDevelopped (String str) {
        return str
            .replaceAll("a", "-0")
            .replaceAll("b", "-1")
            .replaceAll("c", "-2")
            .replaceAll("d", "-3")
            .replaceAll("e", "-4")
            .replaceAll("f", "-5")
            .replaceAll("g", "-6")
            .replaceAll("h", "-7")
            .replaceAll("i", "-8")
            .replaceAll("j", "-9")

            .replaceAll("z", "15")
            .replaceAll("y", "14")
            .replaceAll("x", "13")
            .replaceAll("w", "12")
            .replaceAll("v", "11")
            .replaceAll("u", "10");

    }
}
