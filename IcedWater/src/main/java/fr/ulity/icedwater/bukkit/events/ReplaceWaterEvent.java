package fr.ulity.icedwater.bukkit.events;

import fr.ulity.icedwater.bukkit.IcedWater;
import fr.ulity.core.api.Api;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;


public class ReplaceWaterEvent implements Listener {
    static int taskID;

    @EventHandler
    private static void onMove (PlayerMoveEvent e) {
        Player player = e.getPlayer();

        String ice_path = "player." + player.getName() + ".icedwater";

        if (IcedWater.config.getOrSetDefault("for_everyone", false) &&
                !Api.data.contains(ice_path) ||
                (Api.data.contains(ice_path)) &&
                Api.data.getBoolean(ice_path))
        {

            Block blockAvant = e.getFrom().clone().subtract(0, 1, 0).getBlock();
            Block blockApres = e.getTo().clone().subtract(0, 1, 0).getBlock();

            if (!blockAvant.equals(blockApres)) {
                player.setFallDistance(0);

                if (blockApres.getType().equals(Material.WATER)) {
                    final Block restoreBlock = blockApres;
                    final BlockData data = restoreBlock.getBlockData();

                    blockApres.setType(Material.ICE);

                    taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(IcedWater.plugin, new Runnable() {
                        final int id = taskID;

                        @Override
                        public void run() {
                            if (!player.getLocation().subtract(0, 1, 0).getBlock().equals(blockApres)) {
                                if (blockApres.equals(Material.ICE))
                                    blockApres.setType(Material.WATER);

                                blockApres.setBlockData(data);

                                Bukkit.getServer().getScheduler().cancelTask(id);
                            }
                        }
                    }, 20L, 30L);
                }
            }
        }



    }

}
