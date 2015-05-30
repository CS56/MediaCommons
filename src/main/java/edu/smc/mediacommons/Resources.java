/**
* The MIT License (MIT)
* 
* Copyright (c) 2015 CS56
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
 */

package edu.smc.mediacommons;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {

    public static Font VERDANA_16_BOLD = new Font("Verdana", Font.BOLD, 16);
    public static Font VERDANA_14_BOLD = new Font("Verdana", Font.BOLD, 14);
    public static Font VERDANA_12_BOLD = new Font("Verdana", Font.BOLD, 12);

    public static BufferedImage IMAGE_COMPUTER;
    public static BufferedImage IMAGE_ICON;
    public static BufferedImage IMAGE_LOGO;
    public static BufferedImage IMAGE_BACKGROUND;
    public static BufferedImage IMAGE_SUN;
    public static BufferedImage IMAGE_CLOUDY;
    public static BufferedImage IMAGE_RAINY;
    public static BufferedImage IMAGE_TWITTER_HOME;
    public static BufferedImage IMAGE_TWITTER_PROFILE;
    public static BufferedImage IMAGE_TWITTER_TIMELINE;
    public static BufferedImage IMAGE_TWITTER_TWEET;
    public static BufferedImage TWITTER_BUTTON;

    private static String PATH = "edu/smc/mediacommons/resources/";

    static {
        try {
            ClassLoader loader = Main.class.getClassLoader();

            IMAGE_COMPUTER = ImageIO.read(loader.getResource(PATH + "computer.png"));
            IMAGE_ICON = ImageIO.read(loader.getResource(PATH + "icon.png"));
            IMAGE_LOGO = ImageIO.read(loader.getResource(PATH + "logo.png"));
            IMAGE_BACKGROUND = ImageIO.read(loader.getResource(PATH + "background.png"));
            IMAGE_SUN = ImageIO.read(loader.getResource(PATH + "sun.png"));
            IMAGE_CLOUDY = ImageIO.read(loader.getResource(PATH + "partlycloudy.png"));
            IMAGE_RAINY = ImageIO.read(loader.getResource(PATH + "rainy.png"));
            IMAGE_TWITTER_HOME = ImageIO.read(loader.getResource(PATH + "homelogo.png"));
            IMAGE_TWITTER_PROFILE = ImageIO.read(loader.getResource(PATH + "profile.png"));
            IMAGE_TWITTER_TIMELINE = ImageIO.read(loader.getResource(PATH + "timeline.png"));
            IMAGE_TWITTER_TWEET = ImageIO.read(loader.getResource(PATH + "tweet.png"));
            TWITTER_BUTTON = ImageIO.read(loader.getResource(PATH + "twitterButton.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}