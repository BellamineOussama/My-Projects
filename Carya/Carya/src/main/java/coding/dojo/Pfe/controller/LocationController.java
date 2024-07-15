package coding.dojo.Pfe.controller;

import coding.dojo.Pfe.dto.ReservationRequest;
import coding.dojo.Pfe.entity.Location;
import coding.dojo.Pfe.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;
    @GetMapping("/vehicule/{vehiculeId}")
    public List<Location> getLocationByVehicule(@PathVariable Long vehiculeId) {
        return locationService.getLocationByVehicule(vehiculeId);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam Long carId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        boolean available = locationService.isCarAvailable(carId, startDate, endDate);
        return ResponseEntity.ok(available);
    }

    @GetMapping("/checek")
    public ResponseEntity<List<Location>> checkkAvailability(@RequestParam Long carId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Location> available = locationService.tata(carId, startDate, endDate);
        return ResponseEntity.ok(available);
    }

    @PostMapping
    public ResponseEntity<Location> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Location reservation = locationService.createReservation(reservationRequest.getUserId(), reservationRequest.getCarId(), reservationRequest.getStartDate(), reservationRequest.getEndDate(), reservationRequest.isWithChouffer());
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return locationService.getAllLocations();
    }



    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location locationDetails) {
        return locationService.updateLocation(id, locationDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        return locationService.deleteLocation(id);
    }
}

