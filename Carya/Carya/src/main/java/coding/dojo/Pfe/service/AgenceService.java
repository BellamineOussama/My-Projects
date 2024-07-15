package coding.dojo.Pfe.service;

import coding.dojo.Pfe.entity.Agence;
import coding.dojo.Pfe.repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgenceService {

    @Autowired
    private AgenceRepository agenceRepository;

    public ResponseEntity<Agence> getAgenceById(Long id) {
        Optional<Agence> agence = agenceRepository.findById(id);
        if (agence.isPresent()) {
            return new ResponseEntity<>(agence.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Agence>> getAllAgences() {

        List<Agence> agences = agenceRepository.findAll();

        return new ResponseEntity<>(agences, HttpStatus.OK);
    }

    public ResponseEntity<Agence> createAgence(Agence agence) {
        if(agence.getChauffeurs()==null)
            agence.setChauffeurs(new ArrayList<>());
        if(agence.getVehicules()==null)
            agence.setVehicules(new ArrayList<>());
        Agence savedAgence = agenceRepository.save(agence);
        return new ResponseEntity<>(savedAgence, HttpStatus.CREATED);
    }

    public ResponseEntity<Agence> updateAgence(Long id, Agence agenceDetails) {
        Optional<Agence> agence = agenceRepository.findById(id);
        if (agence.isPresent()) {
            Agence updatedAgence = agence.get();
            updatedAgence.setNom(agenceDetails.getNom());
            updatedAgence.setAdresse(agenceDetails.getAdresse());
            updatedAgence.setTelephone(agenceDetails.getTelephone());
            agenceRepository.save(updatedAgence);
            return new ResponseEntity<>(updatedAgence, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteAgence(Long id) {
        Optional<Agence> agence = agenceRepository.findById(id);
        if (agence.isPresent()) {
            agenceRepository.delete(agence.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
