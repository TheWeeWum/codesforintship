package Fields;
import java.util.ArrayList;
import java.util.List;

public class ThreadForLoopA extends Thread{
    // variables for your Thread ... 
    int width;
    int height;
    int precision;
    double[][] field;
    double MASS;
    List<ArrayList<Double>> list;

    public ThreadForLoopA(int _width, int _height, int _precision, double[][] _field, double _MASS, List<ArrayList<Double>> _list) {
        width = _width;
        height = _height;
        precision = _precision;
        field = _field;
        MASS = _MASS;
        list = _list;
    }

    @Override
    public void run () {
        for (int x = 0; x < 1*width / 4; x += precision) {
            for (int y = 0; y < height; y += precision) {
                double gy = 0;
                double gx = 0;
                for (int i = 0; i < field.length; i++) {
                    double dx = field[i][0] - x;
                    double dy = field[i][1] - y;
                    double distance = Math.pow(dx, 2) + Math.pow(dy, 2);
                    if (distance != 0) {
                        gy = gy + field[i][2] * dy/ (distance * distance * MASS);
                        gx = gx + field[i][2] * dx/ (distance * distance * MASS);
                    }
                }

                double g = Math.sqrt(gy*gy + gx*gx);
                ArrayList<Double> arr = new ArrayList<Double>(3);
                arr.add(0, g);
                arr.add(1, (double)x);
                arr.add(2, (double)y);
                list.add(arr);
            }
        }
    }
}
