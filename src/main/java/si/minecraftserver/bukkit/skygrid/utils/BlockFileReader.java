/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.skygrid.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;

/**
 *
 * @author martin
 */
public class BlockFileReader {

    private static final String FILE_NAME = "ignore_blocks.lst";
    private List<Integer> ids = new ArrayList<Integer>();

    public BlockFileReader(File dataFolder) {
        File file = new File(dataFolder, FILE_NAME);
        if (!file.exists()) {
            Utils.copyResource(dataFolder, FILE_NAME, getClass());
        }
        try {
            BufferedReader r = new BufferedReader(new FileReader(file));
            String line;
            while ((line = r.readLine()) != null) {
                ids.add(Integer.valueOf(line));
            }
        } catch (FileNotFoundException ex) {
            Bukkit.getLogger().log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, null, ex);
        }
    }

    public boolean excluded(Material m) {
        return !ids.contains(m.getId());
    }
}
