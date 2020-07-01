package fr.ulity.worldsaver.commands;

import fr.ulity.core.api.CommandManager;
import fr.ulity.worldsaver.api.methods.save.CreateJson;
import fr.ulity.worldsaver.api.methods.Warner;
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

        Warner warner = new Warner(sender, "§eProgression: §6%percent%% §e: " +
                "§c%finishedVertical% §e/ §c%totalVertical% §e( §6%finishedChunk% §e/ §6%totalChunk% Chunks §e)", 3);



        CreateJson.generate("test", "world", warner);
        sender.sendMessage("§eWola");
        return true;
    }

}