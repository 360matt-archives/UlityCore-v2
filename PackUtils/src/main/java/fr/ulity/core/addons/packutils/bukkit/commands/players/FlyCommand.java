package fr.ulity.core.addons.packutils.bukkit.commands.players;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class FlyCommand extends CommandManager.Assisted {
    public FlyCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "fly");
        addPermission("ulity.packutils.fly");
        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (arg.inRange(0, 1)) {
            Player target = null;
            if (arg.is(0)) {
                if (requirePermission("ulity.packutils.fly.others"))
                    if (arg.requirePlayer(0))
                        target = arg.getPlayer(0);
            } else if (requirePlayer())
                target = (Player) sender;

            if (status.equals(Status.SUCCESS)) {
                assert target != null;
                target.setAllowFlight(!target.getAllowFlight());

                LangBukkit.Prepared preparedEnabled = LangBukkit.prepare("global.enabled").prefix("§a");
                LangBukkit.Prepared preparedDisabled = LangBukkit.prepare("global.disabled").prefix("§c");
                LangBukkit.Prepared status = (target.getAllowFlight()) ? preparedEnabled : preparedDisabled;

                LangBukkit.prepare("commands.fly.expressions.fly_changed")
                        .variable("status", status.getOutput(target))
                        .sendPlayer(target);

                if (!sender.getName().equals(target.getName())) {
                    LangBukkit.prepare("commands.fly.expressions.result")
                            .variable("status", status.getOutput(sender))
                            .variable("player", target.getName())
                            .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }

}