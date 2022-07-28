package me.equaferrous.woodchoppingtrials.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class PreventLeafDropsEvent implements Listener {
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onLeafDecay(LeavesDecayEvent event) {
        event.getBlock().setType(Material.AIR);
        event.setCancelled(true);
    }
}
