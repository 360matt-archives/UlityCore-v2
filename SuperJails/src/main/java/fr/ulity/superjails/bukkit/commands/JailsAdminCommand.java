package fr.ulity.superjails.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.superjails.bukkit.api.JailsSystem;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class JailsAdminCommand extends CommandManager {
    private static int warner;


    public JailsAdminCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "jailsadmin");
        addDescription(Lang.get("commands.jailsadmin.description"));

        addUsage(Lang.get("commands.jailsadmin.usage"));
        addPermission("ulity.jails.admin");

        addArrayTabbComplete(0, "ulity.jails.admin", new String[]{}, new String[]{"create", "remove", "tp"});

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String response = null;

        if (args.length >= 1) {
            switch (args[0]) {
                case "create":
                    if (!(sender instanceof Player))
                        sender.sendMessage(Lang.get(sender, "global.player_only"));
                    else if (args.length != 2)
                        sender.sendMessage(Lang.get(sender, "commands.jailsadmin.expressions.jail_name_expected"));
                    else {
                        JailsSystem.Status status = JailsSystem.createJail(args[1], ((Player) sender).getLocation());
                        if (status.success)
                            response = Lang.get(sender, "commands.jailsadmin.expressions.create.success");
                        else if (status.code.equalsIgnoreCase("already exist"))
                            response = Lang.get(sender, "commands.jailsadmin.expressions.create.already_exist");
                    }

                    if (response != null)
                        sender.sendMessage(response
                                .replaceAll("%name%", args[1]));

                    break;
                case "remove":
                case "delete":
                    if (args.length != 2)
                        sender.sendMessage(Lang.get(sender, "commands.jailsadmin.expressions.jail_name_expected"));
                    else {
                        JailsSystem.Status status = JailsSystem.removeJail(args[1]);
                        if (status.success)
                            response = Lang.get(sender, "commands.jailsadmin.expressions.remove.success");
                        else if (status.code.equalsIgnoreCase("no exist"))
                            response = Lang.get(sender, "commands.jailsadmin.expressions.no_exist");

                        if (response != null)
                            sender.sendMessage(response
                                    .replaceAll("%name%", args[1]));
                    }
                    break;
                case "tp":
                    if (!(sender instanceof Player))
                        sender.sendMessage(Lang.get(sender, "global.player_only"));
                    else if (args.length != 2)
                        sender.sendMessage(Lang.get(sender, "commands.jailsadmin.expressions.jail_name_expected"));
                    else {
                        JailsSystem.Status status = JailsSystem.getLocation(args[1]);

                        if (status.success) {
                            ((Player) sender).teleport((Location) status.data);
                            response = Lang.get(sender, "commands.jailsadmin.expressions.tp.success");
                        }
                        else if (status.code.equalsIgnoreCase("no exist"))
                            response = Lang.get(sender, "commands.jailsadmin.expressions.no_exist");
                        else if (status.code.equalsIgnoreCase("world no exist"))
                            sender.sendMessage(response = Lang.get(sender, "commands.jailsadmin.expressions.world_no_exist")
                                    .replaceAll("%name%", args[1])
                                    .replaceAll("%worldname%", (String) status.data));

                        if (response != null)
                            sender.sendMessage(response
                                    .replaceAll("%name%", args[1]));
                    }

                    break;
                default:
                    return false;

            }
            return true;
        }

        return false;
    }

}