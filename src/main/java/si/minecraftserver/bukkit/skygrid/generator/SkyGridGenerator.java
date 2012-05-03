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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import si.minecraftserver.bukkit.skygrid.utils.BlockFileReader;
import si.minecraftserver.bukkit.skygrid.utils.Utils;

/**
 *
 * @author martin
 */
public class SkyGridGenerator extends ChunkGenerator {

    private static Material[] materials;
    private BlockFileReader blockFileReader;
    private int height = 70;
    private int blockSpace = 4;

    public SkyGridGenerator(FileConfiguration config, BlockFileReader blockFileReader) {
        height = config.getInt("map_height", height);
        blockSpace = config.getInt("block_space", blockSpace);
        this.blockFileReader = blockFileReader;
        LinkedList<Material> ms = new LinkedList<Material>();
        Material m1[] = Material.values();
        for (Material m : m1) {
            if (m.isBlock() && blockFileReader.excluded(m)) {
                ms.add(m);
            }
        }
        materials = new Material[ms.size()];
        for (int i = 0; i < materials.length; i++) {
            materials[i] = ms.get(i);
        }
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

        for (x = 0; x < 16; x += blockSpace) {
            for (z = 0; z < 16; z += blockSpace) {
                for (y = 0; y < height; y += blockSpace) {
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
