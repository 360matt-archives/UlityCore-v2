package fr.ulity.core.bukkit.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.Lang;
import fr.ulity.core.bukkit.MainBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * @author redsarow
 *
 * J'ai utilisé le template de redsarow pour le plugin UlityCore
 * Cette classe sert aussi d'example pour les Add-Ons développés par des bénévoles
 *
 */

public class UlityCoreCommand extends CommandManager {

    public UlityCoreCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "ulitycore");
        addDescription((Lang.defaultLang) == "fr" ? "Affiche les informations du plugin" : "Show the plugin's informations");
        addAliases("uc");
        addUsage("/ulitycore");
        addPermission(null);
        // addPermissionMessage(null);
        addOneTabbComplete(1, "ulitycore");// /commande test

        addOneTabbComplete(2, "lang");
        addOneTabbComplete(3, null, "fr", "lang");
        addOneTabbComplete(3, null, "en", "lang");

        addOneTabbComplete(2, "ulitycore.admin", "reload");

        registerCommand(commandMap);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("reload") || args[0].equals("rl")){
                if (sender.hasPermission("ulitycore.admin")){
                    MainBukkit.plugin.getPluginLoader().disablePlugin(MainBukkit.plugin);
                    MainBukkit.plugin.getPluginLoader().enablePlugin(MainBukkit.plugin);

                    sender.sendMessage(Lang.get(sender, "plugin.reloaded"));
                    return true;
                }
                else{
                    sender.sendMessage(Lang.get(sender, "plugin.no_perm"));
                    return true;
                }
            }
            if (args[0].equals("lang") || args[0].equals("langue")){
                if (sender.hasPermission("ulitycore.admin")){
                    if (args.length == 1)
                        sender.sendMessage(Lang.get("plugin.lang.actual"));
                    else{
                        Api.config.set("global.lang", args[1]);
                        Lang.reload();
                        sender.sendMessage(Lang.get(sender, "plugin.lang.reloaded"));
                    }
                    return true;
                }
                else{
                    sender.sendMessage(Lang.get(sender, "plugin.no_perm"));
                    return true;
                }
            }
        }

        if (Lang.defaultLang.equals("fr")) {
            sender.sendMessage("§bUlityCore §7est créé par §c360matt");
            sender.sendMessage("§7Version: §av" + MainBukkit.plugin.getDescription().getVersion());
            sender.sendMessage("§7Ce plugin est une librairie de fonctionnalités et extensible de commandes en tout genre grâce aux Add-Ons.");
        } else {
            sender.sendMessage("§bUlityCore §7is created by §c360matt");
            sender.sendMessage("§7Version: §av" + MainBukkit.plugin.getDescription().getVersion());
            sender.sendMessage("§7This plugin is a fonctionnality's library with extensible commands thanks to Add-Ons.");
        }

        return true;
    }
}