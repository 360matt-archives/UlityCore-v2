package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.moderation.api.sanctions.BanUser;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UnbanCmd extends CommandBukkit {
    public UnbanCmd() {
        super("unban");
        setPermission("ulity.mod.unban");
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            @SuppressWarnings("deprecation")
            OfflinePlayer player = Bukkit.getOfflinePlayer(arg.get(0));

            BanUser playerBan = new BanUser(player.getName());
            String result;
            if (playerBan.isBanned()){
                playerBan.unban();
                result = "unbanned";
            } else
                result = "is_not_banned";

            Lang.prepare("commands.unban.expressions." + result)
                    .variable("player", player.getName())
                    .sendPlayer(sender);
        } else
            setStatus(Status.SYNTAX);
    }
}
