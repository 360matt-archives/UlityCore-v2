package hey.i360matt.truc;

import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.modules.loaders.BungeeLoader;

public class Bungee extends BungeeLoader {

    @Override
    public void onEnable() {
        Core.initialize(this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}