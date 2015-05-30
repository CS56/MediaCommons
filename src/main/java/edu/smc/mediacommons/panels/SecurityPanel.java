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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.io.IOUtils;
import org.jasypt.util.text.BasicTextEncryptor;

import edu.smc.mediacommons.Utils;

public class SecurityPanel extends JPanel {

    private final JFileChooser FILE_CHOOSER;

    public SecurityPanel() {
        setLayout(null);

        FILE_CHOOSER = new JFileChooser();

        JButton openFile = Utils.createButton("Open File", 10, 20, 100, 30, null);
        add(openFile);

        JButton saveFile = Utils.createButton("Save File", 110, 20, 100, 30, null);
        add(saveFile);

        JButton decrypt = Utils.createButton("Decrypt", 210, 20, 100, 30, null);
        add(decrypt);

        JButton encrypt = Utils.createButton("Encrypt", 310, 20, 100, 30, null);
        add(encrypt);

        JTextField path = Utils.createTextField(10, 30, 300, 20);
        path.setText("No file selected");

        final JTextArea viewer = new JTextArea();
        JScrollPane pastePane = new JScrollPane(viewer);
        pastePane.setBounds(15, 60, 400, 200);
        add(pastePane);

        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FILE_CHOOSER.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    File toRead = FILE_CHOOSER.getSelectedFile();

                    if (toRead == null) {
                        JOptionPane.showMessageDialog(getParent(), "The input file does not exist!", "Opening Failed...", JOptionPane.WARNING_MESSAGE);
                    } else {
                        try {
                            List<String> lines = IOUtils.readLines(new FileInputStream(toRead), "UTF-8");

                            viewer.setText("");

                            for (String line : lines) {
                                viewer.append(line);
                            }
                        } catch (IOException ex) {

                        }
                    }
                }
            }
        });

        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FILE_CHOOSER.showSaveDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    File toWrite = FILE_CHOOSER.getSelectedFile();

                    Utils.writeToFile(viewer.getText(), toWrite);
                    JOptionPane.showMessageDialog(getParent(), "The file has now been saved to\n" + toWrite.getPath());
                }
            }
        });

        encrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = Utils.getPasswordInput(getParent());

                if (password != null) {
                    try {
                        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
                        basicTextEncryptor.setPassword(password);

                        String text = basicTextEncryptor.encrypt(viewer.getText());
                        viewer.setText(text);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(getParent(), "Could not encrypt the text, an unexpected error occurred.", "Encryption Failed...", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(getParent(), "Could not encrypt the text, as no\npassword has been specified.", "Encryption Failed...", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        decrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = Utils.getPasswordInput(getParent());

                if (password != null) {
                    try {
                        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
                        basicTextEncryptor.setPassword(password);

                        String text = basicTextEncryptor.decrypt(viewer.getText());
                        viewer.setText(text);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(getParent(), "Could not decrypt the text, an unexpected error occurred.", "Decryption Failed...", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(getParent(), "Could not decrypt the text, as no\npassword has been specified.", "Decryption Failed...", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}