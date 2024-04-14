package org.example.thread.performance_optimization;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Latency_ImageProcessing {

    /*_____Recolor all the flowers in the first picture, to the same purple-ish color and make it all look natural._____*/

    /*
    * A digital image is broken into many small single color points called pixels.
    *  This particular picture is 3036 by 4,048 pixels, which is roughly equivalent to 12 million pixels.
    * Each pixels color is represented by four bytes called a RGB, a stands for alpha, which is the transparency value
    *  of the pixel R stands for red G stands for green and B stands for blue using the RGB components
    * we can achieve pretty much any color we want by using only one component and keeping the others at the minimum.
    *
    * So if we look at the white flour, we see that in fact, we have many shades of gray in it and that's how we're going
    * to identify the area we want them.
    * */

    public static final String SOURCE_FILE = "src/main/resources/many-flowers.jpg";
    public static final String DESTINATION_FILE = "src/main/out/many-flowers.jpg";

    public static void main(String[] args) throws IOException {
    /*
    11.
    we're going to read the original image into a BufferedImage object using the ImageIO.read method.

    BufferedImage class represents the image data such as the pixels, colorspace and dimensions,
    and it provides us with convenient methods to manipulate the pixels of the image.
    * */

        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));

        /*
        12.
        Now let's also create the resultImage object, which will store the output image.
         And the constructor we're simply going to set the image, width, height, and colorspace
        * */
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);



        long startTime = System.currentTimeMillis();
        //recolorSingleThreaded(originalImage, resultImage);
        int numberOfThreads = 1;
        recolorMultithreaded(originalImage, resultImage, numberOfThreads);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);

        System.out.println(String.valueOf(duration));
    }

    public static void recolorMultithreaded(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
        List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads;

        for(int i = 0; i < numberOfThreads ; i++) {
            final int threadMultiplier = i;

            Thread thread = new Thread(() -> {
                int xOrigin = 0 ;
                int yOrigin = height * threadMultiplier;

                recolorImage(originalImage, resultImage, xOrigin, yOrigin, width, height);
            });

            threads.add(thread);
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }


    /*
    20.
    The last method we need to implement is the method that iterates through the entire image and applies the recoloring.
    * */
    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner,
                                    int width, int height) {

        /*
        21.
        We simply loop through all the X values between the left corner and the end of the row.
        And for every X value, we iterate over all the Y values starting from the top corner, all the way to the bottom.
        For every combination of X and Y we called the recolor pixel method that we implement that a few moments ago.
        * */
        for(int x = leftCorner ; x < leftCorner + width && x < originalImage.getWidth() ; x++) {
            for(int y = topCorner ; y < topCorner + height && y < originalImage.getHeight() ; y++) {
                recolorPixel(originalImage, resultImage, x , y);
            }
        }
    }
          /*
    14.

    * */

    /*
    13.
    implement the method that is going to recolor each individual pixel. The method is taking a BufferedImage object,
     representing the original image, the result image and the X and Y coordinates of the pixel.
    */
    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        // 14.  first get the RGB value of that particular pixel in the original image.
        int rgb = originalImage.getRGB(x, y);

        //15. Then we break the RGB value of the pixel into the individual red, green, and blue components by calling the helper methods we created earlier.
        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        /*
        16.
        If the current pixel from the original image is a shade of gray, then we increase the red value by 10 units.
         And to make sure that the value does not exceed the maximum value that can be stored in a byte, we take the minimum
         of 255 and the new red value.
         Then were reduced the green value by 80 units. And to make sure that we don't go below zero, we take the maximum of
         that value and zero.
         And in a similar fashion, we decrease the blue value by 20 units and to avoid a negative value. We also take the maximum
         between that value and zero.
        */

        if(isShadeOfGray(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        }
        /*
        17.
           Finally if the current pixel is not a shade of gray. In other words, this pixel is not part of a white flower.
         We simply leave the old colors of the pixel and assign them to the target color values.
        * */
        else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        /*
        18.
        After we made the decision for each individual color component, we combine those values into a single RGB value
         by calling the createRGBFromColors method that we implemented earlier.
        * */
        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);

        /*
        19.
        The last thing we're missing as assigning the newly created RGB value into the result BufferedImage object.
        * */
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs( green - blue) < 30;
    }



    /*
    9.
    The third helper method we're going to implement is - isShadeOfGray method, which takes a particular
     pixels color values, and determines if the pixel is a shade of gray.
    * */
    public static boolean isShadeOfGrey(int red, int green, int blue){
        /*
        10.
        The logic behind this method is checking that all three components have a similar color intensity.
        In other words, if no one particular component is stronger than the rest. So if the color is almost
        a perfect mix of green, red, and blue, we know it's a shade of gray. The arbitrary distance I'm using
         to determine their proximity is 30, which I found empirically.
        * */
        return Math.abs(red - green) < 30 &&
                Math.abs(red - blue) < 30 &&
                Math.abs(green - blue) < 30;
    }

    /*
    4.
    we're going to implement the opposite method, which is taking the individual values of red, green, and blue,
     and building a compound pixel RGB value out of it.
    * */
    public static int createRGBFromColors(int red, int green, int blue){
        int rgb = 0;
        /*
        5.
        Since the blue value is the right most in the aRGB value, we see simply add it by applying
        logical OR between the RGB target value and the blue value.
         */
        rgb |= blue;

        /*
        6.
        Then to add the green value. We first apply a bit shift to the left in order to align
         the green to its place in the pixel value. And then we add the result to the RGB value by using the OR function
        */
        rgb |= green >>8;

        /*
        7.
        And in a similar way, we shift the red value two bites to the left and add it to the RGB value.
        * */
        rgb |= red >>16;


        /*
        8.
        Lastly, we need to set the alpha value to the highest value to make the pixel opaque.
        So we OR it with the appropriate constant. That constant simply has FF, which is 255 in hexadecimal
        in the leftmost byte and zeros in the rest of the bites.
        * */
        rgb |= 0xFF000000;

        return rgb;
    }

    /*
    3.
    And in the similar fashion that we're going to get the red component
    by masking out the alpha green and bloom and shifting the red value 2 bytes,
     which is 16 bits to the right.
    * */
    public static int getRed(int rgb){
        return rgb & 0x00FF0000 >> 16;
    }

    /*
    2.
    Then in a similar way, we're going to implement the getGreen method.
    Which masks out the alpha red and blue components, but because the green component is the second bite from the right,
    we need to shift the value 8 bits to the right.
    * */
    public static int getGreen(int rgb){
        return rgb & 0x0000FF00 >> 8;
    }

    /*
    1.
    The first one is going to be the get blue method that takes an RGB value and extracts
     only the blue value out of the pixel.*/
    public static int getBlue(int rgb){
        return rgb & 0x000000FF;
    }
}
