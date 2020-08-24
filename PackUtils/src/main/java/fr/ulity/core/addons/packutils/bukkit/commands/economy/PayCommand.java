package fr.ulity.core.addons.packutils.bukkit.commands.economy;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.EconomyMethods;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PayCommand extends CommandBukkit {
    public PayCommand() {
        super("pay");
        setPermission("ulity.packutils.pay");
        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (requirePlayer()) {
            if (arg.requirePlayerNoSelf(0)) {
                if (arg.requireNumber(1)) {
                    Player playerHandle = arg.getPlayer(0);
                    if (new EconomyMethods().has(sender.getName(), Double.parseDouble(args[1]))) {
                        Lang.prepare("commands.pay.expressions.received")
                                .variable("money", args[1])
                                .variable("player", sender.getName())
                                .sendPlayer(playerHandle);

                        Lang.prepare("commands.pay.expressions.result")
                                .variable("money", args[1])
                                .variable("player", playerHandle.getName())
                                .sendPlayer(sender);

                        new EconomyMethods().withdrawPlayer(getPlayer(), Double.parseDouble(args[1]));
                        new EconomyMethods().depositPlayer(playerHandle, Double.parseDouble(args[1]));


                    } else {
                        Lang.prepare("commands.pay.expressions.not_enough_money")
                                .variable("money", args[1])
                                .sendPlayer(sender);
                    }
                }
            }
        }
    }

}