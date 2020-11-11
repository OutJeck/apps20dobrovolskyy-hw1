package ua.edu.ucu.tempseries;

public final class TempSummaryStatistics {
    final private double avgTemp, devTemp, minTemp, maxTemp;

    TempSummaryStatistics(TemperatureSeriesAnalysis temperatureAnalysis) {
        this.avgTemp = temperatureAnalysis.average();
        this.devTemp = temperatureAnalysis.deviation();
        this.minTemp = temperatureAnalysis.min();
        this.maxTemp = temperatureAnalysis.max();

    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getDevTemp() {
        return devTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }
}
