package ua.heatloss.services;

import ua.heatloss.domain.Apartment;

import java.util.List;


public interface ApartmentService extends CrudService<Apartment> {

    List<Apartment> findApartmentsByNumber(Integer number);

}
