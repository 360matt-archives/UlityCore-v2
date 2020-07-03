package fr.ulity.worldsaver.api.methods;

public class LimitBlocs {

    public static String getReduced (String str) {
        if (str.equals("STONE")) return "1";
        if (str.equals("GRANITE")) return "1a";
        if (str.equals("DIORITE")) return "1c";
        if (str.equals("ANDESITE")) return "1e";
        if (str.equals("GRASS_BLOCK")) return "2";
        if (str.equals("DIRT")) return "3";
        if (str.equals("COBBLESTONE")) return "4";
        if (str.equals("OAK_PLANKS")) return "5";
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
        if (str.equals("BIRCH_LOG")) return "17b";
        if (str.equals("OAK_LEAVES")) return "18";
        if (str.equals("SPRUCE_LEAVES")) return "18a";
        if (str.equals("BIRCH_LEAVES")) return "18b";
        if (str.equals("LAPIS_ORE")) return "21";
        if (str.equals("SANDSTONE")) return "24";
        if (str.equals("COBWEB")) return "30";
        if (str.equals("GRASS")) return "31a";
        if (str.equals("FERN")) return "31b";
        if (str.equals("POPPY")) return "38";
        if (str.equals("OBSIDIAN")) return "49";
        if (str.equals("CHEST")) return "54";
        if (str.equals("DIAMOND_ORE")) return "56";
        if (str.equals("REDSTONE_ORE")) return "73";
        if (str.equals("ICE")) return "79";
        if (str.equals("CLAY")) return "82";
        if (str.equals("OAK_FENCE")) return "85";
        if (str.equals("STONE_BRICKS")) return "98";
        if (str.equals("CHISELED_STONE_BRICKS")) return "98c";
        if (str.equals("RAIL")) return "157";
        if (str.equals("DARK_OAK_LEAVES")) return "161a";
        if (str.equals("DARK_OAK_LOGS")) return "162a";
        if (str.equals("LARGE_FERN")) return "175c";
        if (str.equals("ROSE_BUSH")) return "175d";
        if (str.equals("MAGMA_BLOCK")) return "213";

        if (str.equals("MOSSY_COBBLESTONE")) return "u";
        if (str.equals("KELP_PLANT")) return "v";
        if (str.equals("TALL_SEAGRASS")) return "w";
        if (str.equals("KELP")) return "x";
        if (str.equals("BUBBLE_COLUMN")) return "y";
        if (str.equals("SEAGRASS")) return "z";

        return str;
    }

    public static String getDeveloped(String str) {
        if (str.equals("1")) return "STONE";
        if (str.equals("1a")) return "GRANITE";
        if (str.equals("1c")) return "DIORITE";
        if (str.equals("1e")) return "ANDESITE";
        if (str.equals("2")) return "GRASS_BLOCK";
        if (str.equals("3")) return "DIRT";
        if (str.equals("4")) return "COBBLESTONE";
        if (str.equals("5")) return "OAK_PLANKS";
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
        if (str.equals("17b")) return "BIRCH_LOG";
        if (str.equals("18")) return "OAK_LEAVES";
        if (str.equals("18a")) return "SPRUCE_LEAVES";
        if (str.equals("18b")) return "BIRCH_LEAVES";
        if (str.equals("21")) return "LAPIS_ORE";
        if (str.equals("24")) return "SANDSTONE";
        if (str.equals("30")) return "COBWEB";
        if (str.equals("31a")) return "GRASS";
        if (str.equals("31b")) return "FERN";
        if (str.equals("38")) return "POPPY";
        if (str.equals("49")) return "OBSIDIAN";
        if (str.equals("54")) return "CHEST";
        if (str.equals("56")) return "DIAMOND_ORE";
        if (str.equals("73")) return "REDSTONE_ORE";
        if (str.equals("79")) return "ICE";
        if (str.equals("82")) return "CLAY";
        if (str.equals("85")) return "OAK_FENCE";
        if (str.equals("98")) return "STONE_BRICKS";
        if (str.equals("98c")) return "CHISELED_STONE_BRICKS";
        if (str.equals("157")) return "RAIL";
        if (str.equals("161a")) return "DARK_OAK_LEAVES";
        if (str.equals("162a")) return "DARK_OAK_LOGS";
        if (str.equals("175c")) return "LARGE_FERN";
        if (str.equals("175d")) return "ROSE_BUSH";
        if (str.equals("213")) return "MAGMA_BLOCK";

        if (str.equals("u")) return "MOSSY_COBBLESTONE";
        if (str.equals("v")) return "KELP_PLANT";
        if (str.equals("w")) return "TALL_SEAGRASS";
        if (str.equals("x")) return "KELP";
        if (str.equals("y")) return "BUBBLE_COLUMN";
        if (str.equals("z")) return "SEAGRASS";

        return str;
    }
}
