package fr.ulity.core.addons.packutils.bukkit.methods;

import fr.ulity.core.api.Lang;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeMethods {

    public static void define (Player player, GameMode gameMode, CommandSender sender) {
        String mode;
        if (gameMode.equals(GameMode.CREATIVE))
            mode = Lang.get("packutils.gamemode.type.creative");
        else if (gameMode.equals(GameMode.SURVIVAL))
            mode = Lang.get("packutils.gamemode.type.survival");
        else if (gameMode.equals(GameMode.ADVENTURE))
            mode = Lang.get("packutils.gamemode.type.adventure");
        else
            mode = Lang.get("packutils.gamemode.type.spectator");


        player.setGameMode(gameMode);
        Lang.prepare("packutils.gamemode.gamemode_changed")
                .variable("gamemode", mode)
                .sendPlayer(player);

        if (!sender.getName().equals(player.getName())) {
            Lang.prepare("packutils.gamemode.result")
                    .variable("player", player.getName())
                    .variable("gamemode", mode)
                    .sendPlayer(sender);
        }
    }

    public static void define (Player player, GameMode gameMode) {
        define(player, gameMode, null);
    }

}
