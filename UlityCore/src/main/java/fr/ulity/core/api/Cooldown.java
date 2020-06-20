package fr.ulity.core.api;

import fr.ulity.core.utils.Time;

import java.util.Date;

public class Cooldown {
    public String type;
    public String id = "default";

    private Object player = Lang.defaultLang;

    private static final Config cooldownConfig = new Config("cooldown");

    public Cooldown (String type) {
        this.type = type;
    }

    public Cooldown (String type, String id) {
        this.type = type;
        this.id = id;
    }

    public void setPlayer (Object player) {
        this.player = player;
    }

    public void applique (int seconds) {
        cooldownConfig.set("cooldown." + type + "." + id, (seconds*1000) + new Date().getTime());
    }

    public void applique (Time time) {
        cooldownConfig.set("cooldown." + type + "." + id, (time.seconds*1000) + new Date().getTime());
    }

    public boolean isInitialized () {
        return cooldownConfig.isSet("cooldown." + type + "." + id);
    }

    public void clear () {
        cooldownConfig.remove("cooldown." + type + "." + id);
    }

    public Time getTimeLeft () {
        long timestamp = cooldownConfig.getLong("cooldown." + type + "." + id);
        return new Time(
                (timestamp > new Date().getTime() ? (int) ((timestamp - new Date().getTime()) / 1000) : 0)
                , player
        );
    }

    public boolean isEnded () {
        long timestamp = cooldownConfig.getLong("cooldown." + type + "." + id);
        return new Date().getTime() >= timestamp;
    }

}
