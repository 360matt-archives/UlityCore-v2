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

        if (str.startsWith("a")) return str.replace("a", "-0");
        if (str.startsWith("b")) return str.replace("b", "-1");
        if (str.startsWith("c")) return str.replace("c", "-2");
        if (str.startsWith("d")) return str.replace("d", "-3");
        if (str.startsWith("e")) return str.replace("e", "-4");
        if (str.startsWith("f")) return str.replace("f", "-5");
        if (str.startsWith("g")) return str.replace("g", "-6");
        if (str.startsWith("h")) return str.replace("h", "-7");
        if (str.startsWith("i")) return str.replace("i", "-8");
        if (str.startsWith("j")) return str.replace("j", "-9");

        if (str.equals("z")) return "15";
        if (str.equals("y")) return "14";
        if (str.equals("x")) return "13";
        if (str.equals("w")) return "12";
        if (str.equals("v")) return "11";
        if (str.equals("u")) return "10";

        return str;
    }
}
