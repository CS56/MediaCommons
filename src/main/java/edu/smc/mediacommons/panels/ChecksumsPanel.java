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

import edu.smc.mediacommons.Utils;
import edu.smc.mediacommons.modules.Md5Module;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChecksumsPanel extends JPanel {

    private final JFileChooser FILE_CHOOSER = new JFileChooser();

    public ChecksumsPanel() {
        setLayout(null);

        add(Utils.createLabel("Check the above integrity against", 30, 110, 200, 20, null));
        add(Utils.createLabel("MD5: ", 30, 50, 50, 20, null));
        add(Utils.createLabel("MD5: ", 30, 140, 50, 20, null));

        final JTextField outputMD5 = Utils.createTextField(70, 50, 300, 20);
        add(outputMD5);

        final JTextField outputSHA1 = Utils.createTextField(70, 80, 300, 20);
        add(outputSHA1);

        add(Utils.createLabel("SHA-1", 30, 80, 50, 20, null));
        add(Utils.createLabel("SHA-1", 30, 170, 50, 20, null));

        final JTextField inputMD5 = Utils.createTextField(70, 140, 300, 20);
        add(inputMD5);

        final JTextField inputSHA1 = Utils.createTextField(70, 170, 300, 20);
        add(inputSHA1);

        JButton selectFile = Utils.createButton("Select File", 30, 20, 100, 20, null);
        add(selectFile);

        // Listen for clicks
        selectFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FILE_CHOOSER.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    File inputFile = FILE_CHOOSER.getSelectedFile();

                    if (inputFile != null) {
                        outputMD5.setText(Md5Module.getMD5(inputFile));
                        outputSHA1.setText(Md5Module.getSHA1(inputFile));

                        boolean md5 = false;
                        boolean sha1 = false;

                        if (outputMD5.getText().equals(inputMD5.getText())) {
                            md5 = true;
                        }

                        if (outputSHA1.getText().equals(inputSHA1.getText())) {
                            sha1 = true;
                        }

                        JOptionPane.showMessageDialog(getParent(), "Output Results:\nThe MD5 " + (!md5 ? "does not match" : "matches") + "\nThe SHA-1 " + (!sha1 ? "does not match" : "matches"));
                    }
                }
            }
        });
    }
}