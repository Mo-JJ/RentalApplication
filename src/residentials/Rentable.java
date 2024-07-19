package residentials;

import java.time.LocalDate;

public interface Rentable {
    String UNAVAILABLE_MESSAGE_DATES = "Sorry, This Property is Unavailable for the Requested Dates.";
    String UNAVAILABLE_MESSAGE_INFO = "Sorry, This Property is Unavailable for Renting Using The Given Information.";
    boolean isAvailable(LocalDate startDate, LocalDate endDate);
    void rent(int tenantId, LocalDate startDate, LocalDate endDate);
    double calculateRentingPrice(LocalDate startDate, LocalDate endDate);
}
