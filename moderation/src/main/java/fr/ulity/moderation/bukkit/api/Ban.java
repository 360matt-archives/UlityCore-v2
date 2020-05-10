package fr.ulity.moderation.bukkit.api;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core.api.Config;
import fr.ulity.core.utils.Time;

import java.util.Date;

public class Ban {
    private static final Config ban = new Config("ban", "moderation");

    private final FlatFileSection playerBan;
    private final String playername;

    public String reason;
    public String expire_text;
    public String responsable;
    public long timestamp;
    public long expire;


    public Ban (String playername) {
        this.playername = playername;
        playerBan = ban.getSection(playername);

        if (isBan()){
            reason = playerBan.getString("reason");
            responsable = playerBan.getString("responsable");
            timestamp = playerBan.getLong("timestamp");

            if (!playerBan.get("expire").equals(0)) {
                expire = playerBan.getLong("expire");
                expire_text = new Time((int)((expire - new Date().getTime())/1000)).text;
            }
        }
    }

    public void ban () {
        playerBan.set("reason", reason);
        playerBan.set("responsable", responsable);
        playerBan.set("expire", expire);
        playerBan.set("timestamp", timestamp);
    }

    public void unban () {
        ban.delete(playername);
    }

    public boolean isBan () {
        if (ban.get(playername) != null){
            if (playerBan.getLong("expire") != 0 && playerBan.getLong("expire") <= new Date().getTime()) {
                ban.delete(playername);
                return false;
            }
            return true;
        }
        return false;
    }
}