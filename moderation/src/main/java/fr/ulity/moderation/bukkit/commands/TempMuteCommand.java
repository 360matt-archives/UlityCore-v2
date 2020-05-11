package fr.ulity.moderation.bukkit.commands;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.Text;
import fr.ulity.core.utils.Time;
import fr.ulity.moderation.bukkit.MainModBukkit;
import fr.ulity.moderation.bukkit.api.Mute;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

public class TempMuteCommand extends CommandManager {

    public TempMuteCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "tempmute");
        addDescription(Lang.get("commands.tempmute.description"));
        addUsage(Lang.get("commands.tempmute.usage"));
        addPermission("ulity.mod.tempmute");

        addOneTabbComplete(-1, "ulity.mod.tempmute", "tempmute");
        addListTabbComplete(2, null, null, Lang.getStringArray("commands.tempmute.reasons_predefined"));

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 2) {
            @SuppressWarnings("deprecation")
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

            String reason = (args.length >= 3) ? Text.fullColor(args, 2) : Lang.get("commands.mute.expressions.unknown_reason");
            Time time = new Time(args[1]);

            Mute playerMute = new Mute(player.getName());
            playerMute.reason = reason;
            playerMute.expire = new Date().getTime() + time.milliseconds;
            playerMute.responsable = sender.getName();
            playerMute.mute();

            if (Lang.getBoolean("commands.tempmute.broadcast.enabled")) {
                String keyName = (args.length >= 3) ? "message" : "message_without_reason";
                Bukkit.broadcastMessage(Lang.get("commands.tempmute.broadcast." + keyName)
                        .replaceAll("%player%", player.getName())
                        .replaceAll("%staff%", sender.getName())
                        .replaceAll("%reason%", reason)
                        .replaceAll("%time%", time.text));
            } else if (player.isOnline())
                Bukkit.getPlayer(args[0]).sendMessage(Lang.get(player, "commands.tempmute.expressions.you_are_muted")
                        .replaceAll("%staff%", sender.getName())
                        .replaceAll("%reason%", reason)
                        .replaceAll("%time%", time.text));

            return true;
        }
        return false;
    }
}
