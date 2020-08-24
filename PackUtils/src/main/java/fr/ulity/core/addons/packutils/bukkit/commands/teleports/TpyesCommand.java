package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.TeleportMethods;
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

public class TpyesCommand extends CommandBukkit {

    public TpyesCommand() {
        super("tpyes");
        setPermission("ulity.packutils.tpyes");
        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (requirePlayer()) {
            String path_last = "tpa." + sender.getName() + ".last";

            if (Core.temp.contains(path_last)) {
                if (arg.inRange(0, 1)) {
                    Player origin = Bukkit.getPlayer(Core.temp.getString(path_last));
                    if (arg.is(0) && arg.requirePlayerNoSelf(0))
                        origin = arg.getPlayer(0);

                    if (status.equals(Status.SUCCESS)) {
                        if (Core.temp.getLong("tpa." + origin.getName() + ".requests." + sender.getName()) < new Date().getTime()) {
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

                            UserCooldown cooldownObj = new UserCooldown( origin.getName() + "_" + sender.getName(), "tpa");
                            cooldownObj.remove();
                            Core.temp.remove("tpa." + origin.getName() + ".requests." + sender.getName());
                            Core.temp.remove("tpa." + sender.getName() + ".last");

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