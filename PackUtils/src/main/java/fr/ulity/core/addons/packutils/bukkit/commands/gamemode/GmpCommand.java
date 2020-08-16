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

public class GmpCommand extends CommandManager.Assisted {

    public GmpCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "gmp");
        addDescription(LangBukkit.get("commands.gmX.description"));
        addUsage(LangBukkit.get("commands.gmX.usage"));
        addPermission("ulity.packutils.gamemode");
        addPermission("ulity.packutils.gmp");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (arg.inRange(0, 1)) {
            Player target = null;
            if (arg.is(0)) {
                if (requirePermission("ulity.packutils.gamemode.others"))
                    if (arg.requirePlayer(0))
                        target = arg.getPlayer(0);
            } else if (requirePlayer())
                target = (Player) sender;

            if (status.equals(Assisted.Status.SUCCESS))
                GamemodeMethods.define(target, GameMode.SPECTATOR, sender);
        } else
            setStatus(Assisted.Status.SYNTAX);
    }
}