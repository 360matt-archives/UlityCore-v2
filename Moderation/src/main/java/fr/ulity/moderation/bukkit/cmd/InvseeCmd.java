package fr.ulity.moderation.bukkit.cmd;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class InvseeCmd extends CommandBukkit {
    public InvseeCmd () {
        super("invsee");
        setPermission("ulity.mod.invsee");
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (requirePlayer()) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);

                if (arg.requirePlayer(0)) {
                    PlayerInventory inventory = target.getInventory();
                    ((Player) sender).openInventory(inventory);

                    Lang.prepare("commands.invsee.expressions.inventory_opened")
                            .variable("player", target.getName())
                            .sendPlayer(sender);
                }
            } else
                setStatus(Status.SYNTAX);
        }
    }
}
