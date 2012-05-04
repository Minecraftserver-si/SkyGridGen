/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.skygrid.generator;

import java.util.LinkedList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import si.minecraftserver.bukkit.skygrid.SkyGrid;
import si.minecraftserver.bukkit.skygrid.utils.BlockFileReader;
import si.minecraftserver.bukkit.skygrid.utils.Utils;

/**
 *
 * @author martin
 */
public class ChestAndWoolGenerator implements Runnable {

    public static final int TYPE_WOOL = 1;
    public static final int TYPE_CHEST = 2;
    private World world;
    private int chunkX;
    private int chunkZ;
    private int x, y, z;
    private Random random;
    private int type;
    private BlockFileReader blockFileReader;
    private static Material[] materials;

    public ChestAndWoolGenerator(World world, int chunkX, int chunkZ, int x, int y, int z, Random random, int type, BlockFileReader blockFileReader) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.x = x;
        this.y = y;
        this.z = z;
        this.random = random;
        this.type = type;
        this.blockFileReader = blockFileReader;
        materials = Utils.getAllowedMaterialsAndItems(blockFileReader);
    }

    public void run() {
        if (type == TYPE_CHEST) {
            Inventory inventory = ((Chest) world.getChunkAt(chunkX, chunkZ).getBlock(x, y, z).getState()).getInventory();
            for (int i = 0; i < inventory.getSize(); i++) {
                if (random.nextInt() % 4 == 0) {
                    Material m;
                    do {
                        m = materials[(int) (random.nextDouble() * materials.length)];
                    } while (m == null || !blockFileReader.excluded(m));
                    inventory.setItem(i, new ItemStack(m, (int) (random.nextDouble() * 10)));
                }
            }
        } else if (type == TYPE_WOOL) {
            world.getChunkAt(chunkX, chunkZ).getBlock(x, y, z).setData((byte) (random.nextDouble() * 15));
        } else {
            throw new IllegalArgumentException("Unknown type!");
        }
    }

    public static void generate(World world, int chunkX, int chunkZ, int x, int y, int z, Random random, int type, BlockFileReader blockFileReader) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(SkyGrid.getInstance(), new ChestAndWoolGenerator(world, chunkX, chunkZ, x, y, z, random, type, blockFileReader));
    }
}
