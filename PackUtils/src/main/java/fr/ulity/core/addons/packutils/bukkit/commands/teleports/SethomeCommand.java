package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.api.UserHome;
import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SethomeCommand extends CommandBukkit {
    public SethomeCommand() {
        super("sethome");
        setPermission("ulity.packutils.sethome");
        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (requirePlayer()) {
            if (arg.inRange(0, 1)) {
                Player player = (Player) sender;
                UserHome userHome = new UserHome(sender.getName());

                if (!arg.is(0))
                    args = new String[]{"home"};
                if (!StringUtils.isAlphanumeric(args[0]))
                    Lang.prepare("commands.sethome.expressions.alphanumeric_required").sendPlayer(sender);
                else {
                    int max = MainBukkitPackUtils.config.getInt("homes.max");

                    if (!userHome.isExist(args[0]) && userHome.getCount() >= max) {
                        if (!(MainBukkitPackUtils.config.getBoolean("homes.staff_bypass") && player.hasPermission("ulity.packutils.home.bypass"))) {
                            Lang.prepare("commands.sethome.expressions.limit")
                                    .variable("count", String.valueOf(max))
                                    .sendPlayer(sender);
                            return;
                        }
                    }

                    userHome.setHome(args[0], player.getLocation());
                    sender.sendMessage(Lang.get(sender, "commands.sethome.expressions.created")
                            .replaceAll("%home%", args[0]));
                }
            } else
                setStatus(Status.SYNTAX);
        }
    }
}