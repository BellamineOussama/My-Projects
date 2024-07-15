package coding.dojo.Pfe.service;

import coding.dojo.Pfe.entity.Utilisateur;
import coding.dojo.Pfe.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public ResponseEntity<Utilisateur> getUtilisateurById(String id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            return new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Utilisateur> getUtilisateurByUsername(String username) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByUsername(username);
        if (utilisateur.isPresent()) {
            return new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Utilisateur> createUtilisateur(Utilisateur utilisateur) {
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Utilisateur>> getallUtilisateur(){
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        return new ResponseEntity<>(utilisateurs,HttpStatus.OK);
    }



    public ResponseEntity<Void> deleteUtilisateur(String id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        if (utilisateur.isPresent()) {
            utilisateurRepository.delete(utilisateur.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<List<Utilisateur>> getUsersByRoles(String role){
        return ResponseEntity.ok(this.utilisateurRepository.findAll().stream().filter(
                ch -> ch.getRoles().contains(role)
        ).collect(Collectors.toList()));
    }


}
