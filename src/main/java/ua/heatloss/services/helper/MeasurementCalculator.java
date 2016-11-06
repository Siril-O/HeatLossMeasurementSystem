package ua.heatloss.services.helper;

import ua.heatloss.domain.Measurement;
import ua.heatloss.services.LossContext;


public class MeasurementCalculator {

    public static final Double SPECIFIC_HEAT_CAPACITY = 4.187;


    public static double calculatePower(Measurement measurement) {

        checkIsValidValue(measurement.getFlowValue(), "flow");
        checkIsValidValue(measurement.getInputValue(), "input Temperature");
        checkIsValidValue(measurement.getOutputValue(), "Output Temperature");

        double flowMeasure = measurement.getFlowValue();
        double inputMeasure = measurement.getInputValue();
        double outputMeasure = measurement.getOutputValue();

        double power = flowMeasure * SPECIFIC_HEAT_CAPACITY * Math.abs(inputMeasure - outputMeasure);
        return power;
    }

    private static void checkIsValidValue(Double value, String name) {
        if (value == null || value == 0) {
            throw new IllegalArgumentException("Empty value of " + name);
        }
    }

    public static double calculatePowerLosses(LossContext context) {
        double mainPowerConsumption = calculatePower(context.getMainMeasurement());
        double consumedPowerByCustomers = 0;
        for (Measurement measurement : context.getPipeMeasurements()) {
            consumedPowerByCustomers += calculatePower(measurement);
        }
        return mainPowerConsumption - consumedPowerByCustomers;
    }



    public static double calculatePowerConsumedByCustomers(LossContext context) {
        double consumedPowerByCustomers = 0;
        for (Measurement measurement : context.getPipeMeasurements()) {
            consumedPowerByCustomers += calculatePower(measurement);
        }
        return consumedPowerByCustomers;
    }
}
