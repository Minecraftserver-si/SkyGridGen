/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.skygrid.generator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import si.minecraftserver.bukkit.skygrid.utils.Utils;

/**
 *
 * @author martin
 */
public class SkyGridGenerator extends ChunkGenerator {

    private static final Material[] materials;
    private static final Material[] exclude = new Material[]{
        Material.AIR, Material.SAPLING, Material.WATER, Material.LAVA, Material.LEVER,
        Material.BED_BLOCK, Material.BEDROCK, Material.POWERED_RAIL, Material.DETECTOR_RAIL,
        Material.LONG_GRASS, Material.DEAD_BUSH, Material.PISTON_EXTENSION, Material.PISTON_MOVING_PIECE,
        Material.TORCH, Material.FIRE, Material.MOB_SPAWNER, Material.WHEAT, Material.BURNING_FURNACE,
        Material.SIGN_POST, Material.WOODEN_DOOR, Material.LADDER, Material.RAILS, Material.WALL_SIGN,
        Material.STONE_PLATE, Material.WOOD_PLATE, Material.IRON_DOOR_BLOCK, Material.REDSTONE_TORCH_ON,
        Material.STONE_BUTTON, Material.SNOW, Material.PORTAL, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON,
        Material.LOCKED_CHEST, Material.TRAP_DOOR, Material.PUMPKIN_STEM, Material.MELON_STEM, Material.VINE,
        Material.WATER_LILY, Material.NETHER_WARTS, Material.ENCHANTMENT_TABLE, Material.BREWING_STAND, Material.CAULDRON,
        Material.ENDER_PORTAL, Material.DRAGON_EGG, Material.ENDER_PORTAL_FRAME, Material.REDSTONE_WIRE,
        Material.REDSTONE_TORCH_OFF, Material.SEEDS, Material.DIAMOND_BLOCK, Material.GOLD_BLOCK};

    static {
        LinkedList<Material> ms = new LinkedList<Material>();
        Material m1[] = Material.values();
        for (Material m : m1) {
            if (m.isBlock() && !excluded(m)) {
                ms.add(m);
            }
        }
        materials = new Material[ms.size()];
        for (int i = 0; i < materials.length; i++) {
            materials[i] = ms.get(i);
        }
    }

    private static boolean excluded(Material m) {
        for (Material m1 : exclude) {
            if (m1.getId() == m.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return new ArrayList<BlockPopulator>();
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return true;
    }

    @Override
    public byte[] generate(World world, Random random, int cX, int cZ) {
        byte[] result = new byte[65536];
        int x, y, z;

        for (x = 0; x < 16; x += 4) {
            for (z = 0; z < 16; z += 4) {
                for (y = 0; y < 70; y += 4) {
                    result[Utils.convertToByte(x, y, z)] = (byte) materials[(int) (random.nextDouble() * materials.length)].getId();
                }
            }
        }

        return result;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        int x = random.nextInt(200) - 100;
        int z = random.nextInt(200) - 100;
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y, z);
    }
}
