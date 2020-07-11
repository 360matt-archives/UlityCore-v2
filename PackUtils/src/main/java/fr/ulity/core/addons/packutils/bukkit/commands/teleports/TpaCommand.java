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

public class TpaCommand extends CommandManager.Assisted {

    public TpaCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tpa");
        addPermission("ulity.packutils.tpa");
        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (requirePlayer()) {
            if (arg.requirePlayerNoSelf(0)) {
                Player player = (Player) sender;
                Player target = arg.getPlayer(0);

                String ignore_path = "player." + target.getName() + ".vanish";

                if (Api.data.contains(ignore_path)) {
                    if (Api.data.getList(ignore_path).contains(sender.getName())) {
                        // le joueur est bloqué par l'autre joueur
                        Lang.prepare("commands.tpa.expressions.you_are_bloqued")
                                .variable("player", target.getName())
                                .sendPlayer(sender);
                        setStatus(Status.STOP);

                    }
                }

                if (status.equals(Status.SUCCESS)) {
                    Cooldown cooldownObj = new Cooldown("tpa", sender.getName() + "_" + target.getName());
                    cooldownObj.setPlayer(player);

                    if (!cooldownObj.isInitialized() && !cooldownObj.isEnded()) {
                        Lang.prepare("commands.tpa.expressions.cooldown")
                                .variable("left", cooldownObj.getTimeLeft().text)
                                .sendPlayer(sender);
                    } else {
                        Lang.prepare("commands.tpa.expressions.request_sent")
                                .variable("player", target.getName())
                                .sendPlayer(sender);

                        Lang.prepare("commands.tpa.expressions.request_body")
                                .variable("player", sender.getName())
                                .sendPlayer(target);

                        cooldownObj.applique(120);
                        Api.data.set("tpa." + sender.getName() + ".requests." + target.getName(), new Date().getTime() + 15000);
                        Api.temp.set("tpa." + target.getName() + ".last", sender.getName());
                    }
                }
            }
        }
    }
}