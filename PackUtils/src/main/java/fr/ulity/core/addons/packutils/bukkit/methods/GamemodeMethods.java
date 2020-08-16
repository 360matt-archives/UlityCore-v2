package fr.ulity.core.addons.packutils.bukkit.methods;

import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeMethods {

    public static void define (Player player, GameMode gameMode, CommandSender sender) {
        String mode;
        if (gameMode.equals(GameMode.CREATIVE))
            mode = LangBukkit.get("packutils.gamemode.type.creative");
        else if (gameMode.equals(GameMode.SURVIVAL))
            mode = LangBukkit.get("packutils.gamemode.type.survival");
        else if (gameMode.equals(GameMode.ADVENTURE))
            mode = LangBukkit.get("packutils.gamemode.type.adventure");
        else
            mode = LangBukkit.get("packutils.gamemode.type.spectator");


        player.setGameMode(gameMode);
        LangBukkit.prepare("packutils.gamemode.gamemode_changed")
                .variable("gamemode", mode)
                .sendPlayer(player);

        if (sender != null && !sender.getName().equals(player.getName())) {
            LangBukkit.prepare("packutils.gamemode.result")
                    .variable("player", player.getName())
                    .variable("gamemode", mode)
                    .sendPlayer(sender);
        }
    }

    public static void define (Player player, GameMode gameMode) {
        define(player, gameMode, null);
    }

}
