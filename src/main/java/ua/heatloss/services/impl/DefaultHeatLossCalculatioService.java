package ua.heatloss.services.impl;


import org.springframework.stereotype.Component;
import ua.heatloss.domain.Measurement;
import ua.heatloss.domain.sensors.SensorType;
import ua.heatloss.services.HeatLossCalculationService;

import java.util.List;

@Component
public class DefaultHeatLossCalculatioService implements HeatLossCalculationService {

    public static final Double SPECIFIC_HEAT_CAPACITY = 4.187;

    public Double calculate(List<Measurement> measurements) {

        MeasurementContex measurementContex = new MeasurementContex(measurements);
        return calculateHeatPowerLoss(measurementContex);
    }

    public Double calculateHeatPowerLoss(MeasurementContex measurementContex) {
        Double flowMeasure = measurementContex.flow.getValue();
        Double inputMeasure = measurementContex.input.getValue();
        Double outputMeasure = measurementContex.output.getValue();

        double loss = flowMeasure * SPECIFIC_HEAT_CAPACITY * (inputMeasure - outputMeasure);
        return loss;
    }


    public static class MeasurementContex {
        private Measurement input;
        private Measurement output;
        private Measurement flow;

        public MeasurementContex(List<Measurement> measurements) {
            populate(measurements);
        }

        public Measurement getInput() {
            return input;
        }

        public void setInput(Measurement input) {
            this.input = input;
        }

        public Measurement getOutput() {
            return output;
        }

        public void setOutput(Measurement output) {
            this.output = output;
        }

        public Measurement getFlow() {
            return flow;
        }

        public void setFlow(Measurement flow) {
            this.flow = flow;
        }

        public void populate(List<Measurement> measurements) {
            for (Measurement measurement : measurements) {
                SensorType sensorType = measurement.getSensor().getSensorType();
                if (SensorType.INPUT == sensorType) {
                    input = measurement;
                } else if (SensorType.OUTPUT == sensorType) {
                    output = measurement;
                } else if (SensorType.FLOW == sensorType) {
                    flow = measurement;
                }
            }
        }
    }
}
