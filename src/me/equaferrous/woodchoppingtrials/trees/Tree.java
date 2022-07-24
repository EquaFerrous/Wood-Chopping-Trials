package me.equaferrous.woodchoppingtrials.trees;

import me.equaferrous.woodchoppingtrials.WoodChoppingTrials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class Tree {

    private static final int TICK_DELAY = 10;

    private Block saplingBlock;

    private int maxGrowTime;
    private int currentGrowTime = 0;
    private TreeType treeType;

    private BukkitTask growTickTask;

    // --------------------------------------------

    public Tree(Location blockLocation, int ticksToGrow, TreeType treeType) {
        saplingBlock = blockLocation.getBlock();
        maxGrowTime = ticksToGrow;
        this.treeType = treeType;

        growTickTask = Bukkit.getScheduler().runTaskTimer(WoodChoppingTrials.getPlugin(), this::tickGrowTimer, TICK_DELAY, TICK_DELAY);

        placeSapling();
    }

    // ---------------------------------------------

    // ---------------------------------------------

    private void placeSapling() {
        saplingBlock.setType(Material.OAK_SAPLING);
    }

    private void tickGrowTimer() {
        if (currentGrowTime >= maxGrowTime) {
            boolean treeGrown = growTree();
            if (!treeGrown) {
                placeSapling();
            }
            else {
                growTickTask.cancel();
            }
        }
        else {
            currentGrowTime += TICK_DELAY;
        }
    }

    private boolean growTree() {
        saplingBlock.setType(Material.AIR);
        return saplingBlock.getWorld().generateTree(saplingBlock.getLocation(), treeType);
    }
}
