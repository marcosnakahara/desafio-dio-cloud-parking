package me.dio.parking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parking {

    private String id;
    private String license;
    private String state;
    private String model;
    private String color;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
    private Double bill;

}
