package fr.ulity.core.addons.packutils.bukkit.commands.players;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.modules.language.PreparedLang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class FlyCommand extends CommandBukkit {
    public FlyCommand() {
        super("fly");
        setPermission("ulity.packutils.fly");
        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
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

                PreparedLang preparedEnabled = Lang.prepare("global.enabled").prefix("§a");
                PreparedLang preparedDisabled = Lang.prepare("global.disabled").prefix("§c");
                PreparedLang status = (target.getAllowFlight()) ? preparedEnabled : preparedDisabled;

                Lang.prepare("commands.fly.expressions.fly_changed")
                        .variable("status", status.getOutput(target))
                        .sendPlayer(target);

                if (!sender.getName().equals(target.getName())) {
                    Lang.prepare("commands.fly.expressions.result")
                            .variable("status", status.getOutput(sender))
                            .variable("player", target.getName())
                            .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }

}