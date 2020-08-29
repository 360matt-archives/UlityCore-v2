package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.moderation.api.sanctions.BanIP;
import fr.ulity.moderation.api.sanctions.BanUser;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UnbanIpCmd extends CommandBukkit {
    public UnbanIpCmd () {
        super("unbanip");
        setPermission("ulity.mod.unbanip");
    }


    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            BanIP banIP = new BanIP(arg.get(0).replaceAll("\\.", "_"));
            // les points sont remplacés par des underscores pour le stockage

            String result;
            if (banIP.isBanned()){
                banIP.unban();
                result = "unbanned";
            } else
                result = "is_not_banned";

            Lang.prepare("commands.unbanip.expressions." + result)
                    .variable("ip", arg.get(0))
                    .sendPlayer(sender);
        } else
            setStatus(Status.SYNTAX);
    }
}
