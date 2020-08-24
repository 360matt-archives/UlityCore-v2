package fr.ulity.core.addons.packutils.bukkit.commands.economy;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.StoredEconomy;
import fr.ulity.core.addons.packutils.bukkit.methods.EconomyMethods;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EcoCommand extends CommandBukkit {
    public EcoCommand() {
        super("eco");
        setPermission("ulity.packutils.eco");
        addArrayTabbComplete(0, null, new String[0], new String[]{"set", "add", "remove"});
        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (arg.get(0).equals("clear")) {
            StoredEconomy.custom.clearAll();
            Lang.prepare("commands.eco.expressions.all_accounts_cleared").sendPlayer(sender);
        } else if (arg.is(0) && arg.requirePlayer(1)) {
            Player playerHandle = arg.getPlayer(1);

            if (args[0].equalsIgnoreCase("set") && arg.requireNumber(2)) {
                StoredEconomy.custom.set("player." + playerHandle.getName(), Double.parseDouble(args[2]));
                if (!sender.getName().equals(playerHandle.getName())) {
                    Lang.prepare("commands.eco.expressions.sender.sold_set")
                            .variable("player", playerHandle.getName())
                            .variable("money", args[2])
                            .sendPlayer(sender);
                }
                Lang.prepare("commands.eco.expressions.player.sold_set")
                        .variable("money", args[2])
                        .sendPlayer(playerHandle);
            } else if (args[0].equalsIgnoreCase("add") && arg.requireNumber(2)) {
                new EconomyMethods().depositPlayer(playerHandle.getName(), Double.parseDouble(args[2]));
                if (!sender.getName().equals(playerHandle.getName())) {
                    Lang.prepare("commands.eco.expressions.sender.sold_added")
                            .variable("player", playerHandle.getName())
                            .variable("added", args[2])
                            .sendPlayer(sender);
                }
                Lang.prepare("commands.eco.expressions.player.sold_added")
                        .variable("added", args[2])
                        .sendPlayer(playerHandle);
            } else if (args[0].equalsIgnoreCase("remove") && arg.requireNumber(2)) {
                new EconomyMethods().withdrawPlayer(playerHandle.getName(), Double.parseDouble(args[2]));
                if (!sender.getName().equals(playerHandle.getName())) {
                    Lang.prepare("commands.eco.expressions.sender.sold_taked")
                            .variable("player", playerHandle.getName())
                            .variable("taked", args[2])
                            .sendPlayer(sender);
                }
                Lang.prepare("commands.eco.expressions.player.sold_taked")
                        .variable("taked", args[2])
                        .sendPlayer(playerHandle);
            } else
                setStatus(Status.SYNTAX);
        }
    }
}