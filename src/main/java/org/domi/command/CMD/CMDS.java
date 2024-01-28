package org.domi.command.CMD;

import org.CatAndDomi.api.NBT;
import org.CatAndDomi.utils.ColorUtils;
import org.bukkit.*;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.domi.event.Events;

import java.util.Random;

public class CMDS {
    private static final Random random = new Random();
    private static World world;
    public static void wands(Player player) {
        ItemStack item = NBT.setStringTag(new ItemStack(Material.STICK), "domi", "true");
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("설정의 지팡이");
        item.setItemMeta(im);
        player.getInventory().addItem(item);
    }

    public static void meteorInt(Player player, int counts) {
        world = Bukkit.getWorld("world");
        Location[] locations = Events.playerLocations.get(player);

        if (locations == null || locations[0] == null || locations[1] == null) {
            Bukkit.broadcastMessage("먼저 좌클릭과 우클릭으로 범위를 설정해주세요.");
        }

        int count = counts;

        int minX = Math.min(locations[0].getBlockX(), locations[0].getBlockX());
        int maxX = Math.max(locations[1].getBlockX(), locations[1].getBlockX());
        int minY = Math.min(locations[0].getBlockY(), locations[0].getBlockY());
        int maxY = Math.max(locations[1].getBlockY(), locations[1].getBlockY());
        int minZ = Math.min(locations[0].getBlockZ(), locations[0].getBlockZ());
        int maxZ = Math.max(locations[1].getBlockZ(), locations[1].getBlockZ());

        for (int i = 0; i < count; i++) {

            int x = getRandomBetween(minX, maxX);
            int y = getRandomBetween(minY, maxY);
            int z = getRandomBetween(minZ, maxZ);

            Location spawnLocation = new Location(player.getWorld(), x, y + 50, z);

            FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(spawnLocation, Material.MAGMA_BLOCK.createBlockData());

            for (int ef = 0; ef < 10; ef++) {
                world.spawnParticle(Particle.SMOKE_LARGE, spawnLocation, 20);
            }
        }
    }

    private static int getRandomBetween(int a, int b) {
        return a + random.nextInt(Math.abs(b - a) + 1);
    }
}
