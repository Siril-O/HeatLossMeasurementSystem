package ua.heatloss.services.helper;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class PowerToEnergyCalculator {

    public static double calculate(Map<Date, Double> powerByTime) {
        List<Value> values = new ArrayList<>(powerByTime.size());
        for (Map.Entry<Date, Double> entry : powerByTime.entrySet()) {
            values.add(new Value(TimeUnit.SECONDS.convert(entry.getKey().getTime(), TimeUnit.MILLISECONDS), entry.getValue()));
        }

        double result = 0;
        for (int i = 1; i < values.size(); i++) {
            Value prevValue = values.get(i - 1);
            Value currentValue = values.get(i);
            result += calculateEnergy(prevValue.power, currentValue.power, prevValue.time, currentValue.time);
        }
        return result;
    }

    private static double calculateEnergy(double powerA, double powerB, double timeA, double timeB) {
        return (timeA - timeB) * abs((powerA + powerB) / 2);
    }


    private static class Value {
        private long time;
        private double power;

        public Value(long time, double power) {
            this.time = time;
            this.power = power;
        }
    }
}
