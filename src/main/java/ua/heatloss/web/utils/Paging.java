package ua.heatloss.web.utils;

import ua.heatloss.dao.AbstractDao;

public class Paging {

    private Integer offset = 0;
    private Integer limit = AbstractDao.DEFAULT_LIMIT;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
