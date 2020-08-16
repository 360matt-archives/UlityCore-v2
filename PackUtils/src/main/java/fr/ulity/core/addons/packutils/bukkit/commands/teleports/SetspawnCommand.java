package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.SpawnMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.bukkit.LangBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetspawnCommand extends CommandManager.Assisted {
    public SetspawnCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "setspawn");
        addPermission("ulity.packutils.setspawn");
        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (requirePlayer()) {
            if (arg.inRange(0, 0)) {
                Player player = (Player) sender;
                SpawnMethods.setSpawnLocation(player);
                player.sendMessage(LangBukkit.get(player, "commands.setspawn.expressions.result"));
            } else
                setStatus(Status.SYNTAX);
        }
    }
}