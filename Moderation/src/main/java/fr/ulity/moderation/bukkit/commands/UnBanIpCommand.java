package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.moderation.bukkit.api.Ban;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class UnBanIpCommand extends CommandManager {

    public UnBanIpCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "unbanip");
        addDescription(Lang.get("commands.unbanip.description"));
        addUsage(Lang.get("commands.unbanip.usage"));
        addPermission("ulity.mod.unbanip");

        addOneTabbComplete(-1, "ulity.mod.unbanip", "unbanip");

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            String ip = args[0];

            Ban playerBanIP = new Ban("ip_" + ip.replaceAll("\\.", "_"));
            String result;
            if (playerBanIP.isBan()){
                playerBanIP.unban();
                result = "unbanned";
            } else
                result = "is_not_banned";

            sender.sendMessage(Lang.get(sender, "commands.unbanip.expressions." + result)
                    .replaceAll("%ip%", args[0]));

            return true;
        }
        return false;

    }

}