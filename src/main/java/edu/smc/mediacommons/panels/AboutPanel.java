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

import edu.smc.mediacommons.displays.templates.CenteredText;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;

public class AboutPanel extends JPanel {
    
    public AboutPanel() {
        setVisible(true);
        setLayout(new BorderLayout());
        
        Font font = new Font("Verdana", Font.BOLD, 16);

        CenteredText title = new CenteredText();
        title.setFont(font);
        title.setEditable(false);
        title.setBackground(null);
        title.setText("MediaCommons");
        
        CenteredText description = new CenteredText();
        description.setEditable(false);
        description.setBackground(null);
        description.setText("MediaCommons is an easy to use utility application that combines"
                    + "\nvarious utilities which access Public APIs or Java libraries."
                    + "\n"
                    + "\nThis application is modularized into different components."
                    + "\nEach \"component\" can be considered a mini utility. They range"
                    + "\nfrom displaying system information, to retrieving real time"
                    + "\ninformation from external servers around the world."
                    + "\n"
                    + "\nThe source code is available at GitHub.com/CS56/MediaCommons"
                    + "\n"
                    + "\nProject Members:"
                    + "\nAndres (Developer)"
                    + "\nFillipo (Developer)"
                    + "\nYoonho (Developer)"
                    + "\nArnold (Developer)"
                    + "\nEmil (Documenter)"
                    + "\nAmin (Tester)");

        add(title, BorderLayout.NORTH);
        add(description, BorderLayout.CENTER);
    }
}