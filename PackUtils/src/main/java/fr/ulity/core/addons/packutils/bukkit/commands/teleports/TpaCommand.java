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

public class TpaCommand extends CommandManager {

    public TpaCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tpa");
        addDescription(Lang.get("commands.tpa.description"));
        addUsage(Lang.get("commands.tpa.usage"));
        addPermission("ulity.packutils.tpa");

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.get(sender, "global.player_only"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null)
                sender.sendMessage(Lang.get(sender, "global.invalid_player")
                        .replaceAll("%player%", args[0]));
            else if (sender.getName().equals(target.getName()))
                sender.sendMessage(Lang.get(sender, "global.no_self"));
            else {
                String ignore_path = "player." + target.getName() + ".vanish";
                if (Api.data.contains(ignore_path)) {
                    if (Api.data.getList(ignore_path).contains(sender.getName())) {
                        // bloqué
                        sender.sendMessage(Lang.get(sender, "commands.tpa.expressions.you_are_bloqued")
                                .replaceAll("%player%", target.getName()));
                        return true;
                    }
                } else {
                    Cooldown cooldownObj = new Cooldown("tpa", sender.getName() + "_" + target.getName());
                    cooldownObj.setPlayer(player);

                    if (!cooldownObj.isInitialized() && !cooldownObj.isEnded()) {
                        sender.sendMessage(Lang.get(sender, "commands.tpa.expressions.cooldown")
                                .replaceAll("%left%", cooldownObj.getTimeLeft().text));
                    } else {
                        sender.sendMessage(Lang.get(sender, "commands.tpa.expressions.request_sent")
                                .replaceAll("%player%", target.getName()));

                        target.sendMessage(Lang.get(sender, "commands.tpa.expressions.request_body")
                                .replaceAll("%player%", sender.getName()));

                        cooldownObj.applique(120);
                        Api.data.set("tpa." + sender.getName() + ".requests." + target.getName(), new Date().getTime() + 15000);

                        Api.temp.set("tpa." + target.getName() + ".last", sender.getName());

                    }
                }
            }

            return true;
        }


        return false;
    }

}