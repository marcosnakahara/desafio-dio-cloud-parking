package me.dio.parking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.parking.controller.dto.ParkingCreateDTO;
import me.dio.parking.controller.dto.ParkingDTO;
import me.dio.parking.controller.mapper.ParkingMapper;
import me.dio.parking.model.Parking;
import me.dio.parking.service.ParkingService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/parking")
@Tag(name = "Parking Controller", description = "Parking Controller")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController(ParkingService service, ParkingMapper parkingMapper) {
        this.parkingService = service;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    @Operation(summary = "Find all parkings")
    public ResponseEntity<List<ParkingDTO>> fingAll() {

        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "fingById")
    public ResponseEntity<ParkingDTO> fingById(@PathVariable String id) {

        Parking parking = parkingService.findById(id);
        ParkingDTO parkingDTO = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.ok(parkingDTO);
    }

    @PostMapping
    @Operation(summary = "create")
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO parkingCreateDTO) {

        Parking parkingCreate = parkingMapper.toParking(parkingCreateDTO);
        Parking parking = parkingService.create(parkingCreate);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete")
    public ResponseEntity delete(@PathVariable String id) {

        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "update")
    public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDTO parkingCreateDTO) {

        Parking parkingCreate = parkingMapper.toParking(parkingCreateDTO);
        Parking parking = parkingService.update(id, parkingCreate);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/{id}")
    @Operation(summary = "exit")
    public ResponseEntity<ParkingDTO> exit(@PathVariable String id) {

        Parking parking = parkingService.exit(id);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
