package fr.ulity.moderation.bukkit.commands;


import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class KickCommand extends CommandManager {

    public KickCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "kick");
        addDescription(Lang.get("commands.kick.description"));
        addUsage(Lang.get("commands.kick.usage"));
        addPermission("ulity.mod.kick");

        addOneTabbComplete(-1, "ulity.mod.kick", "kick");
        addListTabbComplete(1, null, null, Lang.getStringArray("commands.kick.reasons_predefined"));

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            Player player = Bukkit.getPlayer(args[0]);

            if (player == null){
                sender.sendMessage(Lang.get(sender, "global.invalid_player")
                        .replaceAll("%player%", args[0]));
            }
            else{
                String reason = (args.length >= 2) ? Text.fullColor(args, 1) : Lang.get("commands.kick.expressions.unknown_reason");

                if (Lang.getBoolean("commands.ban.broadcast.enabled")) {
                    String keyName = (args.length >= 2) ? "message" : "message_without_reason";
                    Bukkit.broadcastMessage(Lang.get("commands.kick.broadcast." + keyName)
                            .replaceAll("%player%", player.getName())
                            .replaceAll("%staff%", sender.getName())
                            .replaceAll("%reason%", reason));
                }

                player.kickPlayer(Lang.get(player, "commands.kick.expressions.you_are_kicked")
                        .replaceAll("%staff%", sender.getName())
                        .replaceAll("%reason%", reason));

            }

            return true;
        }
        return false;
    }


}
