package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.HomeMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SethomeCommand extends CommandManager.Assisted {
    public SethomeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "sethome");
        addPermission("ulity.packutils.sethome");
        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (requirePlayer()) {
            if (arg.inRange(0, 1)) {
                Player player = (Player) sender;

                if (!arg.is(0))
                    args = new String[]{"home"};
                if (!StringUtils.isAlphanumeric(args[0]))
                    LangBukkit.prepare("commands.sethome.expressions.alphanumeric_required").sendPlayer(sender);
                else {
                    int max = MainBukkitPackUtils.config.getInt("homes.max");

                    if (!HomeMethods.isHomeExist(player, args[0]) && HomeMethods.getHomeCount(player) >= max) {
                        if (!(MainBukkitPackUtils.config.getBoolean("homes.staff_bypass") && player.hasPermission("ulity.packutils.home.bypass"))) {
                            LangBukkit.prepare("commands.sethome.expressions.limit")
                                    .variable("count", String.valueOf(max))
                                    .sendPlayer(sender);
                            return;
                        }
                    }

                    HomeMethods.setHomeLocation(player, args[0]);
                    sender.sendMessage(LangBukkit.get(sender, "commands.sethome.expressions.created")
                            .replaceAll("%home%", args[0]));
                }
            } else
                setStatus(Status.SYNTAX);
        }
    }
}