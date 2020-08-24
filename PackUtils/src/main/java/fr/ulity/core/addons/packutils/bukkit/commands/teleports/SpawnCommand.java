package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.SpawnMethods;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand extends CommandBukkit {
    public SpawnCommand() {
        super("spawn");
        setPermission("ulity.packutils.spawn");
        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        Location spawnLoc = SpawnMethods.getSpawnLocation();

        if (spawnLoc == null)
            Lang.prepare("commands.spawn.expressions.not_defined").sendPlayer(sender);
        else if (arg.inRange(0, 1)) {
            Player origin = null;

            if (!arg.is(0) && requirePlayer())
                origin = (Player) sender;
            else if (requirePermission("ulity.packutils.spawn.others"))
                if (arg.requirePlayer(0))
                    origin = arg.getPlayer(0);

            if (status.equals(Status.SUCCESS)) {
                assert origin != null;

                if (origin.hasPermission("ulity.packutils.spawn.bypass") || origin.hasPermission("grade.staff")) {
                    origin.teleport(spawnLoc);
                    Lang.prepare("commands.spawn.expressions.teleported").sendPlayer(origin);
                } else {
                    Lang.prepare("commands.spawn.expressions.prevent_teleport").sendPlayer(origin);

                    if (!origin.getName().equals(sender.getName()))
                        Lang.prepare("commands.spawn.expressions.others_result")
                                .variable("player", origin.getName())
                                .sendPlayer(sender);

                    Player finalOrigin = origin;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(MainBukkitPackUtils.plugin, () -> {
                        if (finalOrigin.isOnline()) {
                            finalOrigin.teleport(spawnLoc);
                            Lang.prepare("commands.spawn.expressions.teleported").sendPlayer(finalOrigin);
                        }
                    }, 20*5L);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }

}