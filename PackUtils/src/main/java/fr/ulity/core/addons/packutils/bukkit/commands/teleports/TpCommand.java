package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.TeleportMethods;

import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import org.bukkit.Location;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpCommand extends CommandBukkit  {

    public TpCommand() {
        super("tp");
        setPermission("ulity.packutils.tp");

        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
       if (arg.inRange(1, 4)) {
            TeleportMethods teleporting = new TeleportMethods(sender);

            if (args.length == 1 && requirePlayer()) {
                if (arg.requirePlayerNoSelf(0))
                    teleporting.tpPlayer((Player) sender, arg.getPlayer(0));
            } else if (args.length == 2) {
                if (arg.requirePlayer(0) && arg.requirePlayer(1))
                    teleporting.tpPlayer(arg.getPlayer(0), arg.getPlayer(1));
            } else {
                int ind = -3 + args.length;

                try {
                    double x = Double.parseDouble(args[ind]);
                    double y = Double.parseDouble(args[ind + 1]);
                    double z = Double.parseDouble(args[ind + 2]);

                    Player player = null;
                    if (ind == 0 && requirePlayer())
                        player = (Player) sender;
                    else if (arg.requirePlayer(0))
                        player = arg.getPlayer(0);
                    else
                        setStatus(Status.STOP);

                    if (status.equals(Status.SUCCESS))
                        teleporting.tpCoords(player, new Location(player.getWorld(), x, y, z));

                } catch (NumberFormatException ignored) {
                    setStatus(Status.SYNTAX);
                }
            }
        } else
            setStatus(Status.SYNTAX);

    }


}