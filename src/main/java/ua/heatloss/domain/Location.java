package ua.heatloss.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Location {

    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private Double latitude;
    private Double longitude;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        if (country != null ? !country.equals(location.country) : location.country != null) return false;
        if (city != null ? !city.equals(location.city) : location.city != null) return false;
        if (street != null ? !street.equals(location.street) : location.street != null) return false;
        if (houseNumber != null ? !houseNumber.equals(location.houseNumber) : location.houseNumber != null)
            return false;
        if (latitude != null ? !latitude.equals(location.latitude) : location.latitude != null) return false;
        return longitude != null ? longitude.equals(location.longitude) : location.longitude == null;

    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }
}
