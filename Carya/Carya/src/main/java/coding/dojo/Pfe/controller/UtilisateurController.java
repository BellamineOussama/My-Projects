package coding.dojo.Pfe.controller;

import coding.dojo.Pfe.entity.Utilisateur;
import coding.dojo.Pfe.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable String id) {
        return utilisateurService.getUtilisateurById(id);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Utilisateur> getUtilisateurByEmail(@RequestParam String username) {
        return utilisateurService.getUtilisateurByUsername(username);
    }

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.createUtilisateur(utilisateur);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable String id) {
        return utilisateurService.deleteUtilisateur(id);
    }

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUser() {
        return utilisateurService.getallUtilisateur();
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Utilisateur>> getUsersRole(@PathVariable String role) {
        return utilisateurService.getUsersByRoles(role);
    }
}

