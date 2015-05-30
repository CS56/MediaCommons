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

import edu.smc.mediacommons.modules.PastebinModule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class PastebinPanel extends JPanel {

    private PastebinModule pastebinModule;

    public PastebinPanel(String apiKey, final String username, String password) {
        setLayout(null);

        pastebinModule = new PastebinModule(apiKey);

        try {
            pastebinModule.login(username, password);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final JTextArea pasteArea = new JTextArea();

        JScrollPane pastePane = new JScrollPane(pasteArea);
        pastePane.setBounds(10, 10, 400, 250);
        add(pastePane);

        final JComboBox<String> comboBox = new JComboBox<>(new String[]{"Public", "Private", "Unlisted"});
        comboBox.setBounds(10, 270, 70, 20);
        add(comboBox);

        JButton pasteButton = new JButton("Paste");
        pasteButton.setBounds(80, 270, 70, 20);
        add(pasteButton);

        pasteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = pasteArea.getText();

                if (text == null) {
                    JOptionPane.showMessageDialog(getParent(), "The paste was empty!");
                } else {
                    PastebinModule.PastebinModes pastebinModes = null;

                    switch(comboBox.getSelectedItem().toString()) {
                        case "Public":
                            pastebinModes = PastebinModule.PastebinModes.PUBLIC;
                            break;
                        case "Private":
                            pastebinModes = PastebinModule.PastebinModes.PRIVATE;
                            break;
                        case "Unlisted":
                            pastebinModes = PastebinModule.PastebinModes.UNLISTED;
                            break;
                    }

                    try {
                        pastebinModule.makePaste(text, pastebinModes, new Date(System.currentTimeMillis()) + " Paste", "text");
                        JOptionPane.showMessageDialog(getParent(), "Your paste has been posted at\nhttp://pastebin.com/u/" + username);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}