package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.moderation.api.sanctions.MuteUser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UnmuteCmd extends CommandBukkit {
    public UnmuteCmd () {
        super("unmute");
        setPermission("ulity.mod.unmute");
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            MuteUser playerMute = new MuteUser(player.getName());

            if (playerMute.isMuted()){
                playerMute.unmute();
                Lang.prepare("commands.unmute.expressions.unmuted")
                        .variable("player", player.getName())
                        .sendPlayer(sender);

                if (player.isOnline())
                    Lang.prepare("commands.unmute.expressions.you_are_unmuted")
                            .variable("staff", sender.getName())
                            .sendPlayer(arg.getPlayer(0));

            } else
                Lang.prepare("commands.unmute.expressions.is_not_muted")
                        .variable("player", player.getName())
                        .sendPlayer(sender);
        } else
            setStatus(Status.SYNTAX);
    }
}
