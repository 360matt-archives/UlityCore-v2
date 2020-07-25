package fr.ulity.deluxegui.opener;



import org.bukkit.Material;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGlass {
    public static Material getRandom () {
        Material randomMaterial = null;

        int randomNum = ThreadLocalRandom.current().nextInt(0, 7 + 1);
        if (randomNum == 0) randomMaterial = Material.RED_STAINED_GLASS_PANE;
        else if (randomNum == 1) randomMaterial = Material.YELLOW_STAINED_GLASS_PANE;
        else if (randomNum == 2) randomMaterial = Material.LIME_STAINED_GLASS_PANE;
        else if (randomNum == 3) randomMaterial = Material.CYAN_STAINED_GLASS_PANE;
        else if (randomNum == 4) randomMaterial = Material.PURPLE_STAINED_GLASS_PANE;
        else if (randomNum == 5) randomMaterial = Material.GRAY_STAINED_GLASS_PANE;
        else if (randomNum == 6) randomMaterial = Material.ORANGE_STAINED_GLASS_PANE;
        else if (randomNum == 7) randomMaterial = Material.GREEN_STAINED_GLASS_PANE;

        return randomMaterial;
    }
}
