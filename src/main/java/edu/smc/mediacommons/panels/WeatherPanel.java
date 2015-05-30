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

import edu.smc.mediacommons.Resources;
import edu.smc.mediacommons.Utils;
import edu.smc.mediacommons.displays.templates.ImagePanel;
import edu.smc.mediacommons.modules.WeatherModule;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherPanel extends JPanel {

    private ImagePanel cartoonRepresentation;

    public WeatherPanel() {
        setLayout(null);

        add(Utils.createLabel("Enter a ZIP Code or City, then Search", 35, 20, 300, 20, Resources.VERDANA_12_BOLD));

        final JTextField searchBox = Utils.createTextField(35, 40, 200, 20);
        add(searchBox);

        add(Utils.createLabel("Region", 30, 80, 200, 20, Resources.VERDANA_12_BOLD));
        add(Utils.createLabel("Country", 30, 105, 200, 20, Resources.VERDANA_12_BOLD));
        add(Utils.createLabel("City", 30, 130, 200, 20, Resources.VERDANA_12_BOLD));
        add(Utils.createLabel("Sunset", 30, 155, 200, 20, Resources.VERDANA_12_BOLD));
        add(Utils.createLabel("Sunrise", 30, 180, 200, 20, Resources.VERDANA_12_BOLD));
        add(Utils.createLabel("Forecast", 30, 205, 200, 20, Resources.VERDANA_12_BOLD));
        add(Utils.createLabel("High", 30, 230, 200, 20, Resources.VERDANA_12_BOLD));
        add(Utils.createLabel("Low", 30, 255, 200, 20, Resources.VERDANA_12_BOLD));

        final JTextField regionBox = Utils.createTextField(100, 80, 100, 20);
        add(regionBox);

        final JTextField countryBox = Utils.createTextField(100, 105, 100, 20);
        add(countryBox);

        final JTextField cityBox = Utils.createTextField(100, 130, 100, 20);
        add(cityBox);

        final JTextField sunsetBox = Utils.createTextField(100, 155, 100, 20);
        add(sunsetBox);

        final JTextField sunriseBox = Utils.createTextField(100, 180, 100, 20);
        add(sunriseBox);

        final JTextField forecastBox = Utils.createTextField(100, 205, 100, 20);
        add(forecastBox);

        final JTextField highBox = Utils.createTextField(100, 230, 100, 20);
        add(highBox);

        final JTextField lowBox = Utils.createTextField(100, 255, 100, 20);
        add(lowBox);

        cartoonRepresentation = new ImagePanel(Resources.IMAGE_SUN, 100, 100);
        cartoonRepresentation.setBounds(250, 70, 200, 200);
        add(cartoonRepresentation);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(235, 40, 150, 20);
        add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchBox.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(getParent(), "Warning! Your input was blank.");
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            JSONObject result = new WeatherModule(searchBox.getText()).getResponse();

                            try {
                                JSONObject query = new JSONObject(result.getString("query"));
                                JSONObject results = new JSONObject(query.getString("results"));
                                JSONObject channel = new JSONObject(results.getString("channel"));
                                JSONObject location = new JSONObject(channel.getString("location"));
                                JSONObject astronomy = new JSONObject(channel.getString("astronomy"));
                                JSONObject item = new JSONObject(channel.getString("item"));
                                JSONArray array = new JSONArray(item.getString("forecast"));
                                JSONObject forecast = array.getJSONObject(0);

                                regionBox.setText(location.getString("region"));
                                countryBox.setText(location.getString("country"));
                                cityBox.setText(location.getString("city"));
                                sunsetBox.setText(astronomy.getString("sunset"));
                                sunriseBox.setText(astronomy.getString("sunrise"));
                                forecastBox.setText(forecast.getString("text"));
                                highBox.setText(forecast.getString("high"));
                                lowBox.setText(forecast.getString("low"));

                                String partial = forecast.getString("text");

                                if (partial.contains("Cloud")) {
                                    cartoonRepresentation.setScaled(Resources.IMAGE_CLOUDY);
                                } else if (partial.contains("Rain")) {
                                    cartoonRepresentation.setScaled(Resources.IMAGE_RAINY);
                                }

                                cartoonRepresentation.repaint();
                            } catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
        });
    }
}