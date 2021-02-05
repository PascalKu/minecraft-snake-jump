package de.baluwii.snakejump;

import de.baluwii.snakejump.snake.Snake;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SnakeJump extends JavaPlugin implements Listener {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        Bukkit.broadcastMessage("Test!");

        getServer().getPluginManager().registerEvents(this, this);

        final World world = Bukkit.getWorld("world");

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -45.5, 70, -82.5).getBlock()), 0L, 10L);

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -45.5, 70, -88.5).getBlock()), 0L, 10L);

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -41.5, 72, -91.5).getBlock()), 0L, 10L);

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -41.5, 73, -88.5).getBlock()), 0L, 10L);

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -36.5, 71, -87.5).getBlock()), 0L, 10L);

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -41.5, 74, -98.5).getBlock()), 0L, 10L);

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -40.5, 75, -104.5).getBlock()), 0L, 10L);

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -36.5, 79, -99.5).getBlock()), 0L, 10L);

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -27.5, 80, -97.5).getBlock()), 0L, 10L);

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -27.5, 80, -95.5).getBlock()), 0L, 10L);

        // Entrypoint
        Bukkit.getScheduler().runTaskTimer(this, new Snake(new Location(world, -21.5, 84, -86.5).getBlock()), 0L, 10L);
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
