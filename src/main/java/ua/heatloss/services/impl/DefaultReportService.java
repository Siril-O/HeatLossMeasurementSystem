package ua.heatloss.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.domain.modules.AbstractMeasurementModule;
import ua.heatloss.services.HeatConsumptionCalculationService;
import ua.heatloss.services.ReportService;
import ua.heatloss.services.helper.DatePeriod;
import ua.heatloss.web.controller.dto.ApartmentReportData;
import ua.heatloss.web.controller.dto.HouseReportData;
import ua.heatloss.web.controller.dto.HouseReportDataEntry;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DefaultReportService implements ReportService {

    private static final int APARTMENT_SEGMENTS_QUANTITY = 20;
    private static final int HOUSE_SEGMENTS_QUANTITY = 30;

    @Autowired
    private HeatConsumptionCalculationService calculationService;

    @Autowired
    private EnergyByDateCache energyByDateCache;

    @Override
    public HouseReportData buildHouseEnergyReportDataInTimePeriod(House house, Date startDate, Date endDate) {
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        final List<HouseReportDataEntry> chartData = calculationService.calculateHouseEnergyInTimePeriod(house, period.getStartDate(),
                period.getEndDate());
        return new HouseReportData(period.getStartDate(), period.getEndDate(), chartData);
    }

    @Override
    public HouseReportData buildHousePowerReportData(House house, Date startDate, Date endDate) {
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        List<HouseReportDataEntry> chartData = calculationService.calculateHousePower(house, period.getStartDate(),
                period.getEndDate());
        chartData = truncate(chartData, HOUSE_SEGMENTS_QUANTITY);
        return new HouseReportData(period.getStartDate(), period.getEndDate(), chartData);
    }

    @Override
    public ApartmentReportData buildApartmentEnergyReportDataInTimePeriod(Apartment apartment, Date startDate, Date endDate) {
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        Map<Date, Double> energy = energyByDateCache.getCachedValueForApartment(period, apartment);
        if (energy == null) {
            energy = calculationService.calculateEnergyByDays(apartment, period.getStartDate(),
                    period.getEndDate());
            energyByDateCache.addCachedValues(energy, apartment);
        }
        return new ApartmentReportData(period.getStartDate(), period.getEndDate(), energy);
    }

    @Override
    public ApartmentReportData buildPowerReportForApartment(Apartment apartment, Date startDate, Date endDate) {
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        Map<Date, Double> power = calculationService.calculatePowerInTimePeriod(apartment, period.getStartDate(),
                period.getEndDate());
        power = truncateData(power,APARTMENT_SEGMENTS_QUANTITY);
        return new ApartmentReportData(period.getStartDate(), period.getEndDate(), power);
    }

    @Override
    public Map<Date, Double> buildModuleEnergyReportDataInTimePeriod(AbstractMeasurementModule module, Date startDate, Date endDate) {
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        Map<Date, Double> energy = calculationService.calculateEnergyByDays(module, period.getStartDate(),
                period.getEndDate());
        return energy;
    }

    @Override
    public Map<Date, Double> buildModulePowerReportDataInTimePeriod(AbstractMeasurementModule module, Date startDate, Date endDate) {
        DatePeriod period = DatePeriod.checkDates(startDate, endDate);
        Map<Date, Double> power = calculationService.calculatePowerInTimePeriod(module, period.getStartDate(),
                period.getEndDate());
        power = truncateData(power,APARTMENT_SEGMENTS_QUANTITY);
        return power;
    }

    private Map<Date, Double> truncateData(Map<Date, Double> source, int segments) {
        if(source.size() < 0 || source.size()<segments){
            return source;
        }
        List<Map.Entry<Date, Double>> entries  = new ArrayList<>(source.entrySet());
        Map<Date, Double> result = new HashMap<>();
        int step = source.size()/ segments;
        int amountOfIterations = source.size() % segments == 0 ? segments : segments - 1;
        for (int  i = 0; i < amountOfIterations; i++){
            result.put(entries.get(i*step).getKey(), entries.subList(i*step, (i+1)* step).stream().
                    collect(Collectors.averagingDouble(Map.Entry::getValue)));
        }
        if(source.size()% segments != 0){
            result.put(entries.get(amountOfIterations*step).getKey(), entries.subList(amountOfIterations*step, source.size()).stream().
                    collect(Collectors.averagingDouble(Map.Entry::getValue)));
        }
        return result;
    }

    private  List<HouseReportDataEntry> truncate(List<HouseReportDataEntry> source,int segments){
        if(source.size() < 0 || source.size()<segments){
            return source;
        }
        List<HouseReportDataEntry> result = new ArrayList<>();
        int step = source.size()/ segments;
        int amountOfIterations = source.size() % segments == 0 ? segments : segments - 1;
        for (int  i = 0; i < amountOfIterations; i++){
            result.add(truncateSubList(source, i*step,(i+1)* step));
                }
        if(source.size()% segments != 0){
            result.add(truncateSubList(source, amountOfIterations*step,source.size()));
        }
        return result;
    }

    private HouseReportDataEntry truncateSubList(List<HouseReportDataEntry> source, int startPosition, int endPosition){
        HouseReportDataEntry entry = new HouseReportDataEntry();
        entry.setDate(source.get(startPosition).getDate());
        List<HouseReportDataEntry> subList = source.subList(startPosition, endPosition);
        entry.setInput(subList.stream().collect(Collectors.averagingDouble(HouseReportDataEntry::getInput)));
        entry.setConsumed(subList.stream().collect(Collectors.averagingDouble(HouseReportDataEntry::getConsumed)));
        entry.setLoss(subList.stream().collect(Collectors.averagingDouble(HouseReportDataEntry::getLoss)));
        return entry;
    }
}
