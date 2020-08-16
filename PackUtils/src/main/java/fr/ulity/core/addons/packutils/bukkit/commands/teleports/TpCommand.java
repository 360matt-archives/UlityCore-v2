package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.TeleportMethods;
import fr.ulity.core.api.CommandManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TpCommand extends CommandManager.Assisted {

    public TpCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tp");
        addPermission("ulity.packutils.tp");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
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