package fr.ulity.customcommands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.bukkit.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class InstanceCommand extends CommandManager.Assisted {
    public PassInfoCommand passed;

    public InstanceCommand (PassInfoCommand passed) {
        super(Main.plugin, passed.name);
        this.passed = passed;

        if (passed.description != null && !passed.description.equals(""))
            setDescription(passed.description);
        if (passed.usage != null && !passed.usage.equals(""))
            setUsage(passed.usage);
        if (passed.permission != null && !passed.permission.equals(""))
            setPermission(passed.permission);
        if (passed.permissionMessage != null && !passed.permissionMessage.equals(""))
            setPermissionMessage(passed.permissionMessage);


        if (passed.name != null && !passed.name.equals(""))
            registerCommand(Api.getCommandMap());
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        switch (passed.type) {
            case "proxy":
                Bukkit.getServer().dispatchCommand(sender, passed.command);
                break;
            case "message":
                sender.sendMessage(passed.text);
                break;
            case "broadcast":
                Bukkit.broadcastMessage(passed.text);
                break;
        }
    }
}
