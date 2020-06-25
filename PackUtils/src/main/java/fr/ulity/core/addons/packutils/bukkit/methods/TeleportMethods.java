package fr.ulity.core.addons.packutils.bukkit.methods;

import fr.ulity.core.api.Lang;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportMethods {
    private CommandSender executor;

    public TeleportMethods (CommandSender executor) {
        this.executor = executor;
    }


    public void tpPlayer (Player origin, Player target) {
        origin.teleport(target);

        if (executor.getName().equals(target.getName()) && !executor.getName().equals(target.getName()))
            origin.sendMessage(Lang.get(origin, "packutils.teleport.teleported_by_you.player_to_you")
                    .replaceAll("%player%", origin.getName()));
        else if (!executor.getName().equals(target.getName()))
            executor.sendMessage(Lang.get(executor, "packutils.teleport.teleported_by_you.player_to_player")
                    .replaceAll("%player1%", origin.getName())
                    .replaceAll("%player2%", target.getName()));

        origin.sendMessage(Lang.get(origin, "packutils.teleport.notification.teleported_to_player")
                .replaceAll("%player%", target.getName()));

        BackMethods.setLastLocation(origin);

    }

    public void tpCoords (Player origin, Location locTarget) {
        origin.teleport(locTarget);

        String locText = locTarget.getBlockX() + " " + locTarget.getBlockY() + " " + locTarget.getBlockZ();

        if (!executor.getName().equals(origin.getName())) {
            origin.sendMessage(Lang.get(origin, "packutils.teleport.teleported_by_you.player_to_coords")
                    .replaceAll("%player%", origin.getName())
                    .replaceAll("%coords%", locText));
        }

        origin.sendMessage(Lang.get(origin, "packutils.teleport.notification.teleported_to_coords")
                .replaceAll("%coords%", locText));

        BackMethods.setLastLocation(origin);

    }

}
