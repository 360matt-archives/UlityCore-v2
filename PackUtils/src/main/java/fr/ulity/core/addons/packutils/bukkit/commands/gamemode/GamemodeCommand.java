package fr.ulity.core.addons.packutils.bukkit.commands.gamemode;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.GamemodeMethods;
import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class GamemodeCommand extends CommandManager.Assisted {

    public GamemodeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "gamemode");
        addPermission("ulity.packutils.gamemode");
        addAliases("gm");

        addListTabbComplete(0, null, null, Arrays.asList(
                LangBukkit.get("packutils.gamemode.type.creative"),
                LangBukkit.get("packutils.gamemode.type.survival"),
                LangBukkit.get("packutils.gamemode.type.adventure"),
                LangBukkit.get("packutils.gamemode.type.spectator")).toArray(new String[0]));

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (arg.inRange(1, 2)) {
            Player target = null;

            if (arg.is(2)) {
                if (requirePermission("ulity.packutils.gamemode.others"))
                    if (arg.requirePlayer(1))
                        target = arg.getPlayer(1);
            } else if (requirePlayer())
                target = (Player) sender;

            if (status.equals(Status.SUCCESS)) {
                if (arg.compare(args[0], "0", LangBukkit.get("packutils.gamemode.type.survival")))
                    GamemodeMethods.define(target, GameMode.SURVIVAL, sender);
                else if (arg.compare(args[0], "1", LangBukkit.get("packutils.gamemode.type.creative")))
                    GamemodeMethods.define(target, GameMode.CREATIVE, sender);
                else if (arg.compare(args[0], "2", LangBukkit.get("packutils.gamemode.type.adventure")))
                    GamemodeMethods.define(target, GameMode.ADVENTURE, sender);
                else if (arg.compare(args[0], "3", LangBukkit.get("packutils.gamemode.type.spectator")))
                    GamemodeMethods.define(target, GameMode.SPECTATOR, sender);
                else
                    setStatus(Status.SYNTAX);
            }
        } else
            setStatus(Status.SYNTAX);
    }

}