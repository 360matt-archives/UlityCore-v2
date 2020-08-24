package fr.ulity.core.addons.packutils.bukkit.commands.gamemode;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.GamemodeMethods;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class GamemodeCommand extends CommandBukkit {

    public GamemodeCommand() {
        super("gamemode");
        setPermission("ulity.packutils.gamemode");
        setAliases(Arrays.asList("gm"));

        addListTabbComplete(0, null, null, Arrays.asList(
                Lang.get("packutils.gamemode.type.creative"),
                Lang.get("packutils.gamemode.type.survival"),
                Lang.get("packutils.gamemode.type.adventure"),
                Lang.get("packutils.gamemode.type.spectator")).toArray(new String[0]));

        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (arg.inRange(1, 2)) {
            Player target = null;

            if (arg.is(2)) {
                if (requirePermission("ulity.packutils.gamemode.others"))
                    if (arg.requirePlayer(1))
                        target = arg.getPlayer(1);
            } else if (requirePlayer())
                target = (Player) sender;

            if (status.equals(Status.SUCCESS)) {
                if (arg.compare(args[0], "0", Lang.get("packutils.gamemode.type.survival")))
                    GamemodeMethods.define(target, GameMode.SURVIVAL, sender);
                else if (arg.compare(args[0], "1", Lang.get("packutils.gamemode.type.creative")))
                    GamemodeMethods.define(target, GameMode.CREATIVE, sender);
                else if (arg.compare(args[0], "2", Lang.get("packutils.gamemode.type.adventure")))
                    GamemodeMethods.define(target, GameMode.ADVENTURE, sender);
                else if (arg.compare(args[0], "3", Lang.get("packutils.gamemode.type.spectator")))
                    GamemodeMethods.define(target, GameMode.SPECTATOR, sender);
                else
                    setStatus(Status.SYNTAX);
            }
        } else
            setStatus(Status.SYNTAX);
    }

}