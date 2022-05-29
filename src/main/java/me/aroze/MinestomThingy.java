package me.aroze;

import de.articdive.jnoise.JNoise;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.*;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.world.biomes.Biome;

import java.util.Arrays;
import java.util.List;

public class MinestomThingy {
    public static void main(String[] args) {


        System.out.println("Starting thingy (and started timer)...");
        long startTime = System.currentTimeMillis();

        JNoise noise = JNoise.newBuilder()
            .perlin()
            .setFrequency(0.05) // Low frequency for smooth terrain
            .build();

        MinecraftServer minecraftServer = MinecraftServer.init();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        // Create the instance
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        // Set the ChunkGenerator
        instanceContainer.setGenerator(unit -> {
            Point start = unit.absoluteStart();
            for (int x = 0; x < unit.size().x(); x++) {
                for (int z = 0; z < unit.size().z(); z++) {
                    Point bottom = start.add(x, 0, z);

                    synchronized (noise) { // Synchronization is necessary for JNoise
                        double height = noise.getNoise(bottom.x(), bottom.z()) * 16;
                        // * 16 means the height will be between -16 and +16
                        unit.modifier().fill(bottom, bottom.add(1, 0, 1).withY(height), Block.WHITE_CONCRETE);
                    }
                }
            }
        });

        // Add an event callback to specify the spawning instance (and the spawn position)
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
            player.setGameMode(GameMode.CREATIVE);
        });

        // Start the server on port 25565
        minecraftServer.start("0.0.0.0", 25000);

        long startedTime = System.currentTimeMillis();
        long timeTaken = startedTime - startTime;
        System.out.println("Started thingy in " + timeTaken + "ms!");

    }
}