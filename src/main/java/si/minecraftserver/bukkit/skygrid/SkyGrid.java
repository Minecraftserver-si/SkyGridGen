/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.skygrid;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import si.minecraftserver.bukkit.skygrid.generator.SkyGridGenerator;

/**
 *
 * @author martin
 */
public class SkyGrid extends JavaPlugin{

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new SkyGridGenerator();
    }
    
}
