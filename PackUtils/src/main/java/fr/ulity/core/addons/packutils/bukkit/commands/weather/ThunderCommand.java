package fr.ulity.core.addons.packutils.bukkit.commands.weather;


import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ThunderCommand extends CommandBukkit {
    public ThunderCommand( ) {
        super("thunder");
        setPermission("ulity.SuperRTP.commands.thunder");
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {

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
