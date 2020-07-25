package fr.ulity.core.api;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
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



    public abstract static class Assisted extends CommandManager{
        public CommandSender sender;
        public Command cmd;
        public String[] args;

        public Arg arg;
        public Status status;

        public Assisted (JavaPlugin plugin, String name) { super(plugin,  name); }

        public abstract void exec (CommandSender sender, Command command, String label, String[] args);

        @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            this.sender = sender;
            this.cmd = command;
            this.args = args;
            this.status = Status.SUCCESS;

            this.arg = new Arg(sender, command, args);

            exec(sender, command, label, args);
            operateStatus();
            return true;
        }

        public void setStatus (Status status) { this.status = status; }
        public enum Status {
            SUCCESS,
            PLAYER_ONLY,
            SYNTAX,
            NOPERM,
            FAILED,
            STOP
        }
        private void operateStatus () {
            if (status.equals(Status.PLAYER_ONLY))
                sender.sendMessage(Lang.get(sender, "global.player_only"));
            else if (status.equals(Status.SYNTAX))
                sender.sendMessage(ChatColor.RED + getUsage());
            else if (status.equals(Status.NOPERM))
                sender.sendMessage(Lang.get(sender, "global.no_perm"));
        }

        public static class Arg {
            private final CommandSender sender;
            private final Command cmd;
            private final String[] args;

            public Arg (CommandSender sender, Command command, String[] args) {
                this.sender = sender;
                this.cmd = command;
                this.args = args;
            }

            public boolean is (int ind) { return args.length >= ind+1; }
            public boolean isNumber (int ind) {
                try {
                    if (is(ind)) {
                        Double.parseDouble(args[ind]);
                        return true;
                    }
                } catch (Exception ignored) { }
                return false;
            }
            public boolean isPlayer (int ind) { return is(ind) && Bukkit.getPlayer(args[ind]) != null; }
            public boolean isWorld (int ind) { return is(ind) && Bukkit.getWorld(args[ind]) != null; }
            public boolean isAlphaNumeric (int ind) { return is(ind) && StringUtils.isAlphanumeric(args[ind]); }

            public String get (int ind) { return (is(ind)) ? args[ind] : ""; }
            public long getLong (int ind) { return (is(ind)) ? Long.parseLong(args[ind]) : 0; }
            public Player getPlayer (int ind) { return (is(ind)) ? Bukkit.getPlayer(args[ind]) : null; }
            public World getWorld (int ind) { return (is(ind)) ? Bukkit.getWorld(args[ind]) : null; }

            public boolean require (int ind) {
                if (is(ind)) return true;
                else Lang.prepare("arg_needed.default").sendPlayer(sender);
                return false;
            }
            public boolean requirePlayer (int ind) {
                if (isPlayer(ind)) return true;
                else if (is(ind))
                    Lang.prepare("global.invalid_player")
                            .variable("player", get(ind))
                            .sendPlayer(sender);
                else
                    Lang.prepare("arg_needed.player").sendPlayer(sender);
                return false;
            }
            public boolean requireWorld (int ind) {
                if (isWorld(ind)) return true;
                else if (is(ind))
                    Lang.prepare("global.invalid_world")
                            .variable("world", get(ind))
                            .sendPlayer(sender);
                else
                    Lang.prepare("arg_needed.world").sendPlayer(sender);
                return false;
            }
            public boolean requirePlayerNoSelf (int ind) {
                if (requirePlayer(ind)) {
                    if (!getPlayer(ind).getName().equals(sender.getName())) return true;
                    else Lang.prepare("global.no_self").sendPlayer(sender);
                }
                return false;
            }
            public boolean requireNumber (int ind) {
                if (isNumber(ind)) return true;
                else Lang.prepare("arg_needed.number").sendPlayer(sender);
                return false;
            }
            public boolean inRange (int minimal, int maximal) {
                return args.length <= maximal && args.length >= minimal;
            }
            public boolean compare (String source, String... target) {
                return Arrays.stream(target).anyMatch(source::equalsIgnoreCase);
            }
            public boolean compare (int ind, String... target) {
                return Arrays.stream(target).anyMatch(get(ind)::equalsIgnoreCase);
            }

        }

        public boolean isPlayer () { return sender instanceof Player; }
        public Player getPlayer() { return (Player) sender; }
        public boolean requirePlayer () {
            if (isPlayer())
                return true;
            status = Status.PLAYER_ONLY;
            return false;
        }

        public boolean hasPermission (String permission) { return sender.hasPermission(permission); }
        public boolean requirePermission (String permission) {
            if (hasPermission(permission))
                return true;
            status = Status.NOPERM;
            return false;
        }


    }




    /**
     * @param plugin plugin responsable de cette commande.
     * @param name le nom de cette commande.
     */
    public CommandManager(T plugin, String name) {
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
        if (getUsage().equals("/" + getName())) setUsage(Lang.get("commands." + getName() + ".usage"));
        if (getDescription().equals("")) setDescription(Lang.get("commands." + getName() + ".description"));

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
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String command, String[] arg) {
        if (getPermission() != null && !commandSender.hasPermission(getPermission()))
            commandSender.sendMessage((getPermissionMessage() == null) ? Lang.get("plugin.no_perm") : getPermissionMessage());
        else
            onCommand(commandSender, this, command, arg);
        return true;
    }

    /**
     * @param sender sender (de bukkit)
     * @param alias aléas utilisé
     * @param args arguments de cette commande
     *
     * @return une liste des valeurs possibles
     */
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) {

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
    private static class TabCommand {

        private final int indice;
        private final String text;
        private final String permission;
        private final ArrayList<String> textAvant;

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