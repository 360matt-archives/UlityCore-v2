package fr.ulity.claims.events;

import com.github.yannicklamprecht.worldborder.api.BorderAPI;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import fr.ulity.claims.ClaimSystem;
import fr.ulity.claims.MainClaimBukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class FirstLevel implements Listener {
    private static final HashMap<String, Long> noSpam = new HashMap<>();
    WorldBorderApi worldBorderAPI = BorderAPI.getApi();


    private static boolean noSpam(String playerName) {
        long timestamp = new Date().getTime();

        if (!noSpam.containsKey(playerName) || (noSpam.get(playerName)) <= timestamp){
            noSpam.put(playerName, timestamp + 2000);
            return true;
        } else
            return false;
    }


    private static boolean HeCanEdit(Block block, Player player) {
        boolean cancel = true;

        if (!MainClaimBukkit.config.getList("global.worlds").contains(block.getWorld().getName()))
            return true;
        if (player == null)
            return false;
        if (player.hasPermission("claim.bypass"))
            return true;

        Boolean isClaimed = ClaimSystem.canGetInfo(block.getLocation()).success;
        if (isClaimed) {
            if (ClaimSystem.getOwner(block.getLocation()).data.equals(player.getName()))
                cancel = false;
            else if (((ArrayList) ClaimSystem.getTrusts(block.getLocation()).data).contains(player.getName()))
                cancel = false;

            if (cancel && noSpam(player.getName()))
                player.sendMessage("§cCe chunk est claim, tu ne peux pas l'éditer.");

        }
        else if (MainClaimBukkit.config.getBoolean("claims.build_without_claim"))
            cancel = false;
        else if (cancel && noSpam(player.getName()))
            player.sendMessage("§cTu dois claim ce chunk pour pouvoir l'éditer.");

        return !cancel;
    }

    @EventHandler
    private void blockPlace(BlockPlaceEvent e) {
        e.setCancelled(!HeCanEdit(e.getBlock(), e.getPlayer()));

        if (!e.isCancelled()){
            if (e.getBlockPlaced().getType().equals(Material.TNT))
                if (!MainClaimBukkit.config.getBoolean("extra.tnt")){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage("§cLes TNT ont été désactivés sur ce serveur.");
                }
        }
    }

    @EventHandler
    private void blockBreak(BlockBreakEvent e) {
        e.setCancelled(!HeCanEdit(e.getBlock(), e.getPlayer()));
    }

    @EventHandler
    private void tntPlace(EntitySpawnEvent e) {
        if (MainClaimBukkit.config.getList("global.worlds").contains(Objects.requireNonNull(e.getLocation().getWorld()).getName())) {
            if (e.getEntityType().equals(EntityType.PRIMED_TNT) && !MainClaimBukkit.config.getBoolean("extra.tnt"))
                e.setCancelled(true);
        }
    }

    @EventHandler
    private void explosion(EntityExplodeEvent e) {
        if (MainClaimBukkit.config.getList("global.worlds").contains(Objects.requireNonNull(e.getLocation().getWorld()).getName())){
            if ((e.getEntityType().equals(EntityType.PRIMED_TNT) || e.getEntityType().equals(EntityType.CREEPER))
                    && !MainClaimBukkit.config.getBoolean("extra.tnt"))
                e.setCancelled(true);
        }

    }

    @EventHandler
    private void explosion(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null)
            e.setCancelled(!HeCanEdit(e.getClickedBlock(), e.getPlayer()));

    }



    @EventHandler
    private void onLavaAndWater(PlayerBucketEmptyEvent e) {
        e.setCancelled(!HeCanEdit(e.getBlock(), e.getPlayer()));
    }

    @EventHandler
    private void onFire(BlockIgniteEvent e) {
        e.setCancelled(!HeCanEdit(e.getBlock(), e.getPlayer()));
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        MainClaimBukkit.worldBorderShowed.put(e.getPlayer().getName(), false);
    }

    @EventHandler
    private void onTeleport(PlayerTeleportEvent e) {
        if (!e.getFrom().getChunk().getBlock(0, 1, 0).getLocation().equals(Objects.requireNonNull(e.getTo()).getChunk().getBlock(0, 1, 0).getLocation())){
            MainClaimBukkit.worldBorderShowed.put(e.getPlayer().getName(), false);
            worldBorderAPI.resetWorldBorderToGlobal(e.getPlayer());
        }
    }

    @EventHandler
    private void onPiston(BlockPistonExtendEvent e) {
        if (!((ArrayList) MainClaimBukkit.config.get("global.worlds")).contains(e.getBlock().getWorld().getName()))
            return;

        Chunk testing = e.getBlock().getChunk();

        for (Block x : e.getBlocks()) {
            // si le chunk du piston n'est pas le meme que le bloc poussé
            if (!Substracted(e, x.getLocation()).getChunk().equals(testing))
                if (ClaimSystem.canGetInfo(Substracted(e, x.getLocation())).success) {
                    // si le bloc bougé loopé devait atterir sur un autre chunk claimed

                    // ... => si le proprio du chunk du piston n'est pas le même que celui du chunk où le bloc a atteri
                    if (ClaimSystem.canGetInfo(e.getBlock().getLocation()).success)
                        if (ClaimSystem.getOwner(e.getBlock().getLocation()).data.equals(ClaimSystem.getOwner(Substracted(e, x.getLocation())).data))
                            continue;

                    e.setCancelled(true);
                }
        }


    }

    private Location Substracted (BlockPistonExtendEvent e, Location loc){
        switch (e.getDirection()){
            case NORTH:
                return loc.clone().subtract(0, 0, 1);
            case SOUTH:
                return loc.clone().add(0, 0, 1);
            case WEST:
                return loc.clone().subtract(1, 0, 0);
            case EAST:
                return loc.clone().add(1, 0, 0);
            default:
                return loc;
        }
    }

}
