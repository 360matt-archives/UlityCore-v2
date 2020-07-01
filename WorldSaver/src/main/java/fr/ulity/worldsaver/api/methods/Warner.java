package fr.ulity.worldsaver.api.methods;

import org.bukkit.command.CommandSender;

public class Warner {
    public CommandSender sender;
    public String msg;
    public int delay;

    public Warner (CommandSender sender, String msg, int delay) {
        this.sender = sender;
        this.msg = msg;
        this.delay = delay;
    }
}