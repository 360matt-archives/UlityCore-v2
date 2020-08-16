package fr.ulity.core.addons.packutils.bukkit;

import fr.ulity.core.api.Data;

public abstract class StoredEconomy {
    public static StoredEconomy custom = new StoredEconomy() {
        public Data money = new Data("money", "addons/PackUtils/economy/");

        @Override
        public void set(String playername, double amount) { money.set("player." + playername, amount); }

        @Override
        public double get(String playername) { return money.getDouble("player." + playername); }

        @Override
        public boolean hasAccount(String playername) { return money.contains("player." + playername); }

        @Override
        public void clearAll() { money.clear(); }
    };



    public abstract void set (String playername, double amount);
    public abstract double get (String playername);
    public abstract boolean hasAccount (String playername);
    public abstract void clearAll ();

}
