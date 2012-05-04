/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.skygrid;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import si.minecraftserver.bukkit.skygrid.generator.SkyGridGenerator;
import si.minecraftserver.bukkit.skygrid.utils.BlockFileReader;

/**
 *
 * @author martin
 */
public class SkyGrid extends JavaPlugin {

    private static final String CONFIG_NAME = "config.yml";
    private static SkyGrid instance;
    private FileConfiguration config;
    private BlockFileReader blockFileReader;

    @Override
    public void onEnable() {
        instance = this;
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        blockFileReader = new BlockFileReader(getDataFolder());
        config = getConfig();
        try {
            File f = new File(getDataFolder(), CONFIG_NAME);
            if (f.exists()) {
                config.load(f);
            } else {
                copyResource(f);
            }
        } catch (FileNotFoundException ex) {
            Bukkit.getLogger().log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new SkyGridGenerator(config, blockFileReader);
    }

    private void copyResource(File config) {
        try {
            InputStream in = getClass().getResourceAsStream("/" + CONFIG_NAME);
            FileOutputStream out = new FileOutputStream(config);
            byte[] buff = new byte[512];
            int i;
            while ((i = in.read(buff)) != -1) {
                out.write(buff, 0, i);
            }
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BlockFileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlockFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static SkyGrid getInstance(){
        return instance;
    }
}
