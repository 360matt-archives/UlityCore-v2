package fr.ulity.core.addons.packutils.bukkit.commands.weather;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SunCommand extends CommandManager.Assisted {
    public SunCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "sun");
        addPermission("ulity.SuperRTP.commands.sun");
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {

        if (arg.inRange(0, 1)) {
            World world;
            if (args.length == 0 && requirePlayer()) {
                world = getPlayer().getWorld();
                world.setThundering(false);
                world.setStorm(false);

                LangBukkit.prepare("commands.sun.expressions.changed_current").sendPlayer(sender);
            } else if (arg.requireWorld(0)) {
                world = arg.getWorld(0);
                world.setThundering(false);
                world.setStorm(false);

                LangBukkit.prepare("commands.sun.expressions.changed_world")
                        .variable("world", world.getName())
                        .sendPlayer(sender);
            } else
                setStatus(Status.SYNTAX);
        } else
            setStatus(Status.SYNTAX);
    }
}
