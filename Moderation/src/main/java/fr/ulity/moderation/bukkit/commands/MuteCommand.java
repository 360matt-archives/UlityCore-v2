package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import fr.ulity.moderation.bukkit.api.Mute;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MuteCommand extends CommandManager {

    public MuteCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "mute");
        addDescription(Lang.get("commands.mute.description"));
        addUsage(Lang.get("commands.mute.usage"));
        addPermission("ulity.mod.mute");

        addOneTabbComplete(-1, "ulity.mod.mute", "mute");
        addListTabbComplete(1, null, null, Lang.getStringArray("commands.mute.reasons_predefined"));

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

            if (player != null && player.hasPlayedBefore()){
                if (Bukkit.getPlayer(args[0]).hasPermission("ulity.mod")){
                    sender.sendMessage(Lang.get(sender, "commands.mute.expressions.cant_mute_staff")
                            .replaceAll("%player%", args[0]));
                    return true;
                }
            }

            String reason = (args.length >= 2) ? Text.fullColor(args, 1) : Lang.get("commands.mute.expressions.unknown_reason");

            Mute playerMute = new Mute(player.getName());
            playerMute.reason = reason;
            playerMute.expire = 0;
            playerMute.responsable = sender.getName();
            playerMute.mute();

            if (Lang.getBoolean("commands.mute.broadcast.enabled")) {
                String keyName = (args.length >= 2) ? "message" : "message_without_reason";
                Bukkit.broadcastMessage(Lang.get("commands.mute.broadcast." + keyName)
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%staff%", sender.getName())
                        .replaceAll("%reason%", reason));
            } else {
                if (player.isOnline())
                    Bukkit.getPlayer(args[0]).sendMessage(Lang.get(player, "commands.mute.expressions.you_are_muted")
                            .replaceAll("%staff%", sender.getName())
                            .replaceAll("%reason%", reason));
                sender.sendMessage(Lang.get(sender, "commands.mute.expressions.result")
                        .replaceAll("%player%", player.getName()));
            }

            return true;
        }
        return false;
    }


}
