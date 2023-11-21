package Fields;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class ThreadForColorA extends Thread {
    // variables for your Thread ... 
    int ZOOM;
    double gradient;
    Graphics2D screen;
    List<ArrayList<Double>> list;
    double largestG;
    int precision;

    public ThreadForColorA(int _ZOOM, double _gradient, Graphics2D _screen, int _precision, double _largestG, List<ArrayList<Double>> _list) {
        ZOOM = _ZOOM;
        gradient = _gradient;
        screen = _screen;
        precision = _precision;
        largestG = _largestG;
        list = _list;
    }

    @Override
    public void run () {
        int x;
        int y;
        for (int i = 0; i < list.size() / 4; i ++) {
            ArrayList<Double> arr = list.get(i);
            x = arr.get(1).intValue();
            y = arr.get(2).intValue();
            double g = arr.get(0) * ZOOM;
            if (g <= gradient) {
                int[] colors = allColors(g, gradient);
                screen.setColor(new Color(colors[0], colors[1], colors[2]));
                screen.fillRect(x, y, precision, precision);

            } else if (g <= largestG) {
                int[] colors = onlyRed(g, largestG);
                screen.setColor(new Color(colors[0], colors[1], colors[2]));
                screen.fillRect(x, y, precision, precision);
            }
        }
    }
    public static int[] allColors(double g, double gradient) {
        g = (g) / (gradient) * (1785 - 1) + 1;
        int _g = (int)(g);

        int[] colors = new int[3];
        if (_g > 1530) {
            colors[0] = 255;
            colors[1] = 255 * 2 / 3;
            colors[2] = (_g - 1530) / 4;
        } else if (_g > 1275) {
            colors[2] = (_g - 1275) / 4;
        } else if (_g > 1020) {
            colors[1] = (_g - 1020) * 2 / 3;
        } else if (_g > 765) {
            colors[1] = (_g - 765) * 2 / 3; // waybe swap
            colors[2] = 255 / 4;
        } else if (_g > 510) {
            colors[0] = (_g - 510);
            colors[2] = 255 / 8;
        } else if (_g > 255) {
            colors[0] = 255;
            colors[1] = (_g - 255) * 2 / 3;
        } else {
            colors[0] = _g;
            colors[1] = 0;
            colors[2] = 0;
        }
        return colors;
    }
    public static int[] onlyRed(double g, double gradient) {
        g = (g - 1) / (gradient - 1) * (255 - 1) + 1;
        int _g = (int)(g);

        int[] colors = new int[3];
        colors[0] = _g;
        return colors;
    }
}
