package fr.ulity.claims.commands;



import com.github.yannicklamprecht.worldborder.api.BorderAPI;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import de.leonhard.storage.shaded.jetbrains.annotations.NotNull;
import fr.ulity.claims.ClaimSystem;
import fr.ulity.claims.MainClaimBukkit;
import fr.ulity.core.api.CommandManager;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;


public class ZoneCommand  extends CommandManager {
    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    public ZoneCommand(CommandMap commandMap, JavaPlugin plugin) {

        super(plugin, "zone");
        addDescription("Commande général du Plugin zone");
        addOneTabbComplete(-1, "zone");

        addListTabbComplete(0, "claim", "unclaim", "unclaimall", "trust", "untrust", "untrustall", "show", "info");
        addListTabbComplete(1, null, new String[]{"unclaimall", "untrustall"}, "confirmer", "annuler");
        addListTabbComplete(1, null, new String[]{"claim", "unclaim", "trust", "untrust", "show", "info"}, "");

        registerCommand(commandMap);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String msg, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cLa console ne peut pas executer cette commande !");
        } else {
            Player p = (Player) sender;

            if (args.length >= 1) {

                if (args[0].equalsIgnoreCase("claim")) {
                    ClaimSystem.Status result = ClaimSystem.create((Player) sender);
                    if (result.success)
                        sender.sendMessage("§6Wix§eClaim §6» §eVous avez claim cette §dzone §e!");
                    else {
                        switch (result.code) {
                            case "limit":
                                sender.sendMessage("§6Wix§eClaim §6» §eVous avez §catteint §ela limite de §dzones §e!§7(§c" + result.data + "§7)");
                                break;
                            case "already exist":
                                sender.sendMessage("§6Wix§eClaim §6» §eVous avez §cdéjà §eclaim cette §dzone §e!");
                                break;
                            case "already taked":
                                sender.sendMessage("§6Wix§eClaim §6» §eUn joueur a §cdéjà §eclaim cette §dzone §e!");
                                break;
                            case "claim near":
                                sender.sendMessage("§6Wix§eClaim §6» §eVous ne §cpouvez §epas claim à proximité d'un §ajoueur §e!");
                                break;
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("unclaim")) {
                    ClaimSystem.Status result = ClaimSystem.remove((Player) sender);
                    if (result.success)
                        sender.sendMessage("§6Wix§eClaim §6» §eVous avez §cunclaim §ecette §dzone §e!");
                    else {
                        switch (result.code) {
                            case "no ownership":
                                sender.sendMessage("§6Wix§eClaim §6» §eCette §dzone §ene vous §cappartient §epas !");
                                break;
                            case "no exist":
                                sender.sendMessage("§6Wix§eClaim §6» §eCette §dzone §eest §adéjà §elibre !");
                                break;
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("unclaimall")) {
                    if (args.length == 1) {
                        // Afficher le JSON pour confirmer ou annuler
                        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a("[\"\",{\"text\":\"Wix\",\"color\":\"gold\"},{\"text\":\"Claim\",\"color\":\"yellow\"},{\"text\":\" »\",\"color\":\"gold\"},{\"text\":\" Êtes vous sûr de\",\"color\":\"yellow\"},{\"text\":\" supprimer\",\"color\":\"red\"},{\"text\":\" tous \",\"color\":\"yellow\"},{\"text\":\"vos claims \",\"color\":\"light_purple\"},{\"text\":\"?\",\"color\":\"yellow\"},{\"text\":\" (\",\"color\":\"gray\"},{\"text\":\"\\u2714\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/zone unclaimall confirmer\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§aAccepter\"}},{\"text\":\" \\u258d \",\"bold\":true,\"color\":\"dark_gray\"},{\"text\":\"\\u2716\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/zone unclaimall annuler\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§cAnnuler\"}},{\"text\":\")\",\"color\":\"gray\"}]");
                        PacketPlayOutChat chat = new PacketPlayOutChat(comp);
                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(chat);
                        return true;
                    } else if (args[1].equalsIgnoreCase("confirmer")) {
                        ClaimSystem.Status result = ClaimSystem.removeAllOfPlayer((Player) sender);
                        if (result.success)
                            sender.sendMessage("§6Wix§eClaim §6» §eVous avez §csupprimées §etoutes vos §dzones §e!§7(§c" + result.data + "§7)");
                        else if (result.code.equals("none"))
                            sender.sendMessage("§6Wix§eClaim §6» §eVous n'avez §caucune §dzone §e!");
                        return true;
                    } else if (args[1].equalsIgnoreCase("annuler")) {
                        sender.sendMessage("§6Wix§eClaim §6» §eOperation §cannulée §e!");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("trust")) {
                    if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null)
                            sender.sendMessage("§6Wix§eClaim §6» §ele joueur §b" + args[1] + " §en'est pas connecté sur le serveur");
                        else {
                            ClaimSystem.Status result = ClaimSystem.addTrust((Player) sender, target.getName());
                            if (result.success)
                                sender.sendMessage("§6Wix§eClaim §6» §d" + target.getName() + " §eest maintenant §aautorisé §eà éditer cette §dzone §e!");
                            else {
                                switch (result.code) {
                                    case "no ownership":
                                        sender.sendMessage("§6Wix§eClaim §6» §eVous ne §cpossèdez §epas cette §dzone §e!");
                                        break;
                                    case "no exist":
                                        sender.sendMessage("§6Wix§eClaim §6» §eVous ne §cpossèdez §epas cette §dzone §e!");
                                        break;
                                    case "himself":
                                        sender.sendMessage("§6Wix§eClaim §6» §eVous êtes §apropriétaire §ede cette §dzone §e!");
                                        break;
                                    case "already trusted":
                                        sender.sendMessage("§6Wix§eClaim §6» §eCe §djoueur §efait §cdéjà §eparti de vos membres !");
                                        break;
                                }
                            }
                        }
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("untrust")) {
                    if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target == null)
                            sender.sendMessage("§6Wix§eClaim §6» §ele joueur §b" + args[1] + " §en'est pas connecté sur le serveur");
                        else {
                            ClaimSystem.Status result = ClaimSystem.removeTrust((Player) sender, target.getName());
                            if (result.success)
                                sender.sendMessage("§6Wix§eClaim §6» §d" + target.getName() + " §en'est plus §cautorisé §eà éditer cette §dzone §e!");
                            else {
                                switch (result.code) {
                                    case "no ownership":
                                        sender.sendMessage("§6Wix§eClaim §6» §eVous ne §cpossèdez §epas cette §dzone §e!");
                                        break;
                                    case "no exist":
                                        sender.sendMessage("§6Wix§eClaim §6»§e Vous ne §cpossèdez §epas cette §dzone §e!");
                                        break;
                                    case "himself":
                                        sender.sendMessage("§6Wix§eClaim §6» §eVous êtes §apropriétaire §ede cette §dzone §e!");
                                        break;
                                    case "already trusted":
                                        sender.sendMessage("§6Wix§eClaim §6»§e Ce §djoueur §efait §cdéjà §eparti de vos membres !");
                                        break;
                                }
                            }
                        }
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("untrustall")) {
                    if (args.length == 1) {
                        // Afficher le JSON pour confirmer ou annuler
                        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a("[\"\",{\"text\":\"Wix\",\"color\":\"gold\"},{\"text\":\"Claim\",\"color\":\"yellow\"},{\"text\":\" »\",\"color\":\"gold\"},{\"text\":\" Êtes vous sûr d'\",\"color\":\"yellow\"},{\"text\":\"enlever \",\"color\":\"red\"},{\"text\":\"tous \",\"color\":\"yellow\"},{\"text\":\"vos trusts \",\"color\":\"light_purple\"},{\"text\":\"?\",\"color\":\"yellow\"},{\"text\":\" (\",\"color\":\"gray\"},{\"text\":\"\\u2714\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/zone untrustall confirmer\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§aAccepter\"}},{\"text\":\" \\u258d \",\"bold\":true,\"color\":\"dark_gray\"},{\"text\":\"\\u2716\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/zone untrustall annuler\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§cRefuser\"}},{\"text\":\")\",\"color\":\"gray\"}]");
                        PacketPlayOutChat chat = new PacketPlayOutChat(comp);
                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(chat);
                        return true;
                    } else if (args[1].equalsIgnoreCase("confirmer")) {
                        ClaimSystem.Status result = ClaimSystem.removeAllTrust((Player) sender);
                        if (result.success)
                            sender.sendMessage("§6Wix§eClaim §6» §eVous avez §csupprimé " + result.data + "§emembre(s) de votre §dzone §e!");
                        else {
                            switch (result.code) {
                                case "no ownership":
                                    sender.sendMessage("§6Wix§eClaim §6» §eCette §dzone §ene vous §cappartient §epas !");
                                    break;
                                case "no exist":
                                    sender.sendMessage("§6Wix§eClaim §6»§e Vous ne §cpossèdez §epas cette §dzone §e!");
                                    break;
                                case "none":
                                    sender.sendMessage("§6Wix§eClaim §6»§e Vous avez §caucun membre §esur votre §dzone §e!");
                                    break;
                            }
                        }
                        return true;
                    } else if (args[1].equalsIgnoreCase("annuler")) {
                        sender.sendMessage("§6Wix§eClaim §6» §eOperation §cannulée §e!");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("show")) {
                    if (!Bukkit.getPluginManager().isPluginEnabled("WorldBorderAPI")){
                        sender.sendMessage("§6Wix§eClaim §6» §esoucis technique, le §5show §eest §cindisponible !");
                        return true;
                    }

                    WorldBorderApi worldBorderAPI = BorderAPI.getApi();
                    ClaimSystem.Status result = ClaimSystem.canGetInfo((Player) sender);

                    if (result.success) {
                        if (ClaimSystem.getOwner(((Player) sender)).data.equals(sender.getName())
                                || ((ArrayList) ClaimSystem.getTrusts((Player) sender).data).contains(sender.getName())) {

                            if (!MainClaimBukkit.worldBorderShowed.containsKey(sender.getName())) {
                                worldBorderAPI.setBorder((Player) sender, 16, ((Player) sender).getLocation().getChunk().getBlock(8, 1, 8).getLocation());
                                MainClaimBukkit.worldBorderShowed.put(sender.getName(), true);
                                sender.sendMessage("§6Wix§eClaim §6» §eVous avez §activé §el'affichage la §dbordure §e!");
                            } else if (MainClaimBukkit.worldBorderShowed.get(sender.getName())) {
                                worldBorderAPI.resetWorldBorderToGlobal((Player) sender);
                                MainClaimBukkit.worldBorderShowed.put(sender.getName(), false);
                                sender.sendMessage("§6Wix§eClaim §6» §eVous avez §cdésactivé §el'affichage de la §dbordure §e!");
                            } else {
                                worldBorderAPI.setBorder((Player) sender, 16, ((Player) sender).getLocation().getChunk().getBlock(8, 1, 8).getLocation());
                                MainClaimBukkit.worldBorderShowed.put(sender.getName(), true);
                                sender.sendMessage("§6Wix§eClaim §6» §eVous avez §activé §el'affichage la §dbordure §e!");
                            }
                        }
                    } else
                        sender.sendMessage("§6Wix§eClaim §6» §eVous ne §cpossèdez §epas cette §dzone §e!");
                    return true;
                } else if (args[0].equalsIgnoreCase("info")) {
                    ClaimSystem.Status result = ClaimSystem.canGetInfo((Player) sender);
                    if (!result.success)
                        sender.sendMessage("§6Wix§eClaim §6» §eCette §dzonne §en'appartient à §cpersonne §e!");
                    else {
                        String owner = (String) ClaimSystem.getOwner((Player) sender).data;
                        String trusts = ClaimSystem.getTrusts((Player) sender).data.toString()
                                .replaceAll("\\[]", "Aucun(s) joueur(s)")
                                .replaceAll("\\[", "")
                                .replaceAll("]", "");

                        sender.sendMessage("§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-");
                        sender.sendMessage("");
                        sender.sendMessage("§6» §ePropriétaire: §d" + owner);
                        sender.sendMessage("§6» §eJoueur(s) Ajouté(s): §a" + trusts);
                        sender.sendMessage("");
                        sender.sendMessage("§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-");
                    }
                    return true;
                }
            }

            p.sendMessage("§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-");
            p.sendMessage("");
            p.sendMessage("§6» §eAfficher le menu §dd'aide ! §7(/zone)");
            p.sendMessage("§6» §eClaim une §dzone §ede §a16x16! §7(/zone claim)");
            p.sendMessage("§6» §eUnClaim la §dzone §eque vous êtes à §al'intérieur §e! §7(/zone unclaim)");
            p.sendMessage("§6» §eUnClaimAll toutes vos §dzones §e! §7(/zone unclaimall)");
            p.sendMessage("§6» §eAjouter un §ajoueur §eà votre §dzone §e! §7(/zone trust)");
            p.sendMessage("§6» §eSupprimer un §ajoueur §ede votre §dzone §e! §7(/zone untrust)");
            p.sendMessage("§6» §eSupprimer tous les §ajoueurs §ede votre §dzone §e! §7(/zone untrustall)");
            p.sendMessage("§6» §eToggle votre show §dzone §e! §7(/zone show)");
            p.sendMessage("§6» §eInformation sur la §dzone §eactuelle ! §7(/zone info)");
            p.sendMessage("");
            p.sendMessage("§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-§e§m-§6§m-");
        }


        return true;
    }
}
