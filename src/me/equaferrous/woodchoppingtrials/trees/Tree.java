package me.equaferrous.woodchoppingtrials.trees;

import me.equaferrous.woodchoppingtrials.TextEntity;
import me.equaferrous.woodchoppingtrials.WoodChoppingTrials;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class Tree {

    private static final int TICK_DELAY = 10;
    private static final int TREE_SEARCH_WIDTH = 1;
    private static final int TREE_SEARCH_HEIGHT = 7;

    private Block saplingBlock;
    private TextEntity textEntity;
    private ArrayList<Block> logList = new ArrayList<>();
    private TreeType treeType;
    private Material logType;
    private Material saplingType;

    private int maxGrowTime;
    private int currentGrowTime = 0;

    private BukkitTask growTickTask;
    private BukkitTask checkTreeGoneTask;

    // --------------------------------------------

    public Tree(Location blockLocation, int ticksToGrow, TreeType treeType) {
        saplingBlock = blockLocation.getBlock();
        textEntity = new TextEntity(blockLocation.add(0,1,0));

        maxGrowTime = ticksToGrow;
        this.treeType = treeType;
        logType = Material.OAK_LOG;
        saplingType = Material.OAK_SAPLING;

        regrowTree();
    }

    // ---------------------------------------------

    // ---------------------------------------------

    private void placeSapling() {
        saplingBlock.setType(saplingType);
    }

    private void tickGrowTimer() {
        if (currentGrowTime >= maxGrowTime) {
            boolean treeGrown = growTree();
            if (!treeGrown) {
                placeSapling();
                textEntity.setText(ChatColor.RED +"Blocked");
            }
            else {
                findLogBlocks();
                textEntity.setVisible(false);
                growTickTask.cancel();
                checkTreeGoneTask = Bukkit.getScheduler().runTaskTimer(WoodChoppingTrials.getPlugin(), this::checkTreeGone, 20, 20);
            }
        }
        else {
            currentGrowTime += TICK_DELAY;
            String timeText = String.valueOf((int) Math.ceil(((float) maxGrowTime / 20) - ((float) currentGrowTime / 20)));
            textEntity.setText(ChatColor.GREEN +"Growing ["+ timeText +"]");
        }
    }

    private boolean growTree() {
        saplingBlock.setType(Material.AIR);
        return saplingBlock.getWorld().generateTree(saplingBlock.getLocation(), treeType);
    }

    private void findLogBlocks() {
        for (int x = -TREE_SEARCH_WIDTH; x <= TREE_SEARCH_WIDTH; x++) {
            for (int z = -TREE_SEARCH_WIDTH; z <= TREE_SEARCH_WIDTH; z++) {
                for (int y = 0; y < TREE_SEARCH_HEIGHT; y++) {
                    int blockX = saplingBlock.getX() + x;
                    int blockY = saplingBlock.getY() + y;
                    int blockZ = saplingBlock.getZ() + z;

                    Block block = new Location(saplingBlock.getWorld(), blockX, blockY, blockZ).getBlock();
                    if (block.getType().equals(logType)) {
                        logList.add(block);
                    }
                }
            }
        }
    }

    private void checkTreeGone() {
        ArrayList<Block> tempList = new ArrayList<>(logList);

        for (Block block : tempList) {
            if (!block.getType().equals(logType)) {
                logList.remove(block);
            }
        }

        if (logList.isEmpty()) {
            regrowTree();
            checkTreeGoneTask.cancel();
        }
    }

    private void regrowTree() {
        currentGrowTime = 0;
        growTickTask = Bukkit.getScheduler().runTaskTimer(WoodChoppingTrials.getPlugin(), this::tickGrowTimer, TICK_DELAY, TICK_DELAY);
        textEntity.setVisible(true);
        placeSapling();
    }

}
