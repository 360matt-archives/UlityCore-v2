package fr.ulity.worldsaver.api.methods;

import org.bukkit.command.CommandSender;

public class Warner {
    public enum Type {
        SAVE,
        RESTORE
    }

    public CommandSender sender;
    public Type type;

    public String msgProgress;
    public String msgFinished;
    public int delay;

    public Warner (CommandSender sender, Type type) {
        this.sender = sender;
        this.type = type;

        String defaultMsgProgressRestore = "§eProgression: §6%percent%% §e: §e( §c%finishedChunk% §e/ §c%totalChunk% Chunks §e)";
        String defaultMsgProgressSave = "§eProgression: §6%percent%% §e: §c%finishedVertical% §e/ §c%totalVertical% §e( §6%finishedChunk% §e/ §6%totalChunk% Chunks §e)";

        String defaultMsgFinishedRestore = "§eProgression: §6100% : §eRestoration terminée !";
        String defaultMsgFinishedSave = "§eProgression: §6100% : §eenregistrement terminée !";

        this.msgProgress = (type.equals(Type.SAVE)) ? defaultMsgProgressSave : defaultMsgProgressRestore;
        this.msgFinished = (type.equals(Type.SAVE)) ? defaultMsgFinishedSave : defaultMsgFinishedRestore;
        this.delay = (type.equals(Type.SAVE)) ? 3 : 2;
    }

    public void setProgressMsg (String msg) { this.msgProgress = msg; }
    public void setFinishedMsg (String msg) { this.msgFinished = msg; }
    public void setDelay (int delay) { this.delay = delay; }

}