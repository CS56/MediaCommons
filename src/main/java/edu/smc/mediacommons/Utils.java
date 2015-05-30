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

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.DecimalFormat;

public class Utils {

    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    public static String getPasswordInput(Container container) {
        JPasswordField password = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(container, password, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            return new String(password.getPassword());
        }

        return null;
    }

    public static String getTextInput(Container container) {
        JTextArea jTextArea = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setSize(new Dimension(300, 200));
        jScrollPane.setPreferredSize(new Dimension(300, 200));

        int option = JOptionPane.showConfirmDialog(container, jScrollPane, "Enter Tweet", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            return jTextArea.getText();
        }

        return null;
    }

    public static void writeToFile(String text, File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JTextField createTextField(int x, int y, int width, int height) {
        JTextField jTextField = new JTextField();
        jTextField.setBounds(x, y, width, height);
        return jTextField;
    }

    public static JLabel createLabel(String name, int x, int y, int width, int height, Font font) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y, width, height);

        if (font != null) {
            label.setFont(font);
        }

        return label;
    }

    public static JButton createButton(String name, int x, int y, int width, int height, ImageIcon imageIcon) {
        JButton jButton = imageIcon == null ? new JButton(name) : new JButton(imageIcon);
        jButton.setBounds(x, y, width, height);
        return jButton;
    }

    public static String getMac() {
        StringBuilder sb = new StringBuilder();

        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
        } catch (SocketException | UnknownHostException e) {
            
        }

        return sb.toString();
    }

    public static String getExternalIP() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            String ip = in.readLine();
            in.close();
            return ip;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertToStringRepresentation(final long value) {
        final long[] dividers = new long[]{T, G, M, K, 1};
        final String[] units = new String[]{"TB", "GB", "MB", "KB", "B"};
        
        if (value < 1) {
            throw new IllegalArgumentException("Invalid file size: " + value);
        }
            
        String result = null;
        
        for (int i = 0; i < dividers.length; i++) {
            final long divider = dividers[i];
            if (value >= divider) {
                result = format(value, divider, units[i]);
                break;
            }
        }
        
        return result;
    }

    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException
                | InstantiationException | IllegalAccessException e) {
            // ignore
        }
    }

    private static String format(final long value, final long divider, final String unit) {
        final double result = divider > 1 ? (double) value / (double) divider : (double) value;
        return new DecimalFormat("#,##0.#").format(result) + " " + unit;
    }
}