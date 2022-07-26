package me.equaferrous.woodchoppingtrials.trees;

import me.equaferrous.woodchoppingtrials.TextEntity;
import me.equaferrous.woodchoppingtrials.Main;
import me.equaferrous.woodchoppingtrials.utility.Utility;
import me.equaferrous.woodchoppingtrials.utility.WorldObject;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class Tree extends WorldObject {

    private static final int TICK_DELAY = 10;
    private static final int TREE_SEARCH_WIDTH = 1;
    private static final int TREE_SEARCH_HEIGHT = 7;

    private final Block saplingBlock;
    private final TextEntity textEntity;
    private final List<Block> logList = new ArrayList<>();
    private TreeStatus status;

    private TreeTier tier;
    private TreeType treeType;
    private Material logType;
    private Material saplingType;
    private int maxGrowTime;
    private int currentGrowTime = 0;

    private BukkitTask growTickTask;
    private BukkitTask checkTreeGoneTask;

    // --------------------------------------------

    public Tree(Location location, TreeTier treeTier) {
        super(location);

        saplingBlock = getBlock();
        textEntity = new TextEntity(getLocation().add(0,1,0));
        tier = treeTier;

        maxGrowTime = tier.getGrowTime();
        treeType = tier.getTreeType();
        logType = tier.getLogType();
        saplingType = tier.getSaplingType();

        changeStatus(TreeStatus.GROWING);
    }

    // ---------------------------------------------

    public void changeStatus(TreeStatus newStatus) {
        if (newStatus.equals(status)) {
            return;
        }

        status = newStatus;
        switch (status) {
            case GROWN:
                textEntity.setVisible(false);
                findLogBlocks();
                checkTreeGoneTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), this::checkTreeGone, 20, 20);
                break;

            case GROWING:
                currentGrowTime = 0;
                textEntity.setVisible(true);
                placeSapling();
                updateGrowTimeText();
                growTickTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), this::tickGrowTimer, TICK_DELAY, TICK_DELAY);
                break;

            case BLOCKED:
                break;

            case DISABLED:
                break;

        }
    }

    public TreeStatus getTreeStatus() {
        return status;
    }

    public TreeTier getTier() {
        return tier;
    }

    @Override
    public void delete() {
        if (growTickTask != null) {
            growTickTask.cancel();
            growTickTask = null;
        }
        if (checkTreeGoneTask != null) {
            checkTreeGoneTask.cancel();
            checkTreeGoneTask = null;
        }

        for (Block log : logList) {
            log.setType(Material.AIR);
        }
        saplingBlock.setType(Material.AIR);

        textEntity.delete();
        changeStatus(TreeStatus.DISABLED);
    }

    // ---------------------------------------------

    private void placeSapling() {
        saplingBlock.setType(saplingType);
    }

    // Grows the tree. Returns whether the tree was successfully grown.
    private boolean growTree() {
        saplingBlock.setType(Material.AIR);
        return saplingBlock.getWorld().generateTree(saplingBlock.getLocation(), treeType);
    }

    // Finds all log blocks in the tree search area.
    private void findLogBlocks() {
        for (int x = -TREE_SEARCH_WIDTH; x <= TREE_SEARCH_WIDTH; x++) {
            for (int z = -TREE_SEARCH_WIDTH; z <= TREE_SEARCH_WIDTH; z++) {
                for (int y = 0; y < TREE_SEARCH_HEIGHT; y++) {
                    int blockX = saplingBlock.getX() + x;
                    int blockY = saplingBlock.getY() + y;
                    int blockZ = saplingBlock.getZ() + z;

                    Block block = new Location(saplingBlock.getWorld(), blockX, blockY, blockZ).getBlock();
                    if (block.getType().toString().contains("_LOG")) {
                        block.setType(logType);
                        logList.add(block);
                    }
                }
            }
        }
    }

    // Updates the text entity to show grow time remaining.
    private void updateGrowTimeText() {
        String timeText = String.valueOf((int) Math.ceil(((float) maxGrowTime / 20) - ((float) currentGrowTime / 20)));
        textEntity.setText(ChatColor.GREEN +"Growing ["+ timeText +"]");
    }

    // ----------------------------------------------------

    // Increments the grow timer and attempts to grow the tree when fully grown.
    private void tickGrowTimer() {
        if (currentGrowTime >= maxGrowTime) {
            boolean treeGrown = growTree();
            if (!treeGrown) {
                placeSapling();
                textEntity.setText(ChatColor.RED +"Blocked");
            }
            else {
                growTickTask.cancel();
                changeStatus(TreeStatus.GROWN);
            }
        }
        else {
            currentGrowTime += TICK_DELAY;
            updateGrowTimeText();
        }
    }

    // Checks if all log blocks have been mined and regrows the tree.
    private void checkTreeGone() {
        ArrayList<Block> tempList = new ArrayList<>(logList);

        for (Block block : tempList) {
            if (!block.getType().equals(logType)) {
                logList.remove(block);
            }
        }

        if (logList.isEmpty()) {
            checkTreeGoneTask.cancel();
            changeStatus(TreeStatus.GROWING);
        }
    }
}
