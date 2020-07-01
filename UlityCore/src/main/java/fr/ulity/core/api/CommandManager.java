package fr.ulity.core.api;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author redsarow
 *
 * J'ai utilisé le code de redsarow pour le plugin UlityCore
 * Merci à lui cela m'aide beaucoup
 *
 * le lien du code est consultable ici: https://gist.github.com/redsarow/46a9eb30991bf6007508f72aba7da89f
 *
 */
public abstract class CommandManager<T extends JavaPlugin> extends Command implements CommandExecutor, PluginIdentifiableCommand {

    private final T plugin;
    private final boolean register = false;
    private final HashMap<Integer, ArrayList<TabCommand>> tabComplete;


    /**
     * @param plugin plugin responsable de cette commande.
     * @param name le nom de cette commande.
     */
    protected CommandManager(T plugin, String name) {
        super(name);

        assert plugin != null;
        assert name.length() > 0;

        setLabel(name);
        this.plugin = plugin;
        tabComplete = new HashMap<>();
    }


    //<editor-fold desc="add">
    /**
     * @param description description de la commande
     *
     * @return CommandManager, instance de cette classe
     */
    protected CommandManager addDescription(String description) {
        if (register || description != null)
            setDescription(description);
        return this;
    }

    /**
     * @param use syntaxe de cette commande (ex: /myCmd [val1]
     *
     * @return CommandManager, instance de cette classe
     */
    protected CommandManager addUsage(String use) {
        if (register || use != null)
            setUsage(use);
        return this;
    }

    /**
     * @param aliases liste d'aléas de cette commande.
     *
     * @return CommandManager, instance de cette classe
     */
    protected CommandManager addAliases(String... aliases) {
        if (aliases != null && (register || aliases.length > 0))
            setAliases(Arrays.stream(aliases).collect(Collectors.toList()));
        return this;
    }

    //<editor-fold desc="TabbComplete">
    /**
     * Ajouter un argument à un index avec sa permission ainsi que le mot qui le précède
     *
     * @param indice     index where the argument is in the command. /myCmd is at the index -1, so
     *                   /myCmd index0 index1 ...
     * @param permission permission to add (may be null)
     * @param arg        word to add
     * @param beforeText text preceding the argument (may be null)
     *
     * @return CommandManager, instance de cette classe
     */
    protected CommandManager addOneTabbComplete(int indice, String permission, String arg, String... beforeText) {
        if (arg != null && indice >= 0) {
            if (tabComplete.containsKey(indice)) {
                tabComplete.get(indice).add(new TabCommand(indice, arg, permission, beforeText));
            } else {
                ArrayList<TabCommand> tabCommands = new ArrayList<>();
                tabCommands.add(new TabCommand(indice, arg, permission, beforeText));
                tabComplete.put(indice, tabCommands);
            }
        }
        return this;
    }

    //<editor-fold desc="TabbComplete">
    /**
     * Adds an argument to an index with permission and the words before
     *
     * @param indice     index de la position de l'arguments dans la syntaxe. /myCmd est à l'index -1, donc
     *                   /myCmd index0 index1 ...
     * @param arg        word to add
     *
     * @return CommandManager, instance de cette classe
     */
    protected CommandManager addOneTabbComplete(int indice, String arg) {
        if (arg != null && indice >= 0) {
            if (tabComplete.containsKey(indice)) {
                tabComplete.get(indice).add(new TabCommand(indice, arg));
            } else {
                ArrayList<TabCommand> tabCommands = new ArrayList<>();
                tabCommands.add(new TabCommand(indice, arg));
                tabComplete.put(indice, tabCommands);
            }
        }
        return this;
    }


    /**
     * Ajouter plusieurs arguments à cet index
     *
     * @param indice     index de la position de l'arguments dans la syntaxe. /myCmd est à l'index -1, donc
     *                   /myCmd index0 index1 ...
     * @param permission permission à ajouter (peut être null)
     * @param args        array de mots à ajouter
     * @param beforeText texte précédant cet argument (peut être null)
     *
     * @return CommandManager, instance de cette classe
     */
    public CommandManager addArrayTabbComplete (int indice, String permission, String[] beforeText, String[] args) {
        if (args != null && args.length > 0 && indice >= 0) {
            if (tabComplete.containsKey(indice)) {
                tabComplete.get(indice).addAll(Arrays.stream(args).collect(
                        ArrayList::new,
                        (tabCommands, s) -> tabCommands.add(new TabCommand(indice, s, permission, beforeText)),
                        ArrayList::addAll));
            } else {
                tabComplete.put(indice, Arrays.stream(args).collect(
                        ArrayList::new,
                        (tabCommands, s) -> tabCommands.add(new TabCommand(indice, s, permission, beforeText)),
                        ArrayList::addAll)
                );
            }
        }
        return this;
    }


    /**
     * Ajouter plusieurs arguments à cet index
     *
     * @param indice     index de la position de l'arguments dans la syntaxe. /myCmd est à l'index -1, donc
     *                   /myCmd index0 index1 ...
     * @param permission permission à ajouter (peut être null)
     * @param arg        mot à ajouter, un par argument
     * @param beforeText texte précédant cet argument (peut être null)
     *
     * @return CommandManager, instance de cette classe
     */
    protected CommandManager addListTabbComplete(int indice, String permission, String[] beforeText, String... arg) {
        return addArrayTabbComplete(indice, permission, beforeText, arg);
    }


    /**
     * Ajouter plusieurs arguments à cet index
     *
     * @param indice index de la position des arguments dans la syntaxe. /myCmd est à l'index -1, donc
     *               /myCmd index0 index1 ...
     * @param arg    mots à ajouter
     *
     * @return CommandManager, instance de cette classe
     */
    protected CommandManager addListTabbComplete(int indice, String... arg) {
        if (arg != null && arg.length > 0 && indice >= 0) {
            addListTabbComplete(indice, null, null, arg);
        }
        return this;
    }
    //</editor-fold>

    /**
     * ajouter une permission à cette commande
     *
     * @param permission permission à ajouter (peut être null)
     *
     * @return CommandManager, instance de cette classe
     */
    protected CommandManager addPermission(String permission) {
        if (register || permission != null)
            setPermission(permission);
        return this;
    }

    /**
     * @param permissionMessage envoie un message si le joueur (sender) n'a pas la permission
     *
     * @return CommandManager, instance de cette classe
     */
    protected CommandManager addPermissionMessage(String permissionMessage) {
        if (register || permissionMessage != null)
            setPermissionMessage(permissionMessage);
        return this;
    }
    //</editor-fold>

    /**
     * /!\ à executer à la fin /!\ pour enregistrer la commande.
     *
     * @param commandMap via:<br/>
     *                   <code>
     *                   Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");<br/>
     *                   f.setAccessible(true);<br/>
     *                   CommandMap commandMap = (CommandMap) f.get(Bukkit.getServer());
     *                   </code>
     *
     * @return vrai si la commande a bien été enregistrée
     */
    protected boolean registerCommand(CommandMap commandMap) {
        return !register && commandMap.register("", this);
    }

    //<editor-fold desc="get">
    /**
     * @return plugin responsible for the command
     */
    @Override
    public @NotNull T getPlugin() {
        return this.plugin;
    }

    /**
     * @return tabComplete
     */
    public HashMap<Integer, ArrayList<TabCommand>> getTabComplete() {
        return tabComplete;
    }
    //</editor-fold>


    //<editor-fold desc="Override">
    /**
     * @param commandSender sender
     * @param command       commande
     * @param arg           argument de cette commande
     *
     * @return vrai si okay, autrement le résultat serait false
     */
    @Override
    public boolean execute(CommandSender commandSender, String command, String[] arg) {
        if (getPermission() != null) {
            if (!commandSender.hasPermission(getPermission())) {
                commandSender.sendMessage((getPermissionMessage() == null) ? Lang.get("plugin.no_perm") : getPermissionMessage());
                return false;
            }
        }
        if (onCommand(commandSender, this, command, arg))
            return true;
        commandSender.sendMessage(ChatColor.RED + getUsage());
        return false;

    }

    /**
     * @param sender sender (de bukkit)
     * @param alias aléas utilisé
     * @param args arguments de cette commande
     *
     * @return une liste des valeurs possibles
     */
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {

        int indice = args.length - 1;

        if ((getPermission() != null && !sender.hasPermission(getPermission())) || tabComplete.size() == 0 || !tabComplete.containsKey(indice))
            return super.tabComplete(sender, alias, args);

        ArrayList<String> list = tabComplete.get(indice).stream().filter(tabCommand ->
                (tabCommand.getTextAvant() == null || tabCommand.getTextAvant().contains(args[indice - 1])) &&
                        (tabCommand.getPermission() == null || sender.hasPermission(tabCommand.getPermission())) &&
                        (tabCommand.getText().startsWith(args[indice]))
        ).map(TabCommand::getText).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        return list.size() < 1 ? super.tabComplete(sender, alias, args) : list;

    }
    //</editor-fold>

    //<editor-fold desc="class TabCommand">
    private class TabCommand {

        private int indice;
        private String text;
        private String permission;
        private ArrayList<String> textAvant;

        private TabCommand(int indice, String text, String permission, String... textAvant) {
            this.indice = indice;
            this.text = text;
            this.permission = permission;
            if (textAvant == null || textAvant.length < 1) {
                this.textAvant = null;
            } else {
                this.textAvant = Arrays.stream(textAvant).collect(ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll);
            }
        }

        private TabCommand(int indice, String text, String permission) {
            this(indice, text, permission, "");
        }

        private TabCommand(int indice, String text, String[] textAvant) {
            this(indice, text, null, textAvant);
        }

        private TabCommand(int indice, String text) {
            this(indice, text, null, "");
        }

        //<editor-fold desc="get&set">
        public String getText() {
            return text;
        }

        public int getIndice() {
            return indice;
        }

        public String getPermission() {
            return permission;
        }

        public ArrayList<String> getTextAvant() {
            return textAvant;
        }
        //</editor-fold>

    }
    //</editor-fold>
}