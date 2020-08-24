package fr.ulity.core.addons.packutils.api;

import fr.ulity.core_v3.Core;
import fr.ulity.core_v3.modules.datas.UserData;

public class UserEconomy {
    private UserData userData;
    private String server = Core.servername;

    public UserEconomy (String playername) {
        userData = new UserData(playername);
    }

    public UserEconomy (String playername, String servername) {
        userData = new UserData(playername);
        this.server = servername;
    }

    public double getAmount () { return userData.getDouble("economy." + server); }
    public void setAmount (double amount) { userData.set("economy." + server, amount); }

    public void addAmount (double amount) { userData.set("economy." + server, getAmount() + amount); }
    public void removeAmount (double amount) { userData.set("economy." + server, Math.min(0, getAmount() - amount)); }

    public boolean has (double amount) { return getAmount() >= amount; }

    public void clear () { userData.remove("economy." + server); }



}
