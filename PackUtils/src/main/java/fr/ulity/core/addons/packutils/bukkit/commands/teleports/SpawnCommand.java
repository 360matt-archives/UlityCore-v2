package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.SpawnMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnCommand extends CommandManager.Assisted {
    public SpawnCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "spawn");
        addPermission("ulity.packutils.spawn");
        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
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
        } else
            setStatus(Status.SYNTAX);
    }

}