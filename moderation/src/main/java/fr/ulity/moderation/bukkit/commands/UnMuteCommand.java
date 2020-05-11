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

public class UnMuteCommand extends CommandManager {

    public UnMuteCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "unmute");
        addDescription(Lang.get("commands.unmute.description"));
        addUsage(Lang.get("commands.unmute.usage"));
        addPermission("ulity.mod.unmute");

        addOneTabbComplete(-1, "unmute");
        addListTabbComplete(1, "ulity.mod.mute", "");

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            Mute playerMute = new Mute(player.getName());

            if (playerMute.isMute()){
                playerMute.unmute();
                sender.sendMessage(Lang.get(sender, "commands.unmute.expressions.unmuted")
                        .replaceAll("%player%", player.getName()));

                if (player.isOnline())
                    Bukkit.getPlayer(args[0]).sendMessage(Lang.get(sender, "commands.unmute.expressions.you_are_unmuted")
                            .replaceAll("%staff%", sender.getName()));
            } else
                sender.sendMessage(Lang.get(sender, "commands.unmute.expressions.is_not_muted")
                        .replaceAll("%player%", player.getName()));

            return true;
        }
        return false;
    }
}
