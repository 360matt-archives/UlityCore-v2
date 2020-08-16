package fr.ulity.core.addons.packutils.bukkit.commands.gamemode;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.GamemodeMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GmsCommand extends CommandManager.Assisted {

    public GmsCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "gms");
        addDescription(LangBukkit.get("commands.gmX.description"));
        addUsage(LangBukkit.get("commands.gmX.usage"));
        addPermission("ulity.packutils.gamemode");
        addPermission("ulity.packutils.gms");

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

            if (status.equals(Status.SUCCESS))
                GamemodeMethods.define(target, GameMode.SURVIVAL, sender);
        } else
            setStatus(Status.SYNTAX);
    }
}