package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.moderation.bukkit.MainModBukkit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VanishCommand extends CommandManager.Assisted {

    public VanishCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "vanish");
        addPermission("ulity.mod.vanish");
        addPermission("ulity.mod.vanish.others");
        addPermission("ulity.mod.vanish.self");
        addOneTabbComplete(-1, "vanish");
        addAliases("v");
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
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
            if (!Api.temp.isSet(target.getName() + ".vanish")){
                Api.temp.set(target.getName() + ".vanish", true);

                for (Player onePlayer : Bukkit.getOnlinePlayers())
                    if (!onePlayer.hasPermission("ulity.mod.vanish"))
                        onePlayer.hidePlayer(MainModBukkit.plugin, target);

                Lang.prepare("commands.vanish.expressions.vanished_notification").sendPlayer(target);
                if (!target.getName().equals(sender.getName()))
                    Lang.prepare("commands.vanish.expressions.vanished_other")
                            .variable("player", target.getName())
                            .sendPlayer(sender);
            } else{
                Api.temp.remove(target.getName() + ".vanish");

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
