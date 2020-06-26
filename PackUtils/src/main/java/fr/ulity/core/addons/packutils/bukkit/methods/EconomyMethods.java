package fr.ulity.core.addons.packutils.bukkit.methods;

import fr.ulity.core.api.Config;
import fr.ulity.core.api.Data;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;

import java.util.List;

@SuppressWarnings("deprecation")
public class EconomyMethods extends AbstractEconomy {
    public static Data money = new Data("money", "addons/PackUtils/economy/");
    public static Config moneyConfig = new Config("config", "addons/PackUtils/economy/");

    @Override public boolean isEnabled() { return true; }

    @Override public String getName() { return "PackUtils"; }

    @Override public boolean hasBankSupport() { return true; }

    @Override public int fractionalDigits() { return 0; }

    @Override public String format(double amount) {
        return String.valueOf(amount);
    }

    @Override
    public String currencyNamePlural() {
        return moneyConfig.getOrSetDefault("name.plurial", "coins");
    }

    @Override
    public String currencyNameSingular() {
        return moneyConfig.getOrSetDefault("name.singular", "coin");
    }

    @Override
    public boolean hasAccount(String playerName) {
        return money.contains("player." + playerName);
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return money.contains("player." + playerName);
    }

    @Override
    public double getBalance(String playerName) {
        return money.getDouble("player." + playerName);
    }

    @Override
    public double getBalance(String playerName, String world) {
        return money.getDouble("player." + playerName);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return money.getDouble("player." + playerName) >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return money.getDouble("player." + playerName) >= amount;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        double newBalance = money.getDouble("player." + playerName) + amount;
        money.set("player." + playerName, newBalance);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "ma bite");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        double newBalance = money.getDouble("player." + playerName) - amount;
        money.set("player." + playerName, newBalance);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, "ma bite");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.SUCCESS, "ma bite");
    }

    @Override public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.SUCCESS, "ma bite");
    }

    @Override public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.SUCCESS, "ma bite");
    }

    @Override public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.SUCCESS, "ma bite");
    }

    @Override public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.SUCCESS, "ma bite");
    }

    @Override public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.SUCCESS, "ma bite");
    }

    @Override public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.SUCCESS, "ma bite");
    }

    @Override public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0D, 0D, EconomyResponse.ResponseType.SUCCESS, "ma bite");
    }

    @Override public List<String> getBanks() { return null; }

    @Override public boolean createPlayerAccount(String playerName) { return false; }

    @Override public boolean createPlayerAccount(String playerName, String worldName) { return false; }
}
