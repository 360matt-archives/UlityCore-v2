package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.moderation.bukkit.MainModBukkit;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class VanishCmd extends CommandBukkit {
    public VanishCmd () {
        super("vanish");
        setPermission("ulity.mod.vanish");
        setAliases(Arrays.asList("v"));
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        Player target = null;
        if (arg.inRange(0, 1)) {
            if (!arg.is(0)) {
                if (requirePermission("ulity.mod.other") && requirePlayer())
                    target = (Player) sender;
            } else if (arg.requirePlayer(0))
                target = arg.getPlayer(0);
        } else
            setStatus(Status.SYNTAX);

        if (status.equals(Status.SUCCESS)) {
            if (!Core.temp.contains(target.getName() + ".vanish")){
                Core.temp.set(target.getName() + ".vanish", true);

                for (Player onePlayer : Bukkit.getOnlinePlayers())
                    if (!onePlayer.hasPermission("ulity.mod.vanish"))
                        onePlayer.hidePlayer(MainModBukkit.plugin, target);

                Lang.prepare("commands.vanish.expressions.vanished_notification").sendPlayer(target);
                if (!target.getName().equals(sender.getName()))
                    Lang.prepare("commands.vanish.expressions.vanished_other")
                            .variable("player", target.getName())
                            .sendPlayer(sender);
            } else{
                Core.temp.remove(target.getName() + ".vanish");

                for (Player onePlayer : Bukkit.getOnlinePlayers())
                    onePlayer.showPlayer(MainModBukkit.plugin, target);

                Lang.prepare("commands.vanish.expressions.unvanished_notification").sendPlayer(target);
                if (!target.getName().equals(sender.getName()))
                    Lang.prepare("commands.vanish.expressions.unvanished_other")
                            .variable("player", target.getName())
                            .sendPlayer(sender);
            }
        }
    }
}
