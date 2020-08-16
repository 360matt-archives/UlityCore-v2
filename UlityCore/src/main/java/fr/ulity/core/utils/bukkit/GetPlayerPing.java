package fr.ulity.core.utils.bukkit;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class GetPlayerPing {
    public static int getPing (Player p)  {
        try {
            Object entityPlayer = p.getClass().getDeclaredMethod("getHandle").invoke(p);
            Field f = entityPlayer.getClass().getDeclaredField("ping");
            return f.getInt(entityPlayer);
        } catch (Exception ignored) {
            return 0;
        }
    }
}
