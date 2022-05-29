package me.aroze;

import net.minestom.server.MinecraftServer;

public class MinestomThingy {
    public static void main(String[] args) {


        System.out.println("Starting thingy...");

        MinecraftServer minecraftServer = MinecraftServer.init();
        minecraftServer.start("0.0.0.0", 25565);

        System.out.println("Started thingy!");

    }
}