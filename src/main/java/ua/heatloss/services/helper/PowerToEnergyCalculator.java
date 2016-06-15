package ua.heatloss.services.helper;


import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class PowerToEnergyCalculator {

    private static final double KKALORIES_IN_JOULE = 0.000239006;

    public static double calculate(Map<Date, Double> powerByTime) {
        List<Value> values = new ArrayList<>(powerByTime.size());
        for (Map.Entry<Date, Double> entry : powerByTime.entrySet()) {
            values.add(new Value(TimeUnit.SECONDS.convert(entry.getKey().getTime(), TimeUnit.MILLISECONDS), entry.getValue()));
        }
        Collections.sort(values, (o1, o2) -> Long.compare(o1.getTime(), o2.getTime()));
        double result = 0;
        for (int i = 1; i < values.size(); i++) {
            Value prevValue = values.get(i - 1);
            Value currentValue = values.get(i);
            result += calculateEnergy(prevValue.power, currentValue.power, prevValue.time, currentValue.time);
        }
        return result;
    }

    public static double calculateInKKalories(Map<Date, Double> powerByTime) {
        return calculate(powerByTime) * KKALORIES_IN_JOULE;
    }

    private static double calculateEnergy(double powerA, double powerB, double timeA, double timeB) {
        return abs(timeB - timeA) * abs((powerA + powerB) / 2);
    }


    private static class Value {
        private long time;
        private double power;

        public Value(long time, double power) {
            this.time = time;
            this.power = power;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public double getPower() {
            return power;
        }

        public void setPower(double power) {
            this.power = power;
        }
    }
}
