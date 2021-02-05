package de.baluwii.snakejump;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class SnakeJump extends JavaPlugin implements Listener {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    private List<Block> blockSnake = new ArrayList<>();
    private Block lastBlock;
    private Direction direction;
    private Facing facing;

    private int lastId = 1;

    @Override
    public void onEnable() {
        Bukkit.broadcastMessage("Test!");

        getServer().getPluginManager().registerEvents(this, this);

        final World world = Bukkit.getWorld("world");
        final Location location = new Location(world, 1.5, 87, 0.5);

        location.getBlock().setData((byte) 1, false);
        location.getBlock().setType(Material.DEAD_BUSH, false);

        Bukkit.broadcastMessage(location.getBlock().toString());

        final Block blockAt = world.getBlockAt(location);
        //  blockAt.setType(Material.WOOL, false);
        blockAt.setData((byte) 5);

        this.lastBlock = blockAt;
        this.direction = Direction.WEST;
        this.facing = Facing.NONE;

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                final Block nextBlock = getNextBlock();
                lastBlock = nextBlock;

                // change block type
                nextBlock.setType(Material.WOOL, false);
                lastId += 1;

                if (lastId > 10)
                    lastId = 1;

                nextBlock.setData((byte) lastId);
                blockSnake.add(nextBlock);

                if (blockSnake.size() > 5) {
                    Bukkit.broadcastMessage("Removing from snake!");
                    final Block lastBlock = blockSnake.get(0);
                    blockSnake.remove(lastBlock);

                    lastBlock.setData((byte) 0, false);
                    lastBlock.setType(Material.BROWN_MUSHROOM);
                }
            }
        };

        Bukkit.getScheduler().runTaskTimer(this, runnable, 0L, 10L);
    }

    private class BlockSet {
        private Direction direction;
        private Facing facing = Facing.NONE;
        private Block block;

        public BlockSet(Direction direction, Block block) {
            this.direction = direction;
            this.block = block;
        }

        public BlockSet(Facing facing, Block block) {
            this.facing = facing;
            this.block = block;
        }
    }

    public Block getNextBlock() {
        final List<BlockSet> blocksToCheck = new ArrayList<>();

        if (facing != Facing.DOWN) {
            blocksToCheck.add(new BlockSet(Facing.UP, lastBlock.getLocation().add(0.5, 1, 0.5).getBlock()));
        }
        if (facing != Facing.UP) {
            blocksToCheck.add(new BlockSet(Facing.DOWN, lastBlock.getLocation().add(0.5, -1, 0.5).getBlock()));
        }

        final BlockSet southBlockSet = new BlockSet(Direction.SOUTH, lastBlock.getLocation().add(0.5, 0.5, 1).getBlock()),
                eastBlockSet = new BlockSet(Direction.EAST, lastBlock.getLocation().add(1, 0.5, 0.5).getBlock()),
                westBlockSet = new BlockSet(Direction.WEST, lastBlock.getLocation().add(-1, 0.5, 0.5).getBlock()),
                northBlockSet = new BlockSet(Direction.NORTH, lastBlock.getLocation().add(0.5, 0.5, -1).getBlock());

        if (direction == Direction.SOUTH) {
            // SOUTH
            blocksToCheck.add(southBlockSet);
            // EAST
            blocksToCheck.add(eastBlockSet);
            // WEST
            blocksToCheck.add(westBlockSet);
            // NORTH
            blocksToCheck.add(northBlockSet);
        } else if (direction == Direction.NORTH) {
            // NORTH
            blocksToCheck.add(northBlockSet);
            // EAST
            blocksToCheck.add(eastBlockSet);
            // WEST
            blocksToCheck.add(westBlockSet);
            // SOUTH
            blocksToCheck.add(southBlockSet);
        } else if (direction == Direction.EAST) {
            // EAST
            blocksToCheck.add(eastBlockSet);
            // SOUTH
            blocksToCheck.add(southBlockSet);
            // NORTH
            blocksToCheck.add(northBlockSet);
            // WEST
            blocksToCheck.add(westBlockSet);
        } else if (direction == Direction.WEST) {
            // WEST
            blocksToCheck.add(westBlockSet);
            // SOUTH
            blocksToCheck.add(southBlockSet);
            // NORTH
            blocksToCheck.add(northBlockSet);
            // EAST
            blocksToCheck.add(eastBlockSet);
        }

        if (facing == Facing.NONE) {
            blocksToCheck.add(blocksToCheck.size() - 1, new BlockSet(Facing.DOWN, lastBlock.getLocation().add(0.5, -1, 0.5).getBlock()));
        }

        for (BlockSet blockSet : blocksToCheck) {
            final Block block = blockSet.block;

            if (block.getType() == Material.WOOL || block.getType() == Material.DEAD_BUSH || block.getType() == Material.BROWN_MUSHROOM) {
                if (blockSet.direction != null)
                    this.direction = blockSet.direction;
                if (blockSet.facing != null)
                    this.facing = blockSet.facing;

                return block;
            } else {
                final Location add = block.getLocation().add(0, 0.5, 0);
                Bukkit.broadcastMessage(add.getX() + " - " + add.getY() + " - " + add.getZ() + ": " + add.getBlock().getType());
            }

            System.out.println(block.getTypeId());
        }

        throw new NullPointerException("Cannot find wool block!");
    }

    private enum Facing {
        NONE,
        UP,
        DOWN,
    }

    private enum Direction {
        SOUTH,
        WEST,
        NORTH,
        EAST,
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent event) {
        System.out.println(event.toString());
        System.out.println(event.getChangedType());
        event.setCancelled(true);
    }
}
