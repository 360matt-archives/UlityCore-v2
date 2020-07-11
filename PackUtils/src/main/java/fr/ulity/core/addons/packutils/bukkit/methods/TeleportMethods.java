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
            Lang.prepare("packutils.teleport.teleported_by_you.player_to_you")
                    .variable("player", origin.getName())
                    .sendPlayer(origin);
        else if (!executor.getName().equals(target.getName()))
            Lang.prepare("packutils.teleport.teleported_by_you.player_to_player")
                    .variable("player1", origin.getName())
                    .variable("player2", target.getName())
                    .sendPlayer(executor);

        Lang.prepare("packutils.teleport.notification.teleported_to_player")
                .variable("player", target.getName())
                .sendPlayer(origin);

        BackMethods.setLastLocation(origin);
    }

    public void tpCoords (Player origin, Location locTarget) {
        origin.teleport(locTarget);

        String locText = locTarget.getBlockX() + " " + locTarget.getBlockY() + " " + locTarget.getBlockZ();

        if (!executor.getName().equals(origin.getName())) {
            Lang.prepare("packutils.teleport.teleported_by_you.player_to_coords")
                    .variable("player", origin.getName())
                    .variable("coords", locText)
                    .sendPlayer(executor);
        }

        Lang.prepare("packutils.teleport.notification.teleported_to_coords")
                .variable("coords", locText)
                .sendPlayer(origin);

        BackMethods.setLastLocation(origin);

    }
}
