package ua.heatloss.services.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DatePeriod {
    private static final Calendar calendar = Calendar.getInstance();

    private Date startDate;
    private Date endDate;

    public DatePeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        checkDates();
    }

    public boolean isDateFromPast() {
        final Date now = new Date();
        return startDate.before(now) && endDate.before(now);
    }

    public boolean isDateInPeriod(Date date) {
        return date.after(startDate) && (date.before(endDate) || date.equals(endDate));
    }

    public static boolean isDateInPeriod(Date date, Date startDate, Date endDate) {
        return date.after(startDate) && (date.before(endDate) || date.equals(endDate));
    }


    public static DatePeriod checkDates(Date start, Date finish) {
        DatePeriod period = new DatePeriod(start, finish);
        return period;
    }

    public List<Date> getDays() {
        final List<Date> days = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        calendar.setTime(endDate);

        final int daysQuantity = calendar.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < daysQuantity; i++) {
            start.add(Calendar.DAY_OF_YEAR, 1);
            days.add(start.getTime());
        }
        return days;
    }

    public static Date getNextDay(Date date) {
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    private void checkDates() {
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (startDate == null || endDate == null) {
            calendar.setTime(new Date());
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            startDate = calendar.getTime();
            calendar.add(Calendar.DATE, 7);
            endDate = calendar.getTime();
        }
    }
}
