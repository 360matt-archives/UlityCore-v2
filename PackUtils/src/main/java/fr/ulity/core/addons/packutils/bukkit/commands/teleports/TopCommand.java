package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TopCommand extends CommandManager.Assisted {
    public TopCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "top");
        addPermission("ulity.packutils.top");
        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    public static Location getTopLoc (Player player) {
        Location loc = player.getLocation();
        int y = player.getLocation().getWorld().getHighestBlockYAt(loc) + 1;
        return new Location(loc.getWorld(), loc.getX(), y, loc.getZ());
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (arg.inRange(0, 1)) {
            if (!arg.is(0) && requirePlayer()) {
                Player player = (Player) sender;
                player.teleport(getTopLoc(player));
                player.sendMessage(LangBukkit.get(player, "commands.top.expressions.notification"));
            } else if (requirePermission("ulity.packutils.top.others")) {
                if (arg.requirePlayer(0)) {
                    Player target = arg.getPlayer(0);

                    target.teleport(getTopLoc(target));
                    LangBukkit.prepare("commands.top.expressions.notification").sendPlayer(target);

                    if (!sender.getName().equals(target.getName()))
                        LangBukkit.prepare("commands.top.expressions.result_other")
                                .variable("player", target.getName())
                                .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}