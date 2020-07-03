package fr.ulity.worldsaver.api.methods;

public class LimitBlocs {

    public static String getReduced (String str) {
        if (str.equals("STONE")) return "1";
        if (str.equals("GRANITE")) return "1_1";
        if (str.equals("DIORITE")) return "1_3";
        if (str.equals("ANDESITE")) return "1_5";
        if (str.equals("GRASS_BLOCK")) return "2";
        if (str.equals("DIRT")) return "3";
        if (str.equals("COBBLESTONE")) return "4";
        if (str.equals("BEDROCK")) return "7";
        if (str.equals("FLOWING_WATER")) return "8";
        if (str.equals("WATER")) return "9";
        if (str.equals("FLOWING_LAVA")) return "10";
        if (str.equals("LAVA")) return "11";
        if (str.equals("SAND")) return "12";
        if (str.equals("GRAVEL")) return "13";
        if (str.equals("GOLD_ORE")) return "14";
        if (str.equals("IRON_ORE")) return "15";
        if (str.equals("COAL_ORE")) return "16";
        if (str.equals("OAK_LOG")) return "17";
        if (str.equals("BIRCH_LOG")) return "17_2";
        if (str.equals("OAK_LEAVES")) return "18";
        if (str.equals("SPRUCE_LEAVES")) return "18_1";
        if (str.equals("BIRCH_LEAVES")) return "18_2";
        if (str.equals("LAPIS_ORE")) return "21";
        if (str.equals("SANDSTONE")) return "24";
        if (str.equals("GRASS")) return "31_1";
        if (str.equals("FERN")) return "31_2";
        if (str.equals("POPPY")) return "38";
        if (str.equals("OBSIDIAN")) return "49";
        if (str.equals("DIAMOND_ORE")) return "56";
        if (str.equals("REDSTONE_ORE")) return "73";
        if (str.equals("ICE")) return "79";
        if (str.equals("DARK_OAK_LEAVES")) return "161_1";
        if (str.equals("DARK_OAK_LOGS")) return "162_1";
        if (str.equals("LARGE_FERN")) return "175_3";
        if (str.equals("ROSE_BUSH")) return "175_4";

        return str;

    }

    public static String getDeveloped(String str) {
        if (str.equals("1")) return "STONE";
        if (str.equals("1_1")) return "GRANITE";
        if (str.equals("1_3")) return "DIORITE";
        if (str.equals("1_5")) return "ANDESITE";
        if (str.equals("2")) return "GRASS_BLOCK";
        if (str.equals("3")) return "DIRT";
        if (str.equals("4")) return "COBBLESTONE";
        if (str.equals("7")) return "BEDROCK";
        if (str.equals("8")) return "FLOWING_WATER";
        if (str.equals("9")) return "WATER";
        if (str.equals("10")) return "FLOWING_LAVA";
        if (str.equals("11")) return "LAVA";
        if (str.equals("12")) return "SAND";
        if (str.equals("13")) return "GRAVEL";
        if (str.equals("14")) return "GOLD_ORE";
        if (str.equals("15")) return "IRON_ORE";
        if (str.equals("16")) return "COAL_ORE";
        if (str.equals("17")) return "OAK_LOG";
        if (str.equals("17_2")) return "BIRCH_LOG";
        if (str.equals("18")) return "OAK_LEAVES";
        if (str.equals("18_1")) return "SPRUCE_LEAVES";
        if (str.equals("18_2")) return "BIRCH_LEAVES";
        if (str.equals("21")) return "LAPIS_ORE";
        if (str.equals("24")) return "SANDSTONE";
        if (str.equals("31_1")) return "GRASS";
        if (str.equals("31_2")) return "FERN";
        if (str.equals("38")) return "POPPY";
        if (str.equals("49")) return "OBSIDIAN";
        if (str.equals("56")) return "DIAMOND_ORE";
        if (str.equals("73")) return "REDSTONE_ORE";
        if (str.equals("79")) return "ICE";
        if (str.equals("161_1")) return "DARK_OAK_LEAVES";
        if (str.equals("162_1")) return "DARK_OAK_LOGS";
        if (str.equals("175_3")) return "LARGE_FERN";
        if (str.equals("175_4")) return "ROSE_BUSH";

        return str;
    }
}
