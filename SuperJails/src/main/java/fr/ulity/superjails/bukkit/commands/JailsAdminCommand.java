package fr.ulity.superjails.bukkit.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.utils.EnumUtil;
import fr.ulity.core.utils.Text;
import fr.ulity.superjails.bukkit.api.JailsSystem;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getPluginManager;

public class JailsAdminCommand extends CommandManager implements Listener {

    public JailsAdminCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "jailsadmin");
        addDescription(Lang.get("commands.jailsadmin.description"));

        addUsage(Lang.get("commands.jailsadmin.usage"));
        addPermission("ulity.jails.admin");

        addArrayTabbComplete(0, "ulity.jails.admin", new String[]{}, new String[]{"create", "remove", "tp", "edit", "list"});
        addOneTabbComplete(1, "ulity.jails.admin", "§AllJails", "remove", "tp", "edit");


        getPluginManager().registerEvents(this, getPlugin());

        registerCommand(commandMap);
    }

    @EventHandler
    public void onTabComplete(TabCompleteEvent e) {
        String request = e.getBuffer();
        String[] args = request.split(" ");

        if (args[0].replace("/", "").equalsIgnoreCase(getName())){
            if (e.getCompletions().contains("§AllJails"))
                e.setCompletions(Arrays.asList(JailsSystem.getAllJails()));
            else if (args.length == 3 && args[1].equalsIgnoreCase("edit"))
                e.setCompletions(Arrays.asList("setmessage", "getmessage", "setType", "getType"));
            else if (args.length == 4 && args[3].equalsIgnoreCase("setType"))
                e.setCompletions(EnumSet.allOf(JailsSystem.JailType.class).stream().map(v -> v.name().toLowerCase()).collect(Collectors.toList()));
        }
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
                case "edit":
                    if (args.length < 2)
                        sender.sendMessage(Lang.get(sender, "commands.jailsadmin.expressions.jail_name_expected"));
                    else if (args.length < 3)
                        sender.sendMessage(Lang.get(sender, "commands.jailsadmin.expressions.action_name_expected"));
                    else
                        switch (args[2]){
                            case "setmessage":
                                if (args.length < 4)
                                    sender.sendMessage(Lang.get(sender, "commands.jailsadmin.expressions.message_expected"));
                                else {
                                    JailsSystem.Status status = JailsSystem.setCustomMessage(args[1], Text.full(args, 3));

                                    if (status.success)
                                        response = Lang.get(sender, "commands.jailsadmin.expressions.setmessage.success");
                                    else if (status.code.equalsIgnoreCase("no exist"))
                                        response = Lang.get(sender, "commands.jailsadmin.expressions.no_exist");

                                    if (response != null)
                                        sender.sendMessage(response
                                                .replaceAll("%name%", args[1]));
                                }
                                break;
                            case "getmessage":
                                if (true) { // lol, si je n'avais pas fais ça, je le pouvais pas définir status dans la case setType.
                                    JailsSystem.Status status = JailsSystem.getCustomMessage(args[1]);

                                    if (status.success)
                                        response = Lang.get(sender, "commands.jailsadmin.expressions.getmessage.success")
                                                .replaceAll("%message%", status.data.toString());
                                    else if (status.code.equalsIgnoreCase("no exist"))
                                        response = Lang.get(sender, "commands.jailsadmin.expressions.no_exist");

                                    if (response != null)
                                        sender.sendMessage(response
                                                .replaceAll("%name%", args[1]));
                                }

                                break;
                            case "setType":
                                if (args.length < 4)
                                    sender.sendMessage(Lang.get(sender, "commands.jailsadmin.expressions.type_expected"));
                                else{
                                    if (EnumUtil.contains(Arrays.asList(JailsSystem.JailType.values()), args[3])) {
                                        JailsSystem.Status status = JailsSystem.setType(args[1], JailsSystem.JailType.valueOf(args[3].toUpperCase()));

                                        if (status.success)
                                            response = Lang.get(sender, "commands.jailsadmin.expressions.setType.success");
                                        else if (status.code.equalsIgnoreCase("no exist"))
                                            response = Lang.get(sender, "commands.jailsadmin.expressions.no_exist");
                                    } else
                                        response = Lang.get(sender, "commands.jailsadmin.expressions.setType.unknown_type");

                                    if (response != null)
                                        sender.sendMessage(response
                                                .replaceAll("%name%", args[1])
                                                .replaceAll("%type%", args[3]));
                                }
                                break;
                            case "getType":
                                JailsSystem.Status status = JailsSystem.getType(args[1]);

                                if (status.success)
                                    response = Lang.get(sender, "commands.jailsadmin.expressions.getType.success")
                                            .replaceAll("%type%", status.data.toString().toLowerCase());
                                else if (status.code.equalsIgnoreCase("no exist"))
                                    response = Lang.get(sender, "commands.jailsadmin.expressions.no_exist");
                                else if (status.code.equalsIgnoreCase("type undefined"))
                                    response = Lang.get(sender, "commands.jailsadmin.expressions.getType.undefined_type");

                                if (response != null)
                                    sender.sendMessage(response
                                            .replaceAll("%name%", args[1]));

                                break;


                        }
                    break;

                case "list":
                    String[] list = JailsSystem.getAllJails();
                    String noms = (list.length != 0) ? Text.full(list).trim().replaceAll(" ", ", ") : Lang.get(sender, "commands.jailsadmin.expressions.list.empty");

                    sender.sendMessage(Lang.get(sender, "commands.jailsadmin.expressions.list.show_list")
                            .replaceAll("%list%", noms));
                    break;

                default:
                    return false;

            }
            return true;
        }

        return false;
    }

}