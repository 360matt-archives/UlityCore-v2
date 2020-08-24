package hey.i360matt.truc.commands;

import fr.ulity.core_v3.modules.commandHandlers.CommandBukkit;
import fr.ulity.core_v3.modules.language.Lang;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Bubu extends CommandBukkit {
    public Bubu() {
        super("bubu");

        addTabbComplete(1, "test", "", "");
        addTabbComplete(1, "truc", new String[]{}, "");
    }

    @Override
    public void exec(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(Lang.get(sender, "coucou"));

        Lang.prepare("coucou")
                .sendPlayer(sender);

    }
}
