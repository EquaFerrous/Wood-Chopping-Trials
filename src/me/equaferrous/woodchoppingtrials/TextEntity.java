package me.equaferrous.woodchoppingtrials;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class TextEntity {

    private ArmorStand entity;

    // -----------------------------------------------

    public TextEntity(Location location) {
        entity = (ArmorStand) location.getWorld().spawnEntity(location.add(0.5,0,0.5), EntityType.ARMOR_STAND);
        entity.setMarker(true);
        entity.setVisible(false);
        entity.addScoreboardTag(Main.getPlugin().getName());
    }

    // ----------------------------------------------------

    public void setText(String text) {
        entity.setCustomName(text);
    }

    public void setVisible(boolean visible) {
        entity.setCustomNameVisible(visible);
    }

    public void setLocation(Location location) {
        entity.teleport(location);
    }

    public void delete() {
        entity.remove();
    }

    // -----------------------------------------------------
}
