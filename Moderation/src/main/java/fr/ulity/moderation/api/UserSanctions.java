package fr.ulity.moderation.api;

import fr.ulity.core_v3.modules.datas.UserData;
import fr.ulity.moderation.api.sanctions.BanUser;
import fr.ulity.moderation.api.sanctions.FreezeUser;
import fr.ulity.moderation.api.sanctions.MuteUser;

public class UserSanctions extends UserData {
    private String playername;

    public UserSanctions(String playername) {
        super(playername);
        this.playername = playername;
    }

    public BanUser getBan () { return new BanUser(playername); }
    public MuteUser getMute () { return new MuteUser(playername); }
    public FreezeUser getFreeze () { return new FreezeUser(playername); }

}
