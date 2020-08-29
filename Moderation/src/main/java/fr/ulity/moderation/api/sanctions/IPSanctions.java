package fr.ulity.moderation.api;

import fr.ulity.core_v3.modules.datas.IPData;
import fr.ulity.core_v3.modules.datas.UserData;
import fr.ulity.moderation.api.sanctions.BanIP;
import fr.ulity.moderation.api.sanctions.BanUser;
import fr.ulity.moderation.api.sanctions.FreezeUser;
import fr.ulity.moderation.api.sanctions.MuteUser;

public class IPSanctions extends IPData {
    private String ip;

    public IPSanctions(String ip) {
        super(ip);
        this.ip = ip;
    }

    public BanIP getBan () { return new BanIP(ip); }

}
