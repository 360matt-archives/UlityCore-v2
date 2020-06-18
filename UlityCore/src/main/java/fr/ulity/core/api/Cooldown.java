package fr.ulity.core.api;

import fr.ulity.core.utils.Time;

import java.util.Date;

public class Cooldown {
    public String type;
    public String id = "default";

    private static final Config cooldownConfig = new Config("cooldown");

    public Cooldown (String type) {
        this.type = type;
    }

    public Cooldown (String type, String id) {
        this.type = type;
        this.id = id;
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


        if (timestamp > new Date().getTime()) {
            long left = ((timestamp - new Date().getTime()) / 1000);

           return new Time((int) left);
        }
        else
            return new Time(0);
    }

    public boolean isEnded () {
        long timestamp = cooldownConfig.getLong("cooldown." + type + "." + id);
        return new Date().getTime() >= timestamp;
    }

}
