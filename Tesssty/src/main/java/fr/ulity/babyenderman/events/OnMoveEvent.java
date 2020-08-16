package fr.ulity.babyenderman.events;

import com.bergerkiller.bukkit.common.events.EntityMoveEvent;
import fr.ulity.core.api.bukkit.LangBukkit;
import fr.ulity.core.utils.HashMapReversed;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class OnMoveEvent implements Listener {
    private static HashMapReversed<Entity, Zombie> replacement = new HashMapReversed<>();

    @EventHandler (priority = EventPriority.HIGH)
    private static void onMove (EntityMoveEvent e) {
        if (e.getEntity().getType().equals(EntityType.ENDERMAN)) {
            Enderman entity = (Enderman) e.getEntity();

            if (replacement.containsKey(entity)) {
                if (!replacement.get(entity).isDead()) {
                    Zombie zombie = replacement.get(entity);
                    zombie.teleport(entity);

                } else
                    replacement.remove(entity);

            } else if (entity.getName().startsWith(LangBukkit.get("babyenderman.name_for_transform"))) {
                Zombie z = (Zombie) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.ZOMBIE);
                z.setBaby(true);
                z.setCustomName(LangBukkit.get("babyenderman.name_of_baby"));
                z.setAI(false);

                PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 20);
                PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 999999, 4);

                entity.addPotionEffect(invisibility);
                entity.addPotionEffect(slow);

                replacement.put(entity, z);

            }

        }
    }

    @EventHandler
    public void onEntityCombust (EntityCombustEvent e){
        if (replacement.containsValue(e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private static void onDamage (EntityDamageEvent e) {
        if (replacement.containsValue(e.getEntity())) {
            e.setCancelled(true);
            ((Damageable) replacement.getKey((Zombie) e.getEntity())).damage(e.getDamage());
        }
    }

    @EventHandler
    private static void onPvP (EntityDamageByEntityEvent e) {
        if (replacement.containsValue(e.getDamager()))
            e.setCancelled(true);
        else if (replacement.containsValue(e.getEntity())) {
            e.setCancelled(true);
            Bukkit.getPluginManager()
                    .callEvent( // damage to enderman
                            new EntityDamageByEntityEvent(
                                    e.getDamager(),
                                    replacement.get(e.getEntity()),
                                    EntityDamageEvent.DamageCause.ENTITY_ATTACK,
                                    1
                            )

                    );
            Bukkit.getPluginManager()
                    .callEvent( // simulate damage to zombie
                            new EntityDamageByEntityEvent(
                                    e.getDamager(),
                                    replacement.get(e.getEntity()),
                                    EntityDamageEvent.DamageCause.ENTITY_ATTACK,
                                    0
                            )

                    );
        }
    }

    @EventHandler
    private static void onDeath (EntityDeathEvent e) {
        if (replacement.containsKey(e.getEntity())) {
            replacement.get(e.getEntity()).remove();

            replacement.get(e.getEntity()).remove();
            replacement.remove(e.getEntity());
        }
    }



}
