/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.skygrid.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private boolean fillChest = true;
    private boolean colorWool = true;
    private boolean growOnGrassOrDirt = true;
    private boolean growOnWater = true;
    private boolean growOnSend = true;
    private int[] onGrassOrDirt = new int[]{6, 31, 37, 38, 39, 40};
    private int[] onWater = new int[]{111};
    private int[] onSend = new int[]{32, 81};

    public SkyGridGenerator(FileConfiguration config, BlockFileReader blockFileReader) {
        height = config.getInt("map_height", height);
        blockSpace = config.getInt("block_space", blockSpace);
        fillChest = config.getBoolean("fill_chest", fillChest);
        colorWool = config.getBoolean("color_wool", colorWool);
        growOnGrassOrDirt = config.getBoolean("grow_on_grass_or_dirt", growOnGrassOrDirt);
        growOnWater = config.getBoolean("grow_on_water", growOnWater);
        growOnSend = config.getBoolean("grow_on_send", growOnSend);
        this.blockFileReader = blockFileReader;
        materials = Utils.getAllowedMaterials(blockFileReader);
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return new ArrayList<BlockPopulator>();
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return (x % blockSpace == 0 && z % blockSpace == 0);//spawn mobs only on blocks
    }

    @Override
    public byte[] generate(World world, Random random, int cX, int cZ) {
        byte[] result = new byte[65536];
        int x, y, z;

        for (x = 0; x < 16; x += blockSpace) {
            for (z = 0; z < 16; z += blockSpace) {
                for (y = 0; y < height; y += blockSpace) {
                    int id = materials[(int) (random.nextDouble() * materials.length)].getId();
                    if (id == Material.GRASS.getId() && growOnGrassOrDirt) {
                        if (y + 1 < world.getMaxHeight()) {
                            result[Utils.convertToByte(x, y + 1, z)] = (byte) onGrassOrDirt[(int) (random.nextDouble() * onGrassOrDirt.length)];
                        }
                    }
                    if (id == Material.DIRT.getId() && growOnGrassOrDirt) {
                        if (y + 1 < world.getMaxHeight() && random.nextBoolean()) {
                            result[Utils.convertToByte(x, y + 1, z)] = (byte) onGrassOrDirt[(int) (random.nextDouble() * onGrassOrDirt.length)];
                        }
                    }
                    if (id == Material.STATIONARY_WATER.getId() && growOnWater) {
                        if (y + 1 < world.getMaxHeight() && random.nextBoolean()) {
                            result[Utils.convertToByte(x, y + 1, z)] = (byte) onWater[(int) (random.nextDouble() * onWater.length)];
                        }
                    }
                    if (id == Material.SAND.getId() && growOnSend) {
                        if (y + 1 < world.getMaxHeight() && random.nextBoolean()) {
                            result[Utils.convertToByte(x, y + 1, z)] = (byte) onSend[(int) (random.nextDouble() * onSend.length)];
                        }
                    }
                    if (id == Material.CHEST.getId() && fillChest) {
                        ChestAndWoolGenerator.generate(world, cX, cZ, x, y, z, random, ChestAndWoolGenerator.TYPE_CHEST, blockFileReader);
                    }
                    if (id == Material.WOOL.getId() && colorWool) {
                        ChestAndWoolGenerator.generate(world, cX, cZ, x, y, z, random, ChestAndWoolGenerator.TYPE_WOOL, blockFileReader);
                    }
                    result[Utils.convertToByte(x, y, z)] = (byte) id;
                }
            }
        }

        return result;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return world.getBlockAt(0, Math.min(height + 1, world.getMaxHeight()), 0).getLocation();//Spawn no block!
    }
}
