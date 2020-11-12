package ua.edu.ucu.tempseries;

import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    private static final double MIN_TEMPERATURE = -273;

    private double[] temperatureArray;
    private int capacity;


    public TemperatureSeriesAnalysis() {
        temperatureArray = new double[]{};
        capacity = 0;

    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {

        if (checkMinTemperature(temperatureSeries)) {
            throw new InputMismatchException();
        }

        temperatureArray = temperatureSeries.clone();
        capacity = temperatureSeries.length;

    }

    public double average() {
        if (temperatureArray.length == 0) {
            throw new IllegalArgumentException();
        }

        double sum = 0;

        for (double  t: temperatureArray) {
            sum += t;
        }

        return sum / temperatureArray.length;

    }

    public double deviation() {
        if (temperatureArray.length == 0) {
            throw new IllegalArgumentException();
        }

        double sum = 0;

        for (double  t: temperatureArray) {
            sum += Math.abs(t - average()) * Math.abs(t - average());
        }

        return Math.sqrt(sum / temperatureArray.length);

    }

    public double min() {
        return minOrMax(true);
    }

    public double max() {
        return minOrMax(false);
    }

    // truth = min
    private double minOrMax(boolean truth) {
        if (temperatureArray.length == 0) {
            throw new IllegalArgumentException();
        }

        double[] extraArr = temperatureArray.clone();
        Arrays.sort(extraArr);

        if (truth) {
            return extraArr[0];
        }
        return extraArr[extraArr.length - 1];
    }


    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        if (temperatureArray.length == 0) {
            throw new IllegalArgumentException();
        }

        double closestTemp = temperatureArray[0];
        double closestDistance = Math.abs(tempValue - temperatureArray[0]);

        for (int i = 1; i < temperatureArray.length; i++) {

            if (Math.abs(tempValue - temperatureArray[i]) < closestDistance) {
                closestTemp = temperatureArray[i];
                closestDistance = Math.abs(tempValue - temperatureArray[i]);

            } else if (Math.abs(tempValue - temperatureArray[i])
                    - closestDistance < 0.00001) {

                if (temperatureArray[i] > closestTemp) {
                    closestTemp = temperatureArray[i];
                    closestDistance = Math.abs(tempValue - temperatureArray[i]);
                }
            }
        }

        return closestTemp;

    }

    public double[] findTempsLessThen(double tempValue) {
        return findTempsLessOrGreater(true, tempValue);
    }

    public double[] findTempsGreaterThen(double tempValue) {
        return findTempsLessOrGreater(false, tempValue);
    }

    // truth = less
    private double[] findTempsLessOrGreater(boolean truth, double tempValue) {
        if (temperatureArray.length == 0) {
            throw new IllegalArgumentException();
        }

        double[] extraArr = temperatureArray.clone();
        Arrays.sort(extraArr);

        int counter = 0;
        for (int i = 0; i < extraArr.length; i++) {
            if (tempValue <= extraArr[i]) {
                counter = i;
                break;
            }
        }

        if (truth) {
            return Arrays.copyOfRange(extraArr, 0, counter);
        }
        return Arrays.copyOfRange(extraArr, counter, temperatureArray.length);
    }

    public TempSummaryStatistics summaryStatistics() {
        if (temperatureArray.length == 0) {
            throw new IllegalArgumentException();
        }
        return new TempSummaryStatistics(this);
    }

    public int addTemps(double... temps) {
        if (temps.length == 0) {
            return capacity;
        } else if (checkMinTemperature(temps)) {
            throw new InputMismatchException();
        }
        if (temperatureArray.length == 0) {
            temperatureArray = new double[1];
        }

        for (double t: temps) {
            if (capacity == temperatureArray.length) {
                double[] newTempArr = new double[temperatureArray.length * 2];
                System.arraycopy(temperatureArray,
                        0,
                        newTempArr,
                        0,
                        temperatureArray.length);

                temperatureArray = newTempArr;
            }

            temperatureArray[capacity] = t;
            capacity++;
        }
        return capacity;
    }

    private boolean checkMinTemperature(double[] temps) {
        for (double t: temps) {
            if (t  < MIN_TEMPERATURE) {
                return true;
            }
        }
        return false;
    }

}
