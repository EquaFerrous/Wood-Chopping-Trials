package me.equaferrous.woodchoppingtrials.utility;

import org.bukkit.Location;

public class Utility {

    public static Location getBlockLocation(Location location) {
        return new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
