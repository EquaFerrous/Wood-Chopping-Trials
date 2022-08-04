package me.equaferrous.woodchoppingtrials.utility;

import org.bukkit.Location;
import org.bukkit.block.Block;

public abstract class WorldObject {

    protected final Location location;

    // ---------------------------------

    protected WorldObject(Location location) {
        this.location = Utility.getBlockLocation(location);
    }

    // ---------------------------------

    public Location getLocation() {
        return location;
    }

    public Block getBlock() {
        return location.getBlock();
    }

    // ---------------------------------

    public abstract void delete();
}
