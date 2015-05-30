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

package edu.smc.mediacommons.panels;

import java.awt.Graphics;
import java.awt.Image;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.TimeZone;

import javax.swing.JLabel;
import javax.swing.JPanel;

import lombok.AllArgsConstructor;

import com.sun.management.OperatingSystemMXBean;

import edu.smc.mediacommons.Resources;
import edu.smc.mediacommons.Utils;
import edu.smc.mediacommons.modules.SystemModule;

public class SystemPanel extends JPanel {

    public SystemPanel() {
        setLayout(null);

        add(Utils.createLabel("System Specs:", 10, 155, 200, 20, Resources.VERDANA_14_BOLD));
        add(Utils.createLabel("Operating System: " + System.getProperty("os.name"), 10, 175, 200, 15, null));
        add(Utils.createLabel("Java Version: " + Runtime.class.getPackage().getImplementationVersion(), 10, 190, 200, 15, null));
        add(Utils.createLabel("System Properties:", 200, 155, 200, 20, Resources.VERDANA_14_BOLD));
        add(Utils.createLabel("Architecture: " + System.getProperty("os.arch"), 200, 175, 200, 15, null));
        add(Utils.createLabel("Bit Runtime: " + System.getProperty("sun.arch.data.model"), 200, 190, 200, 15, null));
        add(Utils.createLabel("Cores: " + SystemModule.getCores(), 200, 205, 200, 15, null));
        add(Utils.createLabel("IP Address: " + Utils.getExternalIP(), 200, 220, 200, 15, null));
        add(Utils.createLabel("MAC Address: " + Utils.getMac(), 200, 235, 200, 15, null));
        add(Utils.createLabel("TimeZone: " + TimeZone.getDefault().getDisplayName(), 200, 250, 200, 15, null));
        add(Utils.createLabel("System Memory:", 10, 205, 200, 20, Resources.VERDANA_14_BOLD));
        add(Utils.createLabel("CPU:", 10, 275, 200, 20, Resources.VERDANA_14_BOLD));
        add(Utils.createLabel("Total Memory: " + SystemModule.getMaxMemory() + " (" + Utils.convertToStringRepresentation(SystemModule.getMaxMemory()) + ")", 10, 225, 200, 20, null));

        final JLabel freeMemory = Utils.createLabel("Free Memory: Loading...", 10, 240, 200, 20, null);
        final JLabel usedMemory = Utils.createLabel("Used Memory: Loading...", 10, 255, 200, 20, null);
        final JLabel javaPercent = Utils.createLabel("Java %s: Loading...", 10, 290, 200, 20, null);
        final JLabel sysPercent = Utils.createLabel("SYS %s: Loading...", 10, 305, 200, 20, null);

        add(freeMemory);
        add(usedMemory);
        add(javaPercent);
        add(sysPercent);

        new SystemUpdater(freeMemory, usedMemory, javaPercent, sysPercent).start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Resources.IMAGE_COMPUTER.getScaledInstance(100, 100, Image.SCALE_SMOOTH), 160, 25, null);
    }

    @AllArgsConstructor
    class SystemUpdater extends Thread {

        private JLabel freeMemory;
        private JLabel usedMemory;
        private JLabel javaPercent;
        private JLabel sysPercent;

        @Override
        public void run() {
            final DecimalFormat decimalFormat = new DecimalFormat("#.#");
            final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

            while(true) {
                freeMemory.setText("Free Memory: " + SystemModule.getFreeMemory() + " (" + Utils.convertToStringRepresentation(SystemModule.getFreeMemory()) + ")");
                usedMemory.setText("Used Memory: " + SystemModule.getTotalMemory() + " (" + Utils.convertToStringRepresentation(SystemModule.getTotalMemory()) + ")");
                javaPercent.setText("Java %: " +  decimalFormat.format((osBean.getProcessCpuLoad() * 100)));
                sysPercent.setText("SYS %: " + decimalFormat.format(osBean.getSystemCpuLoad() * 100));

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}