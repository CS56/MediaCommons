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

import javax.measure.Measure;
import javax.measure.converter.UnitConverter;
import javax.measure.quantity.*;
import javax.measure.unit.Unit;
import javax.swing.*;

import static javax.measure.unit.NonSI.*;
import static javax.measure.unit.SI.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnitsPanel extends JPanel implements ActionListener {

    private JTextField metf, impf;
    private JComboBox<String> conversion, metb, impb;

    enum UnitType {
        LENGTH, VOLUME, TEMPERATURE, MASS
    }

    public UnitsPanel() {
        setLayout(null);

        String[] options = {"Imperial to Metric", "Metric to Imperial"};
        String[] metric = {"Millimetres", "Centimetres", "Metres", "Kilometres", "Litres", "Kelvin", "Celcius", "Grams", "Kilograms", "Tonnes"};
        String[] imperial = {"Inches", "Feet", "Yards", "Miles", "Fluid Ounces", "US Gallons", "Kelvin", "Fahrenheit", "Ounces", "Pounds", "Tons"};

        conversion = new JComboBox<>(options);
        metb = new JComboBox<>(metric);
        impb = new JComboBox<>(imperial);
        metf = new JTextField();
        impf = new JTextField();

        add(Utils.createLabel("Unit Converter", 150, 80, 200, 40, Resources.VERDANA_16_BOLD));
        add(Utils.createLabel("Metric System", 40, 120, 120, 20, Resources.VERDANA_12_BOLD));
        add(Utils.createLabel("Imperial System", 40, 140, 120, 20, Resources.VERDANA_12_BOLD));

        conversion.setBounds(40, 160, 100, 20);
        metb.setBounds(160, 120, 100, 20);
        impb.setBounds(160, 140, 100, 20);
        metf.setBounds(265, 120, 120, 20);
        impf.setBounds(265, 140, 120, 20);

        add(conversion);
        add(metb);
        add(impb);
        add(metf);
        add(impf);

        JButton convert = Utils.createButton("Convert", 160, 160, 100, 20, null);
        JButton clear = Utils.createButton("Clear", 265, 160, 120, 20, null);

        add(convert);
        add(clear);

        convert.addActionListener(this);
        clear.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(e.getSource() instanceof JButton)) {
            return;
        }

        switch (((JButton) e.getSource()).getText()) {
            case "Convert":
                String metricField = metb.getSelectedItem().toString();
                String imperialField = impb.getSelectedItem().toString();

                double result = 0.0;
                double metricInput = !metf.getText().isEmpty() ? Double.valueOf(metf.getText()) : 0.0;
                double imperialInput = !impf.getText().isEmpty() ? Double.valueOf(impf.getText()) : 0.0;

                boolean metricConversion = conversion.getSelectedItem().toString().equalsIgnoreCase("Metric to Imperial");

                UnitType metricType = getGenericUnitFromMetric(metricField);
                UnitType imperialType = getGenericUnitFromImperial(imperialField);

                if (metricConversion) {
                    if (metricType == UnitType.LENGTH && imperialType == UnitType.LENGTH) {
                        result = getLengthUnitConversion(getLengthUnitFromMetric(metricField), getLengthUnitFromImperial(imperialField), metricInput);
                    } else if (metricType == UnitType.VOLUME && imperialType == UnitType.VOLUME) {
                        result = getVolumeUnitConversion(getVolumeUnitFromMetric(metricField), getVolumeFromImperial(imperialField), metricInput);
                    } else if (metricType == UnitType.TEMPERATURE && imperialType == UnitType.TEMPERATURE) {
                        result = getTemperatureUnitConversion(getTemperatureUnitFromMetric(metricField), getTemperatureFromImperial(imperialField), metricInput);
                    } else if (metricType == UnitType.MASS && imperialType == UnitType.MASS) {
                        result = getMassUnitConversion(getMassUnitFromMetric(metricField), getMassFromImperial(imperialField), metricInput);
                    } else {
                        JOptionPane.showMessageDialog(getParent(), "These units are incommutable. Check the units.", "Conversion Failure", JOptionPane.ERROR_MESSAGE);
                    }

                    impf.setText("" + result);
                } else {
                    if (metricType == UnitType.LENGTH && imperialType == UnitType.LENGTH) {
                        result = getLengthUnitConversion(getLengthUnitFromImperial(imperialField), getLengthUnitFromMetric(metricField), imperialInput);
                    } else if (metricType == UnitType.VOLUME && imperialType == UnitType.VOLUME) {
                        result = getVolumeUnitConversion(getVolumeFromImperial(imperialField), getVolumeUnitFromMetric(metricField), imperialInput);
                    } else if (metricType == UnitType.TEMPERATURE && imperialType == UnitType.TEMPERATURE) {
                        result = getTemperatureUnitConversion(getTemperatureFromImperial(imperialField), getTemperatureUnitFromMetric(metricField), imperialInput);
                    } else if (metricType == UnitType.MASS && imperialType == UnitType.MASS) {
                        result = getMassUnitConversion(getMassFromImperial(imperialField), getMassUnitFromMetric(metricField), imperialInput);
                    } else {
                        JOptionPane.showMessageDialog(getParent(), "These units are incommutable. Check the units.", "Conversion Failure", JOptionPane.ERROR_MESSAGE);
                    }

                    metf.setText("" + result);
                }

                break;
            case "Clear":
                metf.setText("");
                impf.setText("");
                break;
        }
    }

    public static double getVolumeUnitConversion(Unit<Volume> fromUnit, Unit<Volume> toUnit, double initial) {
        UnitConverter conversion = fromUnit.getConverterTo(toUnit);
        return conversion.convert(Measure.valueOf(initial, toUnit).doubleValue(toUnit));
    }

    public static double getTemperatureUnitConversion(Unit<Temperature> fromUnit, Unit<Temperature> toUnit, double initial) {
        UnitConverter conversion = fromUnit.getConverterTo(toUnit);
        return conversion.convert(Measure.valueOf(initial, toUnit).doubleValue(toUnit));
    }

    public static double getMassUnitConversion(Unit<Mass> fromUnit, Unit<Mass> toUnit, double initial) {
        UnitConverter conversion = fromUnit.getConverterTo(toUnit);
        return conversion.convert(Measure.valueOf(initial, toUnit).doubleValue(toUnit));
    }

    public static double getLengthUnitConversion(Unit<Length> fromUnit, Unit<Length> toUnit, double initial) {
        UnitConverter conversion = fromUnit.getConverterTo(toUnit);
        return conversion.convert(Measure.valueOf(initial, toUnit).doubleValue(toUnit));
    }

    public static UnitType getGenericUnitFromImperial(String unit) {
        switch (unit) {
            case "Inches":
                return UnitType.LENGTH;
            case "Feet":
                return UnitType.LENGTH;
            case "Yards":
                return UnitType.LENGTH;
            case "Miles":
                return UnitType.LENGTH;
            case "Fluid Ounces":
                return UnitType.VOLUME;
            case "US Gallons":
                return UnitType.VOLUME;
            case "Kelvin":
                return UnitType.TEMPERATURE;
            case "Fahrenheit":
                return UnitType.TEMPERATURE;
            case "Ounces":
                return UnitType.MASS;
            case "Pounds":
                return UnitType.MASS;
            case "Tons":
                return UnitType.MASS;
            default:
                return null;
        }
    }

    public static UnitType getGenericUnitFromMetric(String unit) {
        switch (unit) {
            case "Millimetres":
                return UnitType.LENGTH;
            case "Centimetres":
                return UnitType.LENGTH;
            case "Metres":
                return UnitType.LENGTH;
            case "Kilometres":
                return UnitType.LENGTH;
            case "Grams":
                return UnitType.MASS;
            case "Kilograms":
                return UnitType.MASS;
            case "Tonnes":
                return UnitType.MASS;
            case "Kelvin":
                return UnitType.TEMPERATURE;
            case "Celcius":
                return UnitType.TEMPERATURE;
            case "Litres":
                return UnitType.VOLUME;
            default:
                return null;
        }
    }

    public static Unit<Length> getLengthUnitFromImperial(String unit) {
        switch (unit) {
            case "Inches":
                return INCH;
            case "Feet":
                return FOOT;
            case "Yards":
                return YARD;
            case "Miles":
                return MILE;
            default:
                return null;
        }
    }

    public static Unit<Volume> getVolumeFromImperial(String unit) {
        switch (unit) {
            case "Fluid Ounces":
                return OUNCE_LIQUID_US;
            case "US Gallons":
                return GALLON_LIQUID_US;
            default:
                return null;
        }
    }

    public static Unit<Temperature> getTemperatureFromImperial(String unit) {
        switch (unit) {
            case "Kelvin":
                return KELVIN;
            case "Fahrenheit":
                return FAHRENHEIT;
            default:
                return null;
        }
    }

    public static Unit<Mass> getMassFromImperial(String unit) {
        switch (unit) {
            case "Ounces":
                return OUNCE;
            case "Pounds":
                return POUND;
            case "Tons":
                return TON_US;
            default:
                return null;
        }
    }

    public static Unit<Length> getLengthUnitFromMetric(String unit) {
        switch (unit) {
            case "Millimetres":
                return MILLIMETER;
            case "Centimetres":
                return CENTIMETER;
            case "Metres":
                return METER;
            case "Kilometres":
                return KILOMETER;
            default:
                return null;
        }
    }

    public static Unit<Mass> getMassUnitFromMetric(String unit) {
        switch (unit) {
            case "Grams":
                return GRAM;
            case "Kilograms":
                return KILOGRAM;
            case "Tonnes":
                return TON_UK;
            default:
                return null;
        }
    }

    public static Unit<Temperature> getTemperatureUnitFromMetric(String unit) {
        switch (unit) {
            case "Kelvin":
                return KELVIN;
            case "Celcius":
                return CELSIUS;
            default:
                return null;
        }
    }

    public static Unit<Volume> getVolumeUnitFromMetric(String unit) {
        switch (unit) {
            case "Litres":
                return LITER;
            default:
                return null;
        }
    }
}