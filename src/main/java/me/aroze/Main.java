package me.aroze;

import net.minestom.server.MinecraftServer;

public class Main {
    public static void main(String[] args) {


        System.out.println("Starting thingy...");

        MinecraftServer minecraftServer = new MinecraftServer();
        minecraftServer.start("0.0.0.0", 25565);

        System.out.println("Started thingy!");

    }
}