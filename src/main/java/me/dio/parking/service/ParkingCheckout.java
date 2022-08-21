package me.dio.parking.service;

import me.dio.parking.model.Parking;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingCheckout {

    public static final double MIN_MINUTES_CHARGE_TIME = 60.0;
    public static final double FIRST_HOUR_PRICE = 10.0;
    public static final double ADDITIONAL_HOUR_PRICE = 5.0;
    public static final double MAX_MINUTES_TOLERANCE = 15.0;

    public static double getBill(Parking parking) {

        double bill = 0.0;
        double totalTimeMinutes = getTotalTimeMinutes(parking.getCheckinDate(), parking.getCheckoutDate());

        if (totalTimeMinutes > MAX_MINUTES_TOLERANCE) {
            bill = calculateBill(getTotalTimeHours(totalTimeMinutes));
        }
        return bill;
    }

    private static double getTotalTimeMinutes(LocalDateTime checkinDate, LocalDateTime checkoutDate) {
        return checkinDate.until(checkoutDate, ChronoUnit.MINUTES);
    }

    private static double getTotalTimeHours(double minutes) {
        return Math.ceil(minutes / MIN_MINUTES_CHARGE_TIME);
    }

    private static double calculateBill(double totalTimeHours) {
        return FIRST_HOUR_PRICE + ((totalTimeHours - 1) * ADDITIONAL_HOUR_PRICE);
    }
}
