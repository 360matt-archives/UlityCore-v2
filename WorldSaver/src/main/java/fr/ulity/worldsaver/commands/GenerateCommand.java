package fr.ulity.worldsaver.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.worldsaver.api.methods.save.CreateJson;
import fr.ulity.worldsaver.api.methods.Warner;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class GenerateCommand extends CommandManager {
    public GenerateCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "generate");
        addDescription("§etest");
        addUsage("§etest");

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Warner warner = new Warner(sender, Warner.Type.SAVE);

        World world = Bukkit.getWorld("world");
        CreateJson.generate("test", world, world.getLoadedChunks(), warner);


        sender.sendMessage("§eWola");
        return true;
    }

}