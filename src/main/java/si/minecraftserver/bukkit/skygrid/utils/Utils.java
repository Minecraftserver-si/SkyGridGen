/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package si.minecraftserver.bukkit.skygrid.utils;

/**
 *
 * @author martin
 */
public class Utils {
    public static int convertToByte(int x, int y, int z) {
        return (x * 16 + z) * 256 + y;
    }
}
