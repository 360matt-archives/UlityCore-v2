package fr.ulity.moderation.bukkit.commands;


import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.moderation.bukkit.api.Ban;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class UnBanCommand extends CommandManager {

    public UnBanCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "unban");
        addDescription(Lang.get("commands.unban.description"));
        addUsage(Lang.get("commands.unban.usage"));
        addPermission("ulity.mod.unban");

        addOneTabbComplete(-1, "ulity.mod.unban", "unban");
        addListTabbComplete(1, null, null, Lang.getStringArray("commands.unban.reasons_predefined"));

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

            Ban playerBan = new Ban(player.getName());
            String result;
            if (playerBan.isBan()){
                playerBan.unban();
                result = "unbanned";
            } else
                result = "is_not_banned";

            sender.sendMessage(Lang.get(sender, "commands.unban.expressions." + result)
                    .replaceAll("%player%", player.getName()));

            return true;
        }
        return false;
    }


}
