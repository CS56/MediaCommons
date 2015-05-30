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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.AttributedString;

public class PasswordPanel extends JPanel {

    public PasswordPanel() {
        setLayout(null);

        add(Utils.createLabel("Enter a Password to Test", 60, 30, 200, 20, null));

        final JButton test = Utils.createButton("Test", 260, 50, 100, 20, null);
        add(test);

        final JPasswordField fieldPassword = new JPasswordField(32);
        fieldPassword.setBounds(60, 50, 200, 20);
        add(fieldPassword);

        final PieDataset dataset = createSampleDataset(33, 33, 33);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(300, 300));
        chartPanel.setBounds(45, 80, 340, 250);

        add(chartPanel);

        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(fieldPassword.getPassword());
                
                if (password.isEmpty() || password == null) {
                    JOptionPane.showMessageDialog(getParent(), "Warning! The input was blank!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else {
                    int letterCount = 0, numberCount = 0, specialCount = 0, total = password.length();

                    for (char c : password.toCharArray()) {
                        if (Character.isLetter(c)) {
                            letterCount++;
                        } else if (Character.isDigit(c)) {
                            numberCount++;
                        } else {
                            specialCount++;
                        }
                    }

                    long totalCombinations = 0;
                    double percentLetters = 0;
                    double percentNumbers = 0;
                    double percentCharacters = 0;

                    if (letterCount > 0) {
                        totalCombinations += (factorial(26) / factorial(26 - letterCount));
                        percentLetters = (letterCount+0.0 / total);
                    }

                    if (numberCount > 0) {
                        totalCombinations += (factorial(10) / factorial(10 - numberCount));
                        percentNumbers = (numberCount+0.0 / total);
                    }

                    if (specialCount > 0) {
                        totalCombinations += (factorial(40) / factorial(40 - specialCount));
                        percentCharacters = (specialCount+0.0 / total);
                    }

                    PieDataset dataset = createSampleDataset(percentLetters, percentNumbers, percentCharacters);
                    JFreeChart chart = createChart(dataset);
                    chartPanel.setChart(chart);

                    JOptionPane.showMessageDialog(getParent(), "Total Combinations: " + totalCombinations + "\nAssuming Rate Limited, Single: " + (totalCombinations / 1000) + " seconds" +
                            "\n\nBreakdown:\nLetters: " + percentLetters + "%\nNumbers: " + percentNumbers + "%\nCharacters: " + percentCharacters + "%");
                }
            }
        });

        setVisible(true);
    }

    public static double factorial(int n) {
        double multi = 1;
        for (int i = 1; i <= n; i++) {
            multi = multi * i;
        }
        return multi;
    }

    private PieDataset createSampleDataset(double letters, double numbers, double special) {
        final DefaultPieDataset result = new DefaultPieDataset();

        if (letters > 0) {
            result.setValue("Letters", letters);
        }

        if (numbers > 0) {
            result.setValue("Numbers", numbers);
        }

        if (special > 0) {
            result.setValue("Special", special);
        }

        return result;
    }

    private JFreeChart createChart(final PieDataset dataset) {
        final JFreeChart chart = ChartFactory.createPieChart3D(
                "Password Pie Chart",   // chart title
                dataset,                // data
                true,                   // include legend
                true,
                false
        );

        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("No data to display");
        plot.setLabelGenerator(new CustomLabelGenerator());
        return chart;
    }
}

class CustomLabelGenerator implements PieSectionLabelGenerator {

    public String generateSectionLabel(final PieDataset dataset, final Comparable key) {
        String result = null;
        if (dataset != null) {
            result = key.toString();
        }
        return result;
    }

    @Override
    public AttributedString generateAttributedSectionLabel(PieDataset pieDataset, Comparable comparable) {
        return null;
    }
}