package fr.ulity.core.utils.bukkit;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Blocks {
    public static List<Block> getNearbyBlocks(final Location loc, final int radius) {
        List<Block> blocks = new ArrayList<>();

        for (int k = 0; k <= radius; k++) {
            Location loc2_a = loc.clone().add(k, 0, 0);
            Location loc2_b = loc.clone().subtract(k, 0, 0);

            for (int k2 = 0; k2 <= radius-k; k2++) {
                blocks.add(loc2_a.clone().add(0, 0, k2).getBlock());
                blocks.add(loc2_a.clone().subtract(0, 0, k2).getBlock());

                blocks.add(loc2_b.clone().add(0, 0, k2).getBlock());
                blocks.add(loc2_b.clone().subtract(0, 0, k2).getBlock());
            }
        }
        return blocks;
    }
}
