package fr.ulity.moderation.api.sanctions;

import de.leonhard.storage.sections.FlatFileSection;
import fr.ulity.core_v3.messaging.SocketClient;
import fr.ulity.core_v3.utils.Time;
import fr.ulity.moderation.api.UserSanctions;

import java.util.Date;
import java.util.HashMap;

public class MuteUser {
    private UserSanctions userSanctions;
    private FlatFileSection active;

    public String staff;
    public String reason;
    public Date when;
    public Date expire;
    public Time left;

    String playername;

    public MuteUser (String playername) {
        this.playername = playername;
        userSanctions = new UserSanctions(playername);
        active = userSanctions.getSection("sanctions.mute.active");
        refresh();
    }

    public boolean isMuted () {
        refresh();
        if (userSanctions.contains("sanctions.mute.active"))
            return getLeft().seconds != 0;
        return false;
    }

    public void refresh () {
        this.staff = active.getString("staff");
        this.reason = active.getString("reason");
        this.when = new Date(active.getLong("when"));
        this.expire = new Date(active.getLong("expire"));
        this.left = new Time((int) (new Date().getTime() - this.expire.getTime()));
    }

    public boolean neverExpire () {
        return this.expire.equals(new Date(0));
    }

    public Time getLeft () {
        refresh();
        return this.left;
    }

    public void unmute () {
        if (isMuted()) {
            refresh();

            int next = userSanctions.singleLayerKeySet("sanctions.mute.archived").size()+1;
            FlatFileSection archived = userSanctions.getSection("sanctions.mute.archived." + next);
            archived.set("staff", this.staff);
            archived.set("reason", this.reason);
            archived.set("when", this.when.getTime());
            archived.set("expire", this.expire.getTime());
            archived.set("left", this.left.seconds);

            if (SocketClient.isEnabled()) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("player", this.playername);
                data.put("staff", this.staff);
                data.put("reason", this.reason);
                data.put("when", this.when);
                data.put("expire", this.expire);
                data.put("left", this.left);

                SocketClient.send("all", "unmute", data);
            }

        }
    }

    public void mute () {
        active.set("staff", this.staff);
        active.set("reason", this.reason);
        active.set("when", new Date().getTime());
        active.set("expire", this.expire.getTime());
        active.set("left", this.left.seconds);

        if (SocketClient.isEnabled()) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("player", playername);
            data.put("staff", staff);
            data.put("reason", reason);
            data.put("when", when);
            data.put("expire", expire);

            SocketClient.send("all", "mute", data);
        }

    }

}