package coding.dojo.Pfe.service;

import coding.dojo.Pfe.entity.Avis;
import coding.dojo.Pfe.entity.Vehicule;
import coding.dojo.Pfe.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    public ResponseEntity<Vehicule> getVehiculeById(Long id) {
        Optional<Vehicule> vehicule = vehiculeRepository.findById(id);
        if (vehicule.isPresent()) {
            return new ResponseEntity<>(vehicule.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return new ResponseEntity<>(vehicules, HttpStatus.OK);
    }

    public ResponseEntity<List<Vehicule>> getVehiculesByDisponibilite(boolean disponible) {
        List<Vehicule> vehicules = vehiculeRepository.findByDisponible(disponible);
        return new ResponseEntity<>(vehicules, HttpStatus.OK);
    }

    public ResponseEntity<List<Vehicule>> getVehiculesByAgence(String Agence) {
        List<Vehicule> vehicules = vehiculeRepository.findByAgenceName(Agence);
        return new ResponseEntity<>(vehicules, HttpStatus.OK);
    }




    public ResponseEntity<Vehicule> createVehicule(Vehicule vehicule) {
        Vehicule savedVehicule = vehiculeRepository.save(vehicule);
        return ResponseEntity.ok(savedVehicule);
    }

    public ResponseEntity<Vehicule> updateVehicule(Long id, Vehicule vehiculeDetails) {
        Optional<Vehicule> vehiculeOptional = vehiculeRepository.findById(id);
        if (vehiculeOptional.isPresent()) {
            Vehicule vehicule = vehiculeOptional.get();
            vehicule.setModele(vehiculeDetails.getModele());
            vehicule.setYears(vehiculeDetails.getYears());
            vehicule.setNbPlace(vehiculeDetails.getNbPlace());
            vehicule.setImmatriculation(vehiculeDetails.getImmatriculation());
            vehicule.setTypeVehicule(vehiculeDetails.getTypeVehicule());
            vehicule.setTronsmition(vehiculeDetails.getTronsmition());
            vehicule.setPrixParJour(vehiculeDetails.getPrixParJour());

            vehiculeRepository.save(vehicule);
            return new ResponseEntity<>(vehicule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Vehicule> AddAvis(Long id, Avis avis) {
        Optional<Vehicule> vehicule = vehiculeRepository.findById(id);
        if (vehicule.isPresent()) {
           Vehicule vehicule1 = vehicule.get();
           if(vehicule1.getAvis() == null)
               vehicule1.setAvis(new ArrayList<>());
           vehicule1.getAvis().add(avis);
            return new ResponseEntity<>(this.vehiculeRepository.save(vehicule1), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteVehicule(Long id) {
        Optional<Vehicule> vehicule = vehiculeRepository.findById(id);
        if (vehicule.isPresent()) {
            vehiculeRepository.delete(vehicule.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
