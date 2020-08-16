package fr.ulity.moderation.bukkit.commands;

import fr.ulity.core.api.bukkit.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.moderation.bukkit.api.Mute;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class UnMuteCommand extends CommandManager.Assisted {

    public UnMuteCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "unmute");
        addPermission("ulity.mod.unmute");
        addListTabbComplete(1, "ulity.mod.mute", "");
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            Mute playerMute = new Mute(player.getName());

            if (playerMute.isMute()){
                playerMute.unmute();
                LangBukkit.prepare("commands.unmute.expressions.unmuted")
                        .variable("player", player.getName())
                        .sendPlayer(sender);

                if (player.isOnline())
                    LangBukkit.prepare("commands.unmute.expressions.you_are_unmuted")
                            .variable("staff", sender.getName())
                            .sendPlayer(arg.getPlayer(0));

            } else
                LangBukkit.prepare("commands.unmute.expressions.is_not_muted")
                        .variable("player", player.getName())
                        .sendPlayer(sender);
        } else
            setStatus(Status.SYNTAX);
    }
}
