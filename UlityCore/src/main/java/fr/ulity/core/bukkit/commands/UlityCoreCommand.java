package fr.ulity.core.bukkit.commands;

import fr.ulity.core.api.Api;
import fr.ulity.core.api.CommandManager;
import fr.ulity.core.api.bukkit.InitializerBukkit;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.core.bukkit.MainBukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * @author redsarow
 *
 * J'ai utilisé le template de redsarow pour le plugin UlityCore
 * Cette classe sert aussi d'example pour les Add-Ons développés par des bénévoles
 *
 */

public class UlityCoreCommand extends CommandManager.Assisted {

    public UlityCoreCommand(CommandMap commandMap, JavaPlugin plugin) {
        super(plugin, "ulitycore");
        addDescription(
                LangBukkit.defaultLang.equals("fr")
                ? "Affiche les informations du plugin"
                : "Show the plugin's informations"
        );
        addAliases("uc");
        addUsage("/ulitycore");
        addPermission(null);

        addOneTabbComplete(0, null, "lang");
        addOneTabbComplete(0, "ulitycore.admin", "reload");
        addListTabbComplete(1, "ulitycore.admin", new String[]{"lang"}, "fr", "en");

        addListTabbComplete(1, "ulitycore.admin", new String[]{"reload", "lang"}, "");
        addListTabbComplete(2, "ulitycore.admin", new String[]{"fr", "en"}, "");

        registerCommand(commandMap);
    }

    @Override
    public void exec(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("reload") || args[0].equals("rl")){
                if (requirePermission("ulitycore.admin")){
                    MainBukkit.plugin.getPluginLoader().disablePlugin(MainBukkit.plugin);
                    MainBukkit.plugin.getPluginLoader().enablePlugin(MainBukkit.plugin);

                    int count = 0;
                    for (int i = InitializerBukkit.lesPlugins.size() - 1; i >= 0; i--) {
                        JavaPlugin x = InitializerBukkit.lesPlugins.get(i);
                        MainBukkit.plugin.getPluginLoader().disablePlugin(x);
                        HandlerList.unregisterAll(x);

                        MainBukkit.plugin.getPluginLoader().enablePlugin(x);
                        count++;
                    }
                    // je suis obligé de reverse loop, sinon cela me cause une erreur

                    LangBukkit.prepare("plugin.reloaded").sendPlayer(sender);
                    LangBukkit.prepare("plugin.addons_reloaded")
                            .variable("count", String.valueOf(count))
                            .sendPlayer(sender);

                    return;
                }
            }
            if (args[0].equals("lang") || args[0].equals("langue")){
                if (requirePermission("ulitycore.admin")){
                    if (args.length == 1)
                        sender.sendMessage(LangBukkit.get("plugin.lang.actual"));
                    else{
                        if (Files.exists(Paths.get(Api.corePath + "/languages/" + args[1] + ".yml"))){
                            Api.config.set("global.lang", args[1]);
                            try {
                                LangBukkit.reload();
                                LangBukkit.prepare("plugin.lang.reloaded").sendPlayer(sender);
                            } catch (IOException | URISyntaxException e) {
                                LangBukkit.prepare("plugin.lang.fail_reload").sendPlayer(sender);
                                e.printStackTrace();
                            }
                        }
                        else
                            LangBukkit.prepare("plugin.commands.lang.lang_unknown")
                                    .variable("lang", args[1])
                                    .sendPlayer(sender);
                    }
                    return;
                }
            }
        }


        if (status.equals(Status.SUCCESS)) {
            if (LangBukkit.defaultLang.equals("fr")) {
                sender.sendMessage("§bUlityCore §7est créé par §c360matt");
                sender.sendMessage("§7Version: §av" + MainBukkit.plugin.getDescription().getVersion());
                sender.sendMessage("§7Ce plugin est une librairie de fonctionnalités et extensible de commandes en tout genre grâce aux Add-Ons.");
            } else {
                sender.sendMessage("§bUlityCore §7is created by §c360matt");
                sender.sendMessage("§7Version: §av" + MainBukkit.plugin.getDescription().getVersion());
                sender.sendMessage("§7This plugin is a fonctionnality's library with extensible commands thanks to Add-Ons.");
            }
        }
    }
}