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
import edu.smc.mediacommons.modules.DictionaryModule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DictionaryPanel extends JPanel {

    private DictionaryModule dictionaryModule = new DictionaryModule();

    public DictionaryPanel() {
        setLayout(null);

        final JLabel info = Utils.createLabel("Enter a word to search", 70, 10, 150, 20, null);
        add(info);

        final JTextField textSearch = Utils.createTextField(70, 30, 200, 20);
        add(textSearch);

        JButton searchButton = Utils.createButton("Search", 270, 30, 80, 20, null);
        add(searchButton);

        final JTextArea resultsText = new JTextArea();
        JScrollPane resultsPane = new JScrollPane(resultsText);
        resultsPane.setBounds(15, 70, 400, 250);
        resultsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(resultsPane);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String text = textSearch.getText();

                if (text.isEmpty()) {
                    JOptionPane.showMessageDialog(getParent(), "The search field was empty!");
                } else {
                    dictionaryModule.queryWord(text.split(" ")[0], new DictionaryModule.DictionaryCallback() {
                        @Override
                        public void dataReceived(List<DictionaryModule.DictionaryData> dictionaryData) {
                            if (dictionaryData.isEmpty()) {
                                resultsText.setText("No results were found for " + text.split(" ")[0]);
                            } else {
                                StringBuilder builder = new StringBuilder();

                                for (int index = 0; index < dictionaryData.size(); index++) {
                                    DictionaryModule.DictionaryData data = dictionaryData.get(index);
                                    builder.append("Word: " + data.getWord() + "\nWord Type: " + data.getWordtype() + "\nDefinition: " + data.getDefinition() + "\n\n");
                                }

                                resultsText.setText(builder.toString());
                            }
                        }
                    });
                }
            }
        });
    }
}