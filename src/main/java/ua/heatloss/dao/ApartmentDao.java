package ua.heatloss.dao;


import ua.heatloss.domain.Apartment;

import java.util.List;

public interface ApartmentDao extends CrudDao<Apartment> {

    List<Apartment> findApartmentsByNumber(Integer number);
}
