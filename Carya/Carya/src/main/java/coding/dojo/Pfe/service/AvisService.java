package coding.dojo.Pfe.service;

import coding.dojo.Pfe.entity.Avis;
import coding.dojo.Pfe.entity.Utilisateur;
import coding.dojo.Pfe.repository.AvisRepository;
import coding.dojo.Pfe.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AvisService {

    @Autowired
    private AvisRepository avisRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public ResponseEntity<Avis> getAvisById(Long id) {
        Optional<Avis> avis = avisRepository.findById(id);
        if (avis.isPresent()) {
            return new ResponseEntity<>(avis.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Avis>> getAllAvis() {
        List<Avis> avis = avisRepository.findAll();
        return new ResponseEntity<>(avis, HttpStatus.OK);
    }

    public ResponseEntity<List<Avis>> getAvisByClientId(String clientId) {
        List<Avis> avis = avisRepository.findByClientId(clientId);
        return new ResponseEntity<>(avis, HttpStatus.OK);
    }

    public ResponseEntity<Avis> createAvis(Avis avis) {
        avis.setDate(new Date());
        if(avis.getClient()!= null){
            if(avis.getClient().getUsername() != null){
                Optional<Utilisateur> optionalUtilisateur = this.utilisateurRepository.findByUsername(avis.getClient().getUsername());
                if (optionalUtilisateur.isPresent()) {
                    Utilisateur utilisateur = optionalUtilisateur.get();
                    avis.setClient(utilisateur);
                }
            }
        }
        Avis savedAvis = avisRepository.save(avis);
        return new ResponseEntity<>(savedAvis, HttpStatus.CREATED);
    }

    public ResponseEntity<Avis> updateAvis(Long id, Avis avisDetails) {
        Optional<Avis> avis = avisRepository.findById(id);
        if (avis.isPresent()) {
            Avis updatedAvis = avis.get();
            updatedAvis.setCommentaire(avisDetails.getCommentaire());
            updatedAvis.setNote(avisDetails.getNote());
            updatedAvis.setDate(avisDetails.getDate());
            avisRepository.save(updatedAvis);
            return new ResponseEntity<>(updatedAvis, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteAvis(Long id) {
        Optional<Avis> avis = avisRepository.findById(id);
        if (avis.isPresent()) {
            avisRepository.delete(avis.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
