package fr.ulity.core.addons.packutils.bukkit.commands.weather;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ThunderCommand extends CommandManager.Assisted {
    public ThunderCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "thunder");
        addPermission("ulity.SuperRTP.commands.thunder");
        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {

        if (arg.inRange(0, 1)) {
            World world;
            if (args.length == 0 && requirePlayer()) {
                world = getPlayer().getWorld();
                world.setThundering(true);
                world.setStorm(true);

                Lang.prepare("commands.thunder.expressions.changed_current").sendPlayer(sender);
            } else if (arg.requireWorld(0)) {
                world = arg.getWorld(0);
                world.setThundering(true);
                world.setStorm(true);

                Lang.prepare("commands.thunder.expressions.changed_world")
                        .variable("world", world.getName())
                        .sendPlayer(sender);
            } else
                setStatus(Status.SYNTAX);
        } else
            setStatus(Status.SYNTAX);
    }
}
