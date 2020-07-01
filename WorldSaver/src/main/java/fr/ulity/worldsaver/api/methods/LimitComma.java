package fr.ulity.worldsaver.api.methods;

public class LimitComma {
    public static String getReduced (int num) {
        return String.valueOf(num).replace("-0", "a")
                .replace("-1", "b")
                .replace("-2", "c")
                .replace("-3", "d")
                .replace("-4", "e")
                .replace("-5", "f")
                .replace("-6", "g")
                .replace("-7", "h")
                .replace("-8", "i")
                .replace("-9", "j");
    }

    public static String getDevelopped (String str) {
        return String.valueOf(str).replace("a", "-0")
                .replace("b", "-1")
                .replace("c", "-2")
                .replace("d", "-3")
                .replace("e", "-4")
                .replace("f", "-5")
                .replace("g", "-6")
                .replace("h", "-7")
                .replace("i", "-8")
                .replace("j", "-9");
    }
}
