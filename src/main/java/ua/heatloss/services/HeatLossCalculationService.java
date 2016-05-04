package ua.heatloss.services;

import ua.heatloss.domain.Measurement;

import java.util.List;


public interface HeatLossCalculationService {

    Double calculate(List<Measurement> measurements);

}
