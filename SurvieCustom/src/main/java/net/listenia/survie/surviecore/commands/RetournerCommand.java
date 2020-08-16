package net.listenia.survie.surviecore.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RetournerCommand extends CommandManager.Assisted {
    public RetournerCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "retourner");
        addDescription("§ePermet de connaitre la position de sa précédente mort");
        addUsage("§eRécupérer la position: §7/retourner");

        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (requirePlayer()) {
            Player p = (Player) sender;

            if (Api.temp.contains("player." + sender.getName() + ".death_lastPosition")) {
                Location beforeHandle = (Location) Api.temp.get("player." + sender.getName() + ".death_lastPosition");

                if (p.getWorld().getName().equals("Spawn"))
                    p.sendMessage("§7Rooh t'es mort au spawn ! Fais un petit effort quand même !");
                else {
                    int locX = (int) Math.round((Math.random() * ((100 - -100) + 1)) + -100);
                    int locZ = (int) Math.round((Math.random() * ((100 - -100) + 1)) + -100);

                    Location loc = new Location(beforeHandle.getWorld(), beforeHandle.clone().getX() + locX, 0, beforeHandle.clone().getZ() + locZ);
                    loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 2);

                    p.teleport(loc);
                    p.sendMessage("§eTu as été téléporté dans un rayon de §6100 §eblocs.\n§eLa véritable position est: " + Math.round(beforeHandle.getX()) + " " + beforeHandle.getY() + " " + Math.round(beforeHandle.getZ()));
                }
            } else
                sender.sendMessage("§7Mais t'es pas encore mort ... Mdr !");
        }
    }
}