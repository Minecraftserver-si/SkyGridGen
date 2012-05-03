/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.skygrid.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;

/**
 *
 * @author martin
 */
public class BlockFileReader {

    private static final String FILE_NAME = "ignor_blocks.lst";
    private List<Integer> ids = new ArrayList<Integer>();

    public BlockFileReader(File dataFolder) {
        File file = new File(dataFolder, FILE_NAME);
        if (!file.exists()) {
            copyResource(dataFolder);
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

    private void copyResource(File folder) {
        try {
            InputStream in = getClass().getResourceAsStream("/" + FILE_NAME);
            FileOutputStream out = new FileOutputStream(new File(folder, FILE_NAME));
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
}
