

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectCode {
    public static List<List<LocalDate>> generateDateIntervals(LocalDate startDate, LocalDate endDate, FlightPriceRecorder flightPriceRecorder) {
        List<List<LocalDate>> dateIntervals = new ArrayList<>();

        LocalDate currentStartDate = startDate;

        while (currentStartDate.isBefore(endDate) || currentStartDate.isEqual(endDate)) {
            LocalDate currentEndDate = currentStartDate.plusDays(6); // Assuming a week interval

            // Make sure the end date does not exceed the actual end date
            if (currentEndDate.isAfter(endDate)) {
                currentEndDate = endDate;
            }

            List<LocalDate> interval = new ArrayList<>();
            interval.add(currentStartDate);
            interval.add(currentEndDate);

            // Record flight price for the current date interval
            recordFlightPriceForInterval("Cancun", currentStartDate, currentEndDate, flightPriceRecorder);

            dateIntervals.add(interval);
            dateIntervals.addAll(generatePermutations(currentStartDate, currentEndDate, endDate));
            // Move to the next week
            currentStartDate = currentEndDate.plusDays(1);
        }

        return dateIntervals;
    }

    private static List<List<LocalDate>> generatePermutations(LocalDate startDate, LocalDate endDate, LocalDate maxEndDate) {
        List<List<LocalDate>> permutations = new ArrayList<>();

        LocalDate currentStartDate = startDate;

        while (currentStartDate.plusDays(6).isBefore(maxEndDate) || currentStartDate.plusDays(6).isEqual(maxEndDate)) {
            LocalDate currentEndDate = currentStartDate.plusDays(6);

            List<LocalDate> interval = new ArrayList<>();
            interval.add(currentStartDate);
            interval.add(currentEndDate);

            permutations.add(interval);

            // Move to the next week
            currentStartDate = currentStartDate.plusDays(1);
        }

        return permutations;
    }

    private static void recordFlightPriceForInterval(String city, LocalDate departureDate, LocalDate returnDate, FlightPriceRecorder flightPriceRecorder) {
        // Record the flight price using the provided FlightPriceRecorder
        double flightPrice = flightPriceRecorder.recordFlightPrice(city, departureDate, returnDate);
        System.out.println("Recorded Flight from " + city + ", Departure Date: " + departureDate + ", Return Date: " + returnDate +
                ", Price: " + flightPrice);
    }
}
