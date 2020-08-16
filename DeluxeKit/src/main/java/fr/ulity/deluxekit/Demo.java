package fr.ulity.deluxekit;

import fr.ulity.core.api.Api;
import org.bukkit.Material;

import java.io.File;

public class Demo {
    public static void use () {
        File dir = new File(Api.corePath + "/addons/DeluxeKit/kits");
        if(!dir.exists()){
            Kit demo = new Kit("demo");
            demo.description = "Small test";
            demo.title = "&a&lExample";

            for (int i = 0; i < 15; i++) {
                Kit.Content content = new Kit.Content();
                content.id = i;
                content.material = Material.DIRT;
                demo.contents.add(content);
            }

            demo.save();
        }
    }
}
