package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Text;
import fr.ulity.moderation.api.sanctions.FreezeUser;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class FreezeCmd extends CommandBukkit {
    public FreezeCmd () {
        super("freeze");
        setPermission("ulity.mod.freeze");
        addListTabbComplete(1, null, null, Lang.getArray("commands.ban.reasons_predefined"));
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            if (arg.requirePlayer(0)) {
                Player target = arg.getPlayer(0);
                if (target.hasPermission("ulity.mod")){
                    Lang.prepare("commands.freeze.expressions.cant_freeze_staff")
                            .variable("player", target.getName())
                            .sendPlayer(sender);
                } else {
                    String reason = (args.length >= 2)
                            ? new Text(args).setColored().setBeginging(1).outputString()
                            : Lang.get("commands.freeze.expressions.unknown_reason");

                    FreezeUser playerFreeze = new FreezeUser(args[0]);
                    playerFreeze.reason = reason;
                    playerFreeze.staff = sender.getName();
                    playerFreeze.when = new Date();
                    playerFreeze.freeze();

                    Lang.prepare("commands.freeze.expressions.player_freezed")
                            .variable("player", target.getName())
                            .sendPlayer(sender);
                }
            }
        } else
            setStatus(Status.SYNTAX);
    }
}
