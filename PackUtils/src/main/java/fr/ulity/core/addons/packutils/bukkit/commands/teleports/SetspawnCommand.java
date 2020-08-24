package fr.ulity.core.addons.packutils.bukkit.commands.teleports;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.SpawnMethods;
import fr.ulity.core_v3.bukkit.BukkitAPI;
import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.commandHandlers.bukkit.Status;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetspawnCommand extends CommandBukkit {
    public SetspawnCommand() {
        super("setspawn");
        setPermission("ulity.packutils.setspawn");
        if (!MainBukkitPackUtils.enabler.canEnable(getName()))
            unregister(BukkitAPI.commandMap);
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (requirePlayer()) {
            if (arg.inRange(0, 0)) {
                Player player = (Player) sender;
                SpawnMethods.setSpawnLocation(player);
                player.sendMessage(Lang.get(player, "commands.setspawn.expressions.result"));
            } else
                setStatus(Status.SYNTAX);
        }
    }
}