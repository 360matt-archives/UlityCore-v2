package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.moderation.api.sanctions.FreezeUser;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UnfreezeCmd extends CommandBukkit {
    public UnfreezeCmd () {
        super("unfreeze");
        setPermission("ulity.mod.unfreeze");
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            FreezeUser playerFreeze = new FreezeUser(args[0]);
            if (playerFreeze.isFrozen()){
                playerFreeze.unfreeze();

                Lang.prepare("commands.unfreeze.expressions.player_unfreezed")
                        .variable("player", args[0])
                        .sendPlayer(sender);

                if (arg.isPlayer(0)) // if is online
                    Lang.prepare("commands.unfreeze.expressions.notification").sendPlayer(arg.getPlayer(0));
            }
            else
                Lang.prepare("commands.unfreeze.expressions.is_not_freezed")
                        .variable("player", args[0])
                        .sendPlayer(sender);
        } else
            setStatus(Status.SYNTAX);
    }
}
