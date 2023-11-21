package Fields;
// package com.javacodegeeks.snippets.desktop;
 
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class fieldLines {
    public static void main(String[] args) throws IOException, InterruptedException {
        int width = 2000;
        int height = 2000;
        int ZOOM = 5 * width * height / 1000;
        int precision = 1;

        int steps = 5000;
        int radius = 30;
        double howMany = 2*Math.PI / 15;
    
        double[][] field = new double [15][3];  // {(x, y): mass}

        // field = pointCharge(width, height);
        // field = dipole(width, height);
        // field = plane(width, height); // if using this one I recommend turning down howMany
        field = capacitor(width, height);

        // Random rand = new Random();
        // for (int i = 0; i < 5; i++) {
        //     field[i][0] = rand.nextInt(400, width - 400);
        //     field[i][1] = rand.nextInt(400, height - 400);
        //     field[i][2] = 1*(Math.pow(10, 5));
        //     field[i][2] = 1*(Math.pow(10, 5))*rand.nextInt(-1, 1);;
        //     if (field[i][2] == 0) {
        //         field[i][2] = 1*(Math.pow(10, 5));
        //     }
        // }

        double biggest = 0;
        for (int f = 0; f < field.length; f++) { // m in field.values():
            if (field[f][2] > biggest) {
                biggest = field[f][2];
            }
        }
        double MASS = biggest / (Math.pow(10, 5)); // largest mass / constant
        double largestG = 0;

        final long startTime = System.currentTimeMillis();
        List<ArrayList<Double>> list = new ArrayList<ArrayList<Double>>(); 
        for (int x = 0; x < width; x += precision) {
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
                ArrayList<Double> arr = new ArrayList<Double>(5);
                arr.add(0, g);
                arr.add(1, (double)x);
                arr.add(2, (double)y);
                if (Math.abs(gx) > Math.abs(gy)) {
                    gy = gy / Math.abs(gx);
                    if (gx >= 0) {
                        arr.add(3, 1d);
                        arr.add(4, gy);
                    } else {
                        arr.add(3, -1d);
                        arr.add(4, gy);
                    }
                } else if (Math.abs(gy) > Math.abs(gx)) {
                    gx = gx / Math.abs(gy);
                    if (gy >= 0) {
                        arr.add(3, 2d);
                        arr.add(4, gx);
                    } else {
                        arr.add(3, -2d);
                        arr.add(4, gx);
                    }
                } else {
                    arr.add(3, 3d);
                }
                list.add(arr);
            }
        }

        for (ArrayList<Double> arr : list) {
            if (arr.get(0)  > largestG) {
                largestG = arr.get(0);
            }
        }
        double gradient = Math.pow(largestG, 0.5);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D screen = bufferedImage.createGraphics(); 

        int x;
        int y;
        for (int i = 0; i < list.size(); i ++) {
            ArrayList<Double> arr = list.get(i);
            x = arr.get(1).intValue();
            y = arr.get(2).intValue();
            double g = arr.get(0) * ZOOM;
            if (g <= gradient) {
                int[] colors = allColors(g, gradient);
                screen.setColor(new Color(colors[0], colors[1], colors[2]));
                screen.fillRect(x, y, precision, precision);

            } else if (g <= largestG) {
                int[] colors = allColors(g, largestG);
                screen.setColor(new Color(colors[0], colors[1], colors[2]));
                screen.fillRect(x, y, precision, precision);
            }
        }

        // draw a line
        boolean kill = false;
        double dir = -1;
        double pixx = field[0][0];
        double pixy = field[0][1];
        int changeDir;

        for (int n = 0; n < field.length; n++) {
            pixx = field[n][0];
            pixy = field[n][1];
            if (field[n][2] > 0) {
                changeDir = 1;
            } else {
                changeDir = -1;
            }
            for (double k = 0; k < 2*Math.PI; k+=howMany) {
                double xrad = (int)(radius*Math.cos(k) + pixx);
                double yrad = (int)(radius*Math.sin(k) + pixy);

                if (xrad > width || yrad > height) {
                    continue;
                }

                int j = height*(int)Math.abs(xrad) + (int)Math.abs(yrad);

                screen.setColor(new Color(255, 255, 255));
                for (int i = 0; i < steps; i++) {
                    dir = list.get(j).get(3);
                    if (changeDir == 1) {
                        if (dir == -1) {
                            xrad += 1;
                            yrad -= list.get(j).get(4);
                        } else if (dir == 1) {
                            xrad -= 1;
                            yrad -= list.get(j).get(4);
                        }
                        else if (dir == -2) {
                            yrad += 1;
                            xrad -= list.get(j).get(4);
                        } else if (dir == 2) {
                            yrad -= 1;
                            xrad -= list.get(j).get(4);
                        }
                    } else {
                        // if (dir == -1) {
                        //     xrad += 1*changeDir;;
                        //     yrad -= list.get(j).get(4)*changeDir;
                        // } else if (dir == 1) {
                        //     xrad -= 1*changeDir;;
                        //     yrad -= list.get(j).get(4)*changeDir;
                        // }
                        // else if (dir == -2) {
                        //     yrad += 1*changeDir;;
                        //     xrad -= list.get(j).get(4)*changeDir;
                        // } else if (dir == 2) {
                        //     yrad -= 1*changeDir;
                        //     xrad -= list.get(j).get(4)*changeDir;
                        // }
                    }
                    if (width - xrad < 5) {
                        xrad = 6;
                    }
                    if (xrad < 5) {
                        xrad = width - 6;
                    }
                    if (yrad < 5) {
                        xrad = Math.abs(width-xrad);
                        yrad += 1;
                    }
                    if (yrad > height - 5) {
                        xrad = Math.abs(width-xrad);
                        yrad -= 1;
                    }

                    j = height*(int)Math.abs(xrad) + (int)Math.abs(yrad);
                    if (j >= width*height || j < 0) {
                        System.out.println("Error. Line drawer out of bounds");
                        break;
                    } else {
                        screen.fillRect((int)xrad, (int)yrad, 2, 2);
                    }

                    // might make code faster might make slower
                    for (int p = 0; p < field.length; p++) {
                        if (field[p][0] != pixx) {
                            double disx = Math.abs(field[p][0] - xrad);
                            double disy = Math.abs(field[p][1] - yrad);
                            if (disx*disx+disy*disy < radius*radius) {
                                kill = true;
                                break;
                            }
                        }
                    }
                    if (kill) {
                        kill = false;
                        break;
                    }
                }
            }
        }

        // Disposes of this graphics context and releases any system resources that it is using. 
        screen.dispose();
 
        // Save as PNG
        File file = new File("fields/myimage.png");
        ImageIO.write(bufferedImage, "png", file);
        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime));
 
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

    public static double normalize(double max, double min, double num) {
        return ((num - min) / (max - min));
    }

    public static double[][] dipole(double width, double height) {
        double[][] field = new double [2][3];  // {(x, y): mass}
        field[0][0] = (int) (1*width  / 3);
        field[0][1] = (int) (1*height / 2);
        field[0][2] = (int) (1*(Math.pow(10, 5)));

        field[1][0] = (int) (2*width  / 3);
        field[1][1] = (int) (1*height / 2);
        field[1][2] = (int)-(1*(Math.pow(10, 5)));
        return field;
    }
    public static double[][] pointCharge(double width, double height) {
        double[][] field = new double [1][3];  // {(x, y): mass}
        field[0][0] = (int) (1*width  / 2);
        field[0][1] = (int) (1*height / 2);
        field[0][2] = (int) (1*(Math.pow(10, 5)));
        return field;
    }
    public static double[][] plane(double width, double height) {
        double[][] field = new double [80][3];  // {(x, y): mass}
        for (int i = 0; i < 40; i++) {
            field[i][0]   = (int) (1*width/2);
            field[i][1]   = (int) (i*height / field.length + height/4);
            field[i][2]   = (int) (1*(Math.pow(10, 5)));
        }
        return field;
    }
    public static double[][] capacitor(double width, double height) {
        double[][] field = new double [80][3];  // {(x, y): mass}
        for (int i = 0; i < 40; i+=2) {
            field[i][0]   = (int) (9*width/20);
            field[i][1]   = (int) (i*height / field.length + height/4);
            field[i][2]   = (int) (1*(Math.pow(10, 5)));
            field[i+1][0] = (int) (11*width/20);
            field[i+1][1] = (int) (i*height / field.length + height/4);
            field[i+1][2] = (int) (-1*(Math.pow(10, 5)));
        }
        return field;
    }
}
