package ua.heatloss.facades;

import ua.heatloss.domain.*;

import java.util.Date;
import java.util.List;


public interface DataGeneratingFacade {


    House generateMeasurementModules(House house);

    List<Pipe> generatePipes(int quantity, House house);

    List<Apartment> generateApartments(int floors, int apartmentsOnFloor, int rooms, int entrances, House house);

    List<House> generateHouses(int quantity);

    void generateHouseMeasurementData(Date startDate, Date finishDate, long density, House house, boolean withApartments);

    void generatePipeMeasurementData(Date startDate, Date finishDate, long density, Pipe pipe, boolean withApartments);
}
