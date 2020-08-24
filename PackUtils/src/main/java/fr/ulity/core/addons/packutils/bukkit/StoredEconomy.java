package fr.ulity.core.addons.packutils.bukkit;


import fr.ulity.core.addons.packutils.api.UserEconomy;

public abstract class StoredEconomy {
    public static StoredEconomy custom = new StoredEconomy() {

        @Override
        public void set(String playername, double amount) {
            new UserEconomy(playername).setAmount(amount);
        }

        @Override
        public double get(String playername) {
            return new UserEconomy(playername).getAmount();
        }

        @Override
        public boolean hasAccount(String playername) {
            return true;
        }

        @Override
        public void clearAll() {

        }
    };



    public abstract void set (String playername, double amount);
    public abstract double get (String playername);
    public abstract boolean hasAccount (String playername);
    public abstract void clearAll ();

}
