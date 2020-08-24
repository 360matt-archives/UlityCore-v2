package fr.ulity.core.addons.packutils.bukkit.commands.economy;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.EconomyMethods;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalanceCommand extends CommandBukkit {
    public BalanceCommand() {
        super("balance");
        setPermission("ulity.packutils.balance");

        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (requirePlayer()) {
                Lang.prepare("commands.balance.expressions.your_sold")
                        .variable("money", String.valueOf(new EconomyMethods().getBalance(sender.getName())))
                        .sendPlayer(sender);
            }
        } else if (args.length == 1) {
            if (arg.requirePlayer(0)) {
                Player playerHandle = arg.getPlayer(0);
                Lang.prepare("commands.balance.expressions.sold_of_x")
                        .variable("player", playerHandle.getName())
                        .variable("money", String.valueOf(new EconomyMethods().getBalance(playerHandle.getName())))
                        .sendPlayer(sender);
            }
        } else
            setStatus(Status.SYNTAX);
    }

}