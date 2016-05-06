package ua.heatloss.facades;

import ua.heatloss.domain.*;

import java.util.Date;
import java.util.List;


public interface DataGeneratingFacade {

    //frequency in seconds
    void generateMeasurementData(Date startDate, Date finishDate, long density, Object target);

    List<Measurement> generateMeasurementDataForHouse(House house, double startTemperature, Date timestamp);

    List<Measurement> generateMeasurementDataForPipe(Pipe pipe, double startTemperature, Date timestamp);

    List<House> generateHouses(int quantity);

    List<Apartment> generateApartments(int floors, int apartmentsOnFloor, int rooms, int entrances, House house);

    House generateMeasurementModules(House house, MeasurementModuleType moduleType);

    List<Pipe> generatePipes(int quantity, House house);

}
