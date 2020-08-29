package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.datas.UserCooldown;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class TpnoCommand extends CommandBukkit {

    public TpnoCommand() {
        super("tpno");
        setPermission("ulity.packutils.tpno");

        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (requirePlayer()) {
            if (arg.inRange(0, 1)) {
                Player origin = null;
                String path_last = "tpa." + sender.getName() + ".last";

                if (Core.temp.contains(path_last)) {
                    if (arg.is(0)) {
                        if (arg.requirePlayerNoSelf(0))
                            origin = arg.getPlayer(0);
                    } else
                        origin = Bukkit.getPlayer(Core.temp.getString(path_last));

                    if (status.equals(Status.SUCCESS)) {
                        if (Core.temp.getLong("tpa." + origin.getName() + ".requests." + sender.getName()) < new Date().getTime()) {
                            Lang.prepare("commands.tpno.expressions.no_requested")
                                    .variable("player", origin.getName())
                                    .sendPlayer(sender);
                        } else {
                            Lang.prepare("commands.tpno.expressions.request_accepted")
                                    .variable("player", sender.getName())
                                    .sendPlayer(origin);

                            Lang.prepare("commands.tpno.expressions.accept_result")
                                    .variable("player", origin.getName())
                                    .sendPlayer(sender);

                            UserCooldown cooldownObj = new UserCooldown(origin.getName(), "tpa" + "_" + sender.getName());
                            cooldownObj.remove();
                            Core.temp.remove("tpa." + origin.getName() + ".requests." + sender.getName());
                            Core.temp.remove("tpa." + sender.getName() + ".last");
                        }
                    }

                } else
                    Lang.prepare("commands.tpno.expressions.no_requested").sendPlayer(sender);
            } else
                setStatus(Status.SYNTAX);
        }
    }
}