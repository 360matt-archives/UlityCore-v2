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

public class TpyesCommand extends CommandManager {

    public TpyesCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tpyes");
        addDescription(Lang.get("commands.tpyes.description"));
        addUsage(Lang.get("commands.tpyes.usage"));
        addPermission("ulity.packutils.tpyes");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
            sender.sendMessage(Lang.get(sender, "global.player_only"));
        else {

            Player origin;

            String path_last = "tpa." + sender.getName() + ".last";
            if (!Api.temp.isSet(path_last)) {
                sender.sendMessage(Lang.get(sender, "commands.tpyes.expressions.no_requested"));
            } else {
                if (args.length > 1)
                    return false;
                else if (args.length == 1)
                    origin = Bukkit.getPlayer(args[0]);
                else
                    origin = Bukkit.getPlayer(Api.temp.getString(path_last));

                if (origin == null) {
                    sender.sendMessage(Lang.get(sender, "global.invalid_player")
                            .replaceAll("%player%", (args.length == 1) ? args[0] : Api.temp.getString(path_last)));
                    return true;
                } else if (sender.getName().equals(origin.getName()))
                    sender.sendMessage(Lang.get(sender, "global.no_self"));
                else {


                    if (Api.data.getLong("tpa." + origin.getName() + ".requests." + sender.getName()) < new Date().getTime()) {
                        sender.sendMessage(Lang.get(sender, "commands.tpyes.expressions.no_requested")
                                .replaceAll("%player%", origin.getName()));
                    } else {
                        origin.sendMessage(Lang.get(origin, "commands.tpyes.expressions.request_accepted")
                                .replaceAll("%player%", sender.getName()));

                        sender.sendMessage(Lang.get(sender, "commands.tpyes.expressions.accept_result")
                                .replaceAll("%player%", origin.getName()));


                        Cooldown cooldownObj = new Cooldown("tpa", origin.getName() + "_" + sender.getName());
                        cooldownObj.clear();
                        Api.data.remove("tpa." + origin.getName() + ".requests." + sender.getName());
                        Api.data.remove("tpa." + sender.getName() + ".last");

                        Player player = (Player) sender;
                        Bukkit.getScheduler().scheduleSyncDelayedTask(MainBukkitPackUtils.plugin, new Runnable() {
                            @Override
                            public void run() {
                                if (!origin.isOnline() && player.isOnline()) {
                                    sender.sendMessage(Lang.get(sender, "commands.tpyes.expressions.disconnected")
                                            .replaceAll("%player%", origin.getName()));
                                } else if (!player.isOnline() && origin.isOnline()) {
                                    origin.sendMessage(Lang.get(origin, "commands.tpyes.expressions.disconnected")
                                            .replaceAll("%player%", sender.getName()));
                                } else {
                                    TeleportMethods teleporting = new TeleportMethods(sender);
                                    teleporting.tpPlayer(origin, player);
                                }
                            }
                        }, 20 * 5L); //20 Tick (1 Second) delay before run() is called


                    }

                }
            }

        }
        return true;
    }


}