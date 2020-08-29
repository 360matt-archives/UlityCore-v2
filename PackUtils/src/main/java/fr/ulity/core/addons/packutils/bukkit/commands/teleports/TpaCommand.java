package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.datas.UserCooldown;
import fr.ulity.core_v3.modules.datas.UserData;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class TpaCommand extends CommandBukkit {

    public TpaCommand( ) {
        super("tpa");
        setPermission("ulity.packutils.tpa");
        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (requirePlayer()) {
            if (arg.requirePlayerNoSelf(0)) {
                Player target = arg.getPlayer(0);

                UserData userData = new UserData(sender.getName());

                if (userData.getList("ignored").contains(sender.getName())) {
                    // le joueur est bloqué par l'autre joueur
                    Lang.prepare("commands.tpa.expressions.you_are_bloqued")
                            .variable("player", target.getName())
                            .sendPlayer(sender);
                    setStatus(Status.STOP);
                }

                if (status.equals(Status.SUCCESS)) {
                    UserCooldown cooldownObj = new UserCooldown(sender.getName(), "tpa" + "_" + target.getName());

                    if (cooldownObj.isWaiting()) {
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
                        Core.temp.set("tpa." + sender.getName() + ".requests." + target.getName(), new Date().getTime() + 15000);
                        Core.temp.set("tpa." + target.getName() + ".last", sender.getName());
                    }
                }
            }
        }
    }
}