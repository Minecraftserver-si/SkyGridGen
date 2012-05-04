/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.skygrid.utils;

import java.io.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;

/**
 *
 * @author martin
 */
public class Utils {
    public static int convertToByte(int x, int y, int z) {
        return (x * 16 + z) * 256 + y;
    }
    
    public static Material[] getAllowedMaterialsAndItems(BlockFileReader blockFileReader){
        Material[] materials;
        LinkedList<Material> ms = new LinkedList<Material>();
        Material m1[] = Material.values();
        for (Material m : m1) {
            if (blockFileReader.excluded(m)) {
                ms.add(m);
            }
        }
        materials = new Material[ms.size()];
        for (int i = 0; i < materials.length; i++) {
            materials[i] = ms.get(i);
        }
        return materials;
    }
    
    public static Material[] getAllowedMaterials(BlockFileReader blockFileReader){
        Material[] materials;
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
        return materials;
    }
    
    public static void copyResource(File folder, String fileName, Class clazz) {
        try {
            InputStream in = clazz.getResourceAsStream("/" + fileName);
            FileOutputStream out = new FileOutputStream(new File(folder, fileName));
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
    
    public static void copyResource(File config, Class clazz) {
        try {
            InputStream in = clazz.getResourceAsStream("/" + config.getName());
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
}
