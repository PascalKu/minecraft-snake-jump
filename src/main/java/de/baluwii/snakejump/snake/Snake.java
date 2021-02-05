package de.baluwii.snakejump.snake;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Snake implements Runnable {
    // The body of this snake.
    private final List<Block> blocks = new ArrayList<>();

    private List<Byte> availableColors = Arrays.asList(
            (byte) 5,
            (byte) 13
    );

    // the last color id to make the snake colorized.
    private int lastId = 0;
    // the last placed block location as a variable to save resources.
    private Block lastBlock;

    // the direction we are going to
    private Direction direction;
    // if the snake is going up or down
    private Facing facing;

    public Snake(Block entryPoint) {
        this.lastBlock = entryPoint;
        this.direction = Direction.WEST;
        this.facing = Facing.NONE;
    }

    public Snake(Block entryPoint, List<Byte> availableColors) {
        this(entryPoint);
        this.availableColors = availableColors;
    }

    @Override
    public void run() {
        final Block nextBlock = getNextBlock();
        lastBlock = nextBlock;

        // change block type
        nextBlock.setType(Material.WOOL, false);
        lastId += 1;

        if (lastId >= availableColors.size())
            lastId = 0;

        nextBlock.setData(availableColors.get(lastId));
        blocks.add(nextBlock);

        if (blocks.size() > 5) {
            final Block lastBlock = blocks.get(0);
            blocks.remove(lastBlock);

            lastBlock.setData((byte) 0, false);
            lastBlock.setType(Material.BROWN_MUSHROOM);
        }
    }

    public Block getNextBlock() {
        final List<BlockSet> blocksToCheck = new ArrayList<>();

        final BlockSet upBlockSet = new BlockSet(Facing.UP, lastBlock.getLocation().add(0.5, 1, 0.5).getBlock()),
                downBlockSet = new BlockSet(Facing.DOWN, lastBlock.getLocation().add(0.5, -1, 0.5).getBlock()),
                southBlockSet = new BlockSet(Direction.SOUTH, lastBlock.getLocation().add(0.5, 0.5, 1).getBlock()),
                eastBlockSet = new BlockSet(Direction.EAST, lastBlock.getLocation().add(1, 0.5, 0.5).getBlock()),
                westBlockSet = new BlockSet(Direction.WEST, lastBlock.getLocation().add(-1, 0.5, 0.5).getBlock()),
                northBlockSet = new BlockSet(Direction.NORTH, lastBlock.getLocation().add(0.5, 0.5, -1).getBlock());

        if (facing != Facing.DOWN) {
            // UP
            blocksToCheck.add(upBlockSet);
        }
        if (facing != Facing.UP) {
            // DOWN
            blocksToCheck.add(downBlockSet);
        }

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

        for (BlockSet blockSet : blocksToCheck) {
            final Block block = blockSet.getBlock();

            if (block.getType() == Material.WOOL || block.getType() == Material.DEAD_BUSH || block.getType() == Material.BROWN_MUSHROOM) {
                if (blockSet.getDirection() != null)
                    this.direction = blockSet.getDirection();
                if (blockSet.getFacing() != null)
                    this.facing = blockSet.getFacing();

                return block;
            }
        }

        throw new NullPointerException("Cannot find next wool block!");
    }
}
