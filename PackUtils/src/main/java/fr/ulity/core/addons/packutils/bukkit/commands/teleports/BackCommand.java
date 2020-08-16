package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.BackMethods;
import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BackCommand extends CommandManager.Assisted {

    public BackCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "back");
        addPermission("ulity.packutils.back");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (requirePlayer()) {
            if (arg.inRange(0, 0)) {
                Player player = (Player) sender;

                Location lastLoc = BackMethods.getLastLocation(player);
                if (lastLoc != null) {
                    if (lastLoc.getWorld() == null)
                        LangBukkit.prepare("commands.back.expressions.unknown_world").sendPlayer(player);
                    else {
                        player.teleport(lastLoc);
                        LangBukkit.prepare("commands.back.expressions.teleported").sendPlayer(player);
                        BackMethods.setLastLocation(player);
                    }
                } else
                    LangBukkit.prepare("commands.back.expressions.nothing").sendPlayer(player);
            }
        }
    }
}