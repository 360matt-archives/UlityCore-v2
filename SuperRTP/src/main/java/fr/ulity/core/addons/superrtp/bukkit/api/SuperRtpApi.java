package fr.ulity.core.addons.superrtp.bukkit.api;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.addons.superrtp.bukkit.MainBukkitRTP;
import fr.ulity.core.utils.Text;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;

public class SuperRtpApi {

    public static void playerRTP (Player player, World world, String name, Boolean message) {
        FlatFileSection section = MainBukkitRTP.config.getSection("gui." + name);

        Material blockBottom = null;

        Location locFinal = null;

        int minX = (int) Math.round(section.getDouble("randomWorld." + world.getName() + ".min.x"));
        int maxX = (int) Math.round(section.getDouble("randomWorld." + world.getName() + ".max.x") + 1);

        int minZ = (int) Math.round(section.getDouble("randomWorld." + world.getName() + ".min.z"));
        int maxZ = (int) Math.round(section.getDouble("randomWorld." + world.getName() + ".max.z"));

        while (blockBottom == null || !blockBottom.isSolid() || !world.getBlockAt(locFinal).getType().isAir()) {
            int locX = (int) Math.round((Math.random() * ((maxX - minX) + 1)) + minX);
            int locZ = (int) Math.round((Math.random() * ((maxZ - minZ) + 1)) + minZ);

            int locY = 10;
            if (world.getEnvironment().equals(World.Environment.NETHER)) {
                for (int k=10; k <=118; k++) {
                    Block block = world.getBlockAt(locX, k, locZ);
                    Block block2 = world.getBlockAt(locX, k+1, locZ);

                    if (block.getType().isSolid() && block2.getType().isAir())
                        locY = k;
                }
            } else {
                locY = world.getHighestBlockYAt(locX, locZ);
            }

            locFinal = new Location(world, locX, locY + 1, locZ);
            blockBottom = world.getBlockAt(locX, locY, locZ).getType();
        }

        player.teleport(locFinal);

        HashMap<String, Object> data = new HashMap<>();
        data.put("time", new Date().getTime() + 15000);
        data.put("x", name);
        MainBukkitRTP.invincible.put(player.getName(), data);



        if (message && section.singleLayerKeySet("extra").contains("message")) {
            // si un message est défini, il serait plus commode de l'envoyer au joueur ;)
            player.sendMessage(Text.withColours(Text.convertEncodage(section.getString("extra.message"))));
        }

    }

    public static void playerRTP (Player player, World world, String name) {
        playerRTP(player, world, name, false);
    }

}
