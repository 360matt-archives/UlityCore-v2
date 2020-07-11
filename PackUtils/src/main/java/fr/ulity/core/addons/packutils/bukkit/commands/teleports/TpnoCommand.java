package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Cooldown;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

public class TpnoCommand extends CommandManager.Assisted {

    public TpnoCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tpno");
        addPermission("ulity.packutils.tpno");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (requirePlayer()) {
            if (arg.inRange(0, 1)) {
                Player origin = null;
                String path_last = "tpa." + sender.getName() + ".last";

                if (Api.temp.isSet(path_last)) {
                    if (arg.is(0)) {
                        if (arg.requirePlayerNoSelf(0))
                            origin = arg.getPlayer(0);
                    } else
                        origin = Bukkit.getPlayer(Api.temp.getString(path_last));

                    if (status.equals(Status.SUCCESS)) {
                        if (Api.data.getLong("tpa." + origin.getName() + ".requests." + sender.getName()) < new Date().getTime()) {
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

                            Cooldown cooldownObj = new Cooldown("tpa", origin.getName() + "_" + sender.getName());
                            cooldownObj.clear();
                            Api.data.remove("tpa." + origin.getName() + ".requests." + sender.getName());
                            Api.data.remove("tpa." + sender.getName() + ".last");
                        }
                    }

                } else
                    Lang.prepare("commands.tpno.expressions.no_requested").sendPlayer(sender);
            } else
                setStatus(Status.SYNTAX);
        }
    }
}