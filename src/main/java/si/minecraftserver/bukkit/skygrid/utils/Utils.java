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
    // Converts the degrees to a normalised radian for use with Math.sin()

    public static int convertToByte(int x, int y, int z) {
        return (x * 16 + z) * 256 + y;
    }

    public static double normalise(int x) {

        int conv = x % 360;
        if (conv >= 1) {
            x = x % 360;
        }
        return Math.toRadians(x);
    }

    public static double sinOctave(int x, int z, int octave) {
        return (Math.sin(normalise(x / octave)) + Math.sin(normalise(z / octave)));
    }

    public static double cube(double x) {
        return x * x * x;
    }

    public static double get(int x, int z) {
        double calc =
                Math.sin(normalise(x / 4))
                + Math.sin(normalise(z / 5))
                + Math.sin(normalise(x - z))
                + (Math.sin(normalise(z)) / (Math.sin(normalise(z)) + 2)) * 2
                + Math.sin(normalise((int) (Math.sin(normalise(x) + Math.sin(normalise(z))) + Math.sin(normalise(z)))) + Math.sin(normalise(z / 2))
                + Math.sin(normalise(x)
                + Math.sin(normalise(z)))
                + Math.sin(normalise(z))
                + Math.sin(normalise(z))
                + Math.sin(normalise(x)))
                + Math.sin(normalise((x + z) / 4))
                + Math.sin(normalise((int) (x + Math.sin(normalise(z) * Math.sin(normalise(x))))));

        calc = Math.abs(calc / 6);
        if (calc > 1) {
            calc = 1;
        }
        return calc;
    }
}
