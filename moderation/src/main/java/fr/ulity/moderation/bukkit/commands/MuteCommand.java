package fr.ulity.moderation.bukkit.commands;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import fr.ulity.core.utils.Time;
import fr.ulity.moderation.bukkit.MainModBukkit;
import fr.ulity.moderation.bukkit.api.Mute;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Date;

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
        System.out.println(Arrays.toString(Lang.getStringArray("commands.mute.reasons_predefined")));

        if (args.length >= 1) {
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null)
                sender.sendMessage(Lang.get("global.invalid_player").replaceAll("%player%", args[0]));
            else {
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
                }
                else
                    player.sendMessage(Lang.get(player, "commands.mute.expressions.you_are_muted")
                            .replaceAll("%staff%", sender.getName())
                            .replaceAll("%reason%", reason));
            }
            return true;
        }
        return false;
    }


}
