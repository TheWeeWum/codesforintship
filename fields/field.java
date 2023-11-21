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

public class field {
    public static void main(String[] args) throws IOException, InterruptedException {
        int width = 5000;
        int height = 5000;
        int ZOOM = 5 * width * height / 1000;
        int precision = 1;
        // double disperpix = 1000;
    
        double[][] field = new double [15][3];  // {(x, y): mass}
        if (true) {
            // sun, earth, moon
            // field[0][0] = 19*width /20;
            // field[0][1] = 1*height/2;
            // field[0][2] = 2*(Math.pow(10, 30));
            // field[1][0] = 19*width /20 - 14731 / 2;
            // field[1][1] = 1*height/2;
            // field[1][2] = 6*(Math.pow(10, 24));
            // field[2][0] = 19*width /20 - 14731 / 2;
            // field[2][1] = 1*height/2 - 38 / 2;
            // field[2][2] = 7.35*(Math.pow(10, 22));

            // field[0][0] = 1*width /2 + 50;
            // field[0][1] = 1*height/2;
            // field[0][2] = 1*(Math.pow(10, 5));
            // field[1][0] = 1*width /2 - 50;
            // field[1][1] = 1*height/2;
            // field[1][2] = 1*(Math.pow(10, 5));

            // field[1 + 3][0] = 4*width /7;
            // field[1 + 3][1] = 1*height/2;
            // field[1 + 3][2] = 6*(Math.pow(10, 21));
            // field[2 + 3][0] = 10*width /19;
            // field[2 + 3][1] = 5*height/9;
            // field[2 + 3][2] = 1*(Math.pow(10, 21));
        }

        Random rand = new Random();
        for (int i = 0; i < 15; i++) {
            field[i][0] = rand.nextInt(400, width - 400);
            field[i][1] = rand.nextInt(400, height - 400);
            field[i][2] = 1*(Math.pow(10, 5));
        }
        
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
        List<ArrayList<Double>> listA = new ArrayList<ArrayList<Double>>(); 
        List<ArrayList<Double>> listB = new ArrayList<ArrayList<Double>>();  
        List<ArrayList<Double>> listC = new ArrayList<ArrayList<Double>>(); 
        List<ArrayList<Double>> listD = new ArrayList<ArrayList<Double>>();  

        ThreadForLoopA threadA = new ThreadForLoopA(height, width, precision, field, MASS, listA);
        ThreadForLoopB threadB = new ThreadForLoopB(height, width, precision, field, MASS, listB);
        ThreadForLoopC threadC = new ThreadForLoopC(height, width, precision, field, MASS, listC);
        ThreadForLoopD threadD = new ThreadForLoopD(height, width, precision, field, MASS, listD);
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        threadA.join();
        threadB.join();
        threadC.join();
        threadD.join();

        list.addAll(threadA.list);
        list.addAll(threadB.list);
        list.addAll(threadC.list);
        list.addAll(threadD.list);

        System.out.println(list.size());

        for (ArrayList<Double> arr : list) {
            if (arr.get(0)  > largestG) {
                largestG = arr.get(0);
            }
        }
        double gradient = Math.pow(largestG, 0.5);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D screen = bufferedImage.createGraphics(); 

        // doesn't work properly, seems to pixelate the image
        // and sometimes just leaves sections blank
        // does run about 20% faster though
        // ThreadForColorA threadForColorA = new ThreadForColorA(ZOOM, gradient, screen, precision, largestG, list);
        // ThreadForColorB threadForColorB = new ThreadForColorB(ZOOM, gradient, screen, precision, largestG, list);
        // ThreadForColorC threadForColorC = new ThreadForColorC(ZOOM, gradient, screen, precision, largestG, list);
        // ThreadForColorD threadForColorD = new ThreadForColorD(ZOOM, gradient, screen, precision, largestG, list);
        // threadForColorA.start();
        // threadForColorB.start();
        // threadForColorC.start();
        // threadForColorD.start();
        // threadForColorA.join();
        // threadForColorB.join();
        // threadForColorC.join();
        // threadForColorD.join();

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
}
