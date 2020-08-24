package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.BackMethods;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BackCommand extends CommandBukkit {

    public BackCommand() {
        super("back");
        setPermission("ulity.packutils.back");

        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (requirePlayer()) {
            if (arg.inRange(0, 0)) {
                Player player = (Player) sender;

                Location lastLoc = BackMethods.getLastLocation(player);
                if (lastLoc != null) {
                    if (lastLoc.getWorld() == null)
                        Lang.prepare("commands.back.expressions.unknown_world").sendPlayer(player);
                    else {
                        player.teleport(lastLoc);
                        Lang.prepare("commands.back.expressions.teleported").sendPlayer(player);
                        BackMethods.setLastLocation(player);
                    }
                } else
                    Lang.prepare("commands.back.expressions.nothing").sendPlayer(player);
            }
        }
    }
}