package ua.heatloss.services.impl;

import ua.heatloss.domain.Apartment;
import ua.heatloss.domain.House;
import ua.heatloss.services.helper.DatePeriod;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
class EnergyByDateCache {

    private Map<CacheKey, Double> cache = new ConcurrentHashMap<>();

    public void cache(CacheKey key, Double value) {
        if (key.getDate().before(new Date()) && !cache.containsKey(key)) {
            cache.put(key, value);
        }
    }

    public void addCachedValue(Date date, Object target, Double energy) {
        CacheKey key = new CacheKey(target, date);
        cache.put(key, energy);
    }

    public void addCachedValues(Map<Date, Double> energyMap, Object target) {
        for (Map.Entry<Date, Double> entry : energyMap.entrySet()) {
            CacheKey key = new CacheKey(target, entry.getKey());
            cache.put(key, entry.getValue());
        }
    }

        public Map<Date, Double> getCachedValueForApartment(DatePeriod period, Apartment apartment) {
        Map<Date, Double> energyApartment = cache.entrySet().stream().filter(entry -> period.isDateInPeriod(entry.getKey().getDate())
                && entry.getKey().target.equals(apartment)).collect(Collectors.toMap(entry -> entry.getKey().getDate(),
                Map.Entry::getValue));
        if (energyApartment.size() != period.getDays().size()) {
            return null;
        } else {
            return energyApartment;
        }
    }

    public boolean checkIfCacheExistForApartment(DatePeriod period, Apartment apartment) {
        return cache.entrySet().stream().filter(entry -> period.isDateInPeriod(entry.getKey().getDate())
                && entry.getKey().target.equals(apartment)).collect(Collectors.toSet()).size() != period.getDays().size();

    }

    private static class CacheKey {
        private Object target;
        private Date date;
        private HouseCacheType type;

        public CacheKey() {
        }

        public CacheKey(Object target, Date date, HouseCacheType type) {
            this.target = target;
            this.date = date;
            this.type = type;
        }

        public CacheKey(Object target, Date date) {
            this.target = target;
            this.date = date;
        }

        public Object getTarget() {
            return target;
        }

        public void setTarget(Object target) {
            this.target = target;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public HouseCacheType getType() {
            return type;
        }

        public void setType(HouseCacheType type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CacheKey cacheKey = (CacheKey) o;

            if (target != null ? !target.equals(cacheKey.target) : cacheKey.target != null) return false;
            if (date != null ? !date.equals(cacheKey.date) : cacheKey.date != null) return false;
            return type == cacheKey.type;

        }

        @Override
        public int hashCode() {
            int result = target != null ? target.hashCode() : 0;
            result = 31 * result + (date != null ? date.hashCode() : 0);
            result = 31 * result + (type != null ? type.hashCode() : 0);
            return result;
        }
    }

    private enum HouseCacheType {
        LOSS, INPUT, CONSUMED
    }
}
