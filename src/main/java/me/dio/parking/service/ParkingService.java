package me.dio.parking.service;

import me.dio.parking.exception.ParkingNotFoundException;
import me.dio.parking.model.Parking;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ParkingService {

    private static final Map<String, Parking> parkingMap = new HashMap<>();

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public List<Parking> findAll() {
        return new ArrayList<>(parkingMap.values());
    }

    public Parking findById(String id) {

        Parking parking = parkingMap.get(id);

        if (parking == null) {
            throw new ParkingNotFoundException(id);
        }
        return parking;
    }

    public Parking create(Parking parkingCreate) {

        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setCheckinDate(LocalDateTime.now());
        parkingMap.put(uuid, parkingCreate);
        return parkingCreate;
    }

    public void delete(String id) {

        Parking parking = findById(id);
        parkingMap.remove(id);
    }

    public Parking update(String id, Parking parkingCreate) {

        Parking parking = findById(id);
        parking.setLicense(parkingCreate.getLicense());
        parking.setState(parkingCreate.getState());
        parking.setColor(parkingCreate.getColor());
        parking.setModel(parkingCreate.getModel());
        parkingMap.replace(id, parking);
        return parking;
    }

    public Parking exit(String id) {

        Parking parking = findById(id);
        parking.setCheckoutDate(LocalDateTime.now());
        parkingMap.replace(id, parking);
        return parking;
    }
}
