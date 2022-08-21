package me.dio.parking.service;

import me.dio.parking.exception.ParkingNotFoundException;
import me.dio.parking.model.Parking;
import me.dio.parking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingService {

    private final ParkingRepository repository;

    public ParkingService(ParkingRepository repository) {
        this.repository = repository;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Parking> findAll() {
        return repository.findAll();
    }

    public Parking findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));
    }

    @Transactional
    public Parking create(Parking parkingCreate) {

        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setCheckinDate(LocalDateTime.now());
        repository.save(parkingCreate);
        return parkingCreate;
    }

    @Transactional
    public void delete(String id) {

        Parking parking = findById(id);
        repository.delete(parking);
    }

    @Transactional
    public Parking update(String id, Parking parkingCreate) {

        Parking parking = findById(id);
        parking.setLicense(parkingCreate.getLicense());
        parking.setState(parkingCreate.getState());
        parking.setColor(parkingCreate.getColor());
        parking.setModel(parkingCreate.getModel());
        repository.save(parking);
        return parking;
    }

    @Transactional
    public Parking exit(String id) {

        Parking parking = findById(id);
        parking.setCheckoutDate(LocalDateTime.now());
        parking.setBill(ParkingCheckout.getBill(parking));
        repository.save(parking);
        return parking;
    }
}
