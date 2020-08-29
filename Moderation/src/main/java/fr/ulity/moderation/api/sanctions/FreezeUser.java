package fr.ulity.moderation.api.sanctions;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core_v3.messaging.SocketClient;
import fr.ulity.moderation.api.UserSanctions;

import java.util.Date;
import java.util.HashMap;

public class FreezeUser {
    private UserSanctions userSanctions;
    private FlatFileSection active;

    public String staff;
    public String reason;
    public Date when;

    String playername;

    public FreezeUser (String playername) {
        this.playername = playername;
        userSanctions = new UserSanctions(playername);
        active = userSanctions.getSection("sanctions.freeze.active");
        refresh();
    }

    public boolean isFrozen () {
        if (userSanctions.contains("sanctions.freeze.active"))
            return active.getLong("expire") != 0;
        return false;
    }

    public void refresh () {
        this.staff = active.getString("staff");
        this.reason = active.getString("reason");
        this.when = new Date(active.getLong("when"));
    }

    public void unfreeze () {
        if (isFrozen()) {
            refresh();

            int next = userSanctions.singleLayerKeySet("sanctions.freeze.archived").size()+1;
            FlatFileSection archived = userSanctions.getSection("sanctions.freeze.archived." + next);
            archived.set("staff", this.staff);
            archived.set("reason", this.reason);
            archived.set("when", this.when.getTime());
            archived.set("expire", new Date().getTime());

            if (SocketClient.isEnabled()) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("player", this.playername);
                data.put("staff", this.staff);
                data.put("reason", this.reason);
                data.put("when", this.when);

                SocketClient.send("all", "unban", data);
            }

        }
    }

    public void freeze () {
        active.set("staff", this.staff);
        active.set("reason", this.reason);
        active.set("when", new Date().getTime());

        if (SocketClient.isEnabled()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("player", this.playername);
            data.put("staff", this.staff);
            data.put("reason", this.reason);
            data.put("when", this.when);

            SocketClient.send("all", "unban", data);
        }
    }

}