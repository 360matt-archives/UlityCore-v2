package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.TeleportMethods;
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

public class TpyesCommand extends CommandManager.Assisted {

    public TpyesCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tpyes");
        addPermission("ulity.packutils.tpyes");
        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (requirePlayer()) {
            String path_last = "tpa." + sender.getName() + ".last";

            if (Api.temp.isSet(path_last)) {
                if (arg.inRange(0, 1)) {
                    Player origin = Bukkit.getPlayer(Api.temp.getString(path_last));
                    if (arg.is(0) && arg.requirePlayerNoSelf(0))
                        origin = arg.getPlayer(0);

                    if (status.equals(Status.SUCCESS)) {
                        if (Api.data.getLong("tpa." + origin.getName() + ".requests." + sender.getName()) < new Date().getTime()) {
                            Lang.prepare("commands.tpyes.expressions.no_requested")
                                    .variable("player", origin.getName())
                                    .sendPlayer(sender);
                        } else {
                            Lang.prepare("commands.tpyes.expressions.request_accepted")
                                    .variable("player", sender.getName())
                                    .sendPlayer(origin);

                            Lang.prepare("commands.tpyes.expressions.accept_result")
                                    .variable("player", origin.getName())
                                    .sendPlayer(sender);

                            Cooldown cooldownObj = new Cooldown("tpa", origin.getName() + "_" + sender.getName());
                            cooldownObj.clear();
                            Api.data.remove("tpa." + origin.getName() + ".requests." + sender.getName());
                            Api.data.remove("tpa." + sender.getName() + ".last");

                            Player player = (Player) sender;
                            Player finalOrigin = origin;
                            Bukkit.getScheduler().scheduleSyncDelayedTask(MainBukkitPackUtils.plugin, new Runnable() {
                                @Override
                                public void run() {
                                    if (!finalOrigin.isOnline() && player.isOnline()) {
                                        Lang.prepare("commands.tpyes.expressions.disconnected")
                                                .variable("player", finalOrigin.getName())
                                                .sendPlayer(sender);
                                    } else if (!player.isOnline() && finalOrigin.isOnline()) {
                                        Lang.prepare("commands.tpyes.expressions.disconnected")
                                                .variable("player", sender.getName())
                                                .sendPlayer(finalOrigin);
                                    } else {
                                        TeleportMethods teleporting = new TeleportMethods(sender);
                                        teleporting.tpPlayer(finalOrigin, player);
                                    }
                                }
                            }, 20 * 5L);
                        }
                    }
                } else
                    setStatus(Status.SYNTAX);
            } else
                sender.sendMessage(Lang.get(sender, "commands.tpyes.expressions.no_requested"));
        }
    }
}