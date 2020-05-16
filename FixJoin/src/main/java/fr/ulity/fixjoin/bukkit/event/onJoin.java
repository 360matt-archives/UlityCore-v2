package fr.ulity.fixjoin.bukkit.event;

import fr.ulity.core.api.Lang;
import fr.ulity.fixjoin.bukkit.MainFixBukkit;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoin implements Listener {
    public onJoin () {
        MainFixBukkit.config.setDefault("global.fix_join_message", true);
        MainFixBukkit.config.setDefault("global.fix_world_join.enabled", false);
        MainFixBukkit.config.setDefault("global.fix_world_join.world", "world");
    }


    @EventHandler
    private static void onJoined (PlayerJoinEvent e) {
        if (MainFixBukkit.config.getBoolean("global.fix_join_message"))
            e.setJoinMessage(Lang.get("fixJoin.join_message")
                    .replaceAll("%player%", e.getPlayer().getName()));

        if (MainFixBukkit.config.getBoolean("global.fix_world_join.enabled")){
            World world = Bukkit.getWorld(MainFixBukkit.config.getString("global.fix_world_join.world"));

            if (world != null)
                e.getPlayer().teleport(world.getSpawnLocation());

        }
    }
}
