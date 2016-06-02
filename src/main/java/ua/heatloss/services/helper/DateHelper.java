package ua.heatloss.services.helper;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static DatePeriod checkDates(Date start, Date finish) {
        DatePeriod period = new DatePeriod(start, finish);
        return period;
    }


    public static class DatePeriod {
        private static final Calendar calendar = Calendar.getInstance();

        private Date startDate;
        private Date endDate;

        public DatePeriod(Date startDate, Date endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            checkDates();
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
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                startDate = calendar.getTime();
                calendar.add(Calendar.DATE, 7);
                endDate = calendar.getTime();
            }
        }
    }
}
