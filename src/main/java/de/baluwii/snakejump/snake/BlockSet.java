package de.baluwii.snakejump.snake;

import org.bukkit.block.Block;

public class BlockSet {
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

    public Direction getDirection() {
        return direction;
    }

    public Facing getFacing() {
        return facing;
    }

    public Block getBlock() {
        return block;
    }
}