package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import fr.ulity.core_v3.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class StaffchatCmd extends CommandBukkit {
    public StaffchatCmd () {
        super("staffchat");
        setPermission("ulity.mod.staffchat");
        setAliases(Arrays.asList("sc"));
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1)
            Bukkit.broadcast(
                    Lang.prepare("commands.staffchat.output")
                            .variable("player", sender.getName())
                            .variable("message", new Text(args).setColored().outputString())
                            .getOutput(),
                    "ulity.mod"
            );
        else
            setStatus(Status.SYNTAX);
    }
}
