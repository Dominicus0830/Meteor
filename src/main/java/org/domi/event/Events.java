package org.domi.event;

import org.CatAndDomi.api.NBT;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.domi.METEOR;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Events implements Listener {
    private static final METEOR plugin = METEOR.getInstance();
    private final Random random = new Random();
    public static final Map<Player, Location[]> playerLocations = new HashMap<>();

    public Events() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            FallingBlock fallingBlock = (FallingBlock) event.getEntity();

            if (fallingBlock.getBlockData().getMaterial() == Material.MAGMA_BLOCK) {
                Location explosionLocation = fallingBlock.getLocation();
                fallingBlock.getWorld().createExplosion(explosionLocation, 4F);  // 마그마 블록 위치에서 폭발을 생성

                for (int i = 0; i < 35; i++) {  // 불을 10개 붙임
                    int x = explosionLocation.getBlockX() + getRandomBetween(-5, 5);  // 폭발 지점 주위의 랜덤한 위치
                    int y = explosionLocation.getBlockY() + getRandomBetween(-5, 5);
                    int z = explosionLocation.getBlockZ() + getRandomBetween(-5, 5);

                    Location fireLocation = new Location(explosionLocation.getWorld(), x, y, z);
                    if (fireLocation.getBlock().getType() == Material.AIR) {  // 블록이 없는 위치인 경우에만 불을 붙임
                        fireLocation.getBlock().setType(Material.FIRE);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("domi.setMeteorArea")) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (NBT.hasTagKey(item, "domi")) {
                    Location[] locations = playerLocations.getOrDefault(player, new Location[2]);
                    locations[0] = event.getClickedBlock().getLocation();
                    playerLocations.put(player, locations);
                    player.sendMessage("pos1 설정완료! x 자표: " + event.getClickedBlock().getLocation().getBlockX() + "z 자표:" +event.getClickedBlock().getLocation().getBlockZ());
                    event.setCancelled(true);
                }
            }
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (NBT.hasTagKey(item, "domi")) {
                    Location[] locations = playerLocations.getOrDefault(player, new Location[3]);
                    locations[1] = event.getClickedBlock().getLocation();
                    playerLocations.put(player, locations);
                    //send x y z
                    player.sendMessage("pos2 설정완료! 자표: " + event.getClickedBlock().getLocation().getBlockX() + "z 자표:" +event.getClickedBlock().getLocation().getBlockZ());
                    event.setCancelled(true);
                }
            }
        }
    }

    private int getRandomBetween(int a, int b) {
        return a + random.nextInt(Math.abs(b - a) + 1);
    }
}
