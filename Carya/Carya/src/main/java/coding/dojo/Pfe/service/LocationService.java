package coding.dojo.Pfe.service;

import coding.dojo.Pfe.entity.Location;
import coding.dojo.Pfe.entity.Utilisateur;
import coding.dojo.Pfe.entity.Vehicule;
import coding.dojo.Pfe.repository.LocationRepository;
import coding.dojo.Pfe.repository.UtilisateurRepository;
import coding.dojo.Pfe.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    public List<Location> getLocationByVehicule(Long vehiculeId) {
        return locationRepository.findByVehiculeId(vehiculeId);
    }

    public boolean isCarAvailable(Long carId, LocalDate startDate, LocalDate endDate) {
        Vehicule car = vehiculeRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        List<Location> reservations = locationRepository.findByVehiculeAndDateRange(car, startDate, endDate);
        return reservations.isEmpty();
    }

    public boolean ischaufferAvailable(String chId, LocalDate startDate, LocalDate endDate) {
        Utilisateur chouffer = utilisateurRepository.findById(chId).orElseThrow(() -> new RuntimeException("chauffer not found"));
        List<Location> reservations = locationRepository.findByChauffeurAndDateRange(chouffer, startDate, endDate);
        return reservations.isEmpty();
    }

    public List<Location> tata(Long carId, LocalDate startDate, LocalDate endDate) {
        Vehicule car = vehiculeRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        List<Location> reservations = locationRepository.findAll();
        List<Location> locations = new ArrayList<>();
        reservations.forEach(reservation -> {
            if(reservation.getVehicule().getId().equals(car.getId())){
                locations.add(reservation);
            }
        });
       if (! locations.isEmpty()) {
            reservations.forEach(
                    reservation -> {
                        if(reservation.getStartDate().isAfter(startDate) && reservation.getEndDate().isBefore(endDate)) {
                            locations.add(reservation);
                        }
                    }
            );
        }
        return locations;
    }

    public Location createReservation(String userId, Long carId, LocalDate startDate, LocalDate endDate, boolean withChouffer) {
        if (!isCarAvailable(carId, startDate, endDate)) {
            throw new RuntimeException("Car is not available for the selected dates");
        }
        Vehicule car = vehiculeRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        Utilisateur User = utilisateurRepository.findByUsername(userId).orElseThrow(() -> new RuntimeException("client not found"));
        Location reservation = new Location();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setVehicule(car);
        reservation.setClient(User);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        reservation.setPrixTotal(car.getPrixParJour()*daysBetween);

        if(withChouffer){
            List<Utilisateur> chauffeurs = this.utilisateurRepository.findAll().stream().filter(
                    ch -> ch.getRoles().contains("chauffeur")
            ).collect(Collectors.toList());
            if(chauffeurs.isEmpty()){
                throw new RuntimeException("Chauffeur not found");
            }

            reservation.setPrixTotal(reservation.getPrixTotal()+50*daysBetween);

            for(Utilisateur chauffeur : chauffeurs){
                if(this.ischaufferAvailable(chauffeur.getId(), startDate, endDate)) {
                    reservation.setChauffeur(chauffeur);
                    return locationRepository.save(reservation);
                }
            }
            throw new RuntimeException("Chauffeur not found");
        }
        return locationRepository.save(reservation);
    }

    public ResponseEntity<Location> getLocationById(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            return new ResponseEntity<>(location.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    public ResponseEntity<Location> createLocation(Location location) {
        Location savedLocation = locationRepository.save(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    public ResponseEntity<Location> updateLocation(Long id, Location locationDetails) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            Location updatedLocation = location.get();
            updatedLocation.setStartDate(locationDetails.getStartDate());
            updatedLocation.setEndDate(locationDetails.getEndDate());
            updatedLocation.setPrixTotal(locationDetails.getPrixTotal());
            updatedLocation.setEtat(locationDetails.getEtat());
            locationRepository.save(updatedLocation);
            return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteLocation(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            locationRepository.delete(location.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
