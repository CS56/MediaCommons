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

package edu.smc.mediacommons.displays;

import edu.smc.mediacommons.panels.*;
import edu.smc.mediacommons.Resources;
import edu.smc.mediacommons.displays.templates.BackgroundPanel;
import edu.smc.mediacommons.displays.templates.ImagePanel;

import javax.swing.*;
import java.awt.*;

public class MainDisplay extends JFrame {

    private Image buffered = Resources.IMAGE_BACKGROUND;

    public MainDisplay() {
        super("Media Commons");
        setLayout(null);
        setContentPane(new BackgroundPanel(buffered));
        setIconImage(Resources.IMAGE_ICON);
        setPreferredSize(new Dimension(500, 500));

        // Add the "MediaCommons" Logo
        JPanel appLogo = new ImagePanel(Resources.IMAGE_LOGO, 500, 200);
        appLogo.setBounds(-5, -30, 500, 200);
        appLogo.setOpaque(true);
        appLogo.setBackground(new Color(0, 0, 0, 0));
        add(appLogo);

        // The Application Tabs
        JTabbedPane appTabs = new JTabbedPane();
        appTabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        appTabs.addTab("About", new AboutPanel());
        appTabs.addTab("System", new SystemPanel());
        appTabs.addTab("Weather", new WeatherPanel());
        appTabs.addTab("Passwords", new PasswordPanel());
        // appTabs.addTab("Pastebin", new PastebinPanel("API_KEY", "USERNAME", "PASSWORD")); You must supply these credentials
        appTabs.addTab("Checksums", new ChecksumsPanel());
        appTabs.addTab("Dictionary", new DictionaryPanel());
        // appTabs.addTab("Twitter", new TwitterPanel());
        appTabs.addTab("Security", new SecurityPanel());
        appTabs.addTab("Units", new UnitsPanel());
        appTabs.setBounds(33, 71, 430, 376);
        add(appTabs);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(buffered, 0, 0, null);
    }
}