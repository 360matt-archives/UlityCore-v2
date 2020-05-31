package fr.ulity.moderation.bukkit.api;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.Config;
import fr.ulity.core.utils.Time;

import java.util.Date;

public class Mute {
    private static final Config mute = new Config("mute", "moderation");

    private final FlatFileSection playerMute;
    private final String playername;

    public String reason;
    public String expire_text;
    public String responsable;
    public long expire;


    public Mute (String playername) {
        this.playername = playername;
        playerMute = mute.getSection(playername);

        if (isMute()){
            reason = playerMute.getString("reason");
            responsable = playerMute.getString("responsable");

            if (!playerMute.get("expire").equals(0)) {
                expire = playerMute.getLong("expire");
                expire_text = new Time((int)((expire - new Date().getTime())/1000)).text;
            }
        }
    }

    public void mute () {
        playerMute.set("reason", reason);
        playerMute.set("responsable", responsable);
        playerMute.set("expire", expire);
    }

    public void unmute () {
        mute.remove(playername);
    }

    public boolean isMute () {
        if (mute.get(playername) != null){
            if (playerMute.getLong("expire") != 0 && playerMute.getLong("expire") <= new Date().getTime()) {
                mute.remove(playername);
                return false;
            }
            return true;
        }
        return false;
    }
}