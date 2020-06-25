package fr.ulity.core.addons.packutils.bukkit.commands.gamemode;

import fr.ulity.core.addons.packutils.bukkit.MainBukkitPackUtils;
import fr.ulity.core.addons.packutils.bukkit.methods.GamemodeMethods;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.Collator;
import java.util.Arrays;

public class GamemodeCommand extends CommandManager {

    public GamemodeCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "gamemode");
        addDescription(Lang.get("commands.gamemode.description"));
        addUsage(Lang.get("commands.gamemode.usage"));
        addPermission("ulity.packutils.gamemode");
        addAliases("gm");

        addListTabbComplete(0, null, null, Arrays.asList(
                Lang.get("packutils.gamemode.type.creative"),
                Lang.get("packutils.gamemode.type.survival"),
                Lang.get("packutils.gamemode.type.adventure"),
                Lang.get("packutils.gamemode.type.spectator")).toArray(new String[0]));

        if (MainBukkitPackUtils.enabler.canEnable(getName()))
            registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1 && args.length <= 2) {
            Player target;

            if (args.length == 2) {
                target = Bukkit.getPlayer(args[1]);
                if (!sender.hasPermission("ulity.packutils.gamemode.others")) {
                    sender.sendMessage(Lang.get("global.no_perm"));
                    return true;
                } else if (target == null) {
                    sender.sendMessage(Lang.get("global.invalid_player")
                            .replaceAll("%player%", args[1]));
                    return true;
                }
            } else {
                if (sender instanceof Player)
                    target = (Player) sender;
                else {
                    sender.sendMessage(Lang.get("global.player_only"));
                    return true;
                }
            }

            final Collator instance = Collator.getInstance();
            instance.setStrength(Collator.NO_DECOMPOSITION);

            GameMode mode;
            if (instance.compare(args[0], "0") == 0 || instance.compare(args[0], Lang.get("packutils.gamemode.type.survival")) == 0)
                mode = GameMode.SURVIVAL;
            else if (instance.compare(args[0], "1") == 0 || instance.compare(args[0], Lang.get("packutils.gamemode.type.creative")) == 0)
                mode = GameMode.CREATIVE;
            else if (instance.compare(args[0], "2") == 0 || instance.compare(args[0], Lang.get("packutils.gamemode.type.adventure")) == 0)
                mode = GameMode.ADVENTURE;
            else if (instance.compare(args[0], "3") == 0 || instance.compare(args[0], Lang.get("packutils.gamemode.type.spectator")) == 0)
                mode = GameMode.SPECTATOR;
            else return false;

            GamemodeMethods.define(target, mode, sender);

            return true;

        }
        return false;
    }

}