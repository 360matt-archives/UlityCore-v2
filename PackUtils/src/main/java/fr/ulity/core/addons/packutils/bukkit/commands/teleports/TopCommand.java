package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TopCommand extends CommandBukkit {
    public TopCommand() {
        super("top");
        setPermission("ulity.packutils.top");
        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    public static Location getTopLoc (Player player) {
        Location loc = player.getLocation();
        int y = player.getLocation().getWorld().getHighestBlockYAt(loc) + 1;
        return new Location(loc.getWorld(), loc.getX(), y, loc.getZ());
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (arg.inRange(0, 1)) {
            if (!arg.is(0) && requirePlayer()) {
                Player player = (Player) sender;
                player.teleport(getTopLoc(player));
                player.sendMessage(Lang.get(player, "commands.top.expressions.notification"));
            } else if (requirePermission("ulity.packutils.top.others")) {
                if (arg.requirePlayer(0)) {
                    Player target = arg.getPlayer(0);

                    target.teleport(getTopLoc(target));
                    Lang.prepare("commands.top.expressions.notification").sendPlayer(target);

                    if (!sender.getName().equals(target.getName()))
                        Lang.prepare("commands.top.expressions.result_other")
                                .variable("player", target.getName())
                                .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}