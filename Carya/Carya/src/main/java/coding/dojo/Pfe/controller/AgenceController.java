package coding.dojo.Pfe.controller;

import coding.dojo.Pfe.entity.Agence;
import coding.dojo.Pfe.service.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
public class AgenceController {

    @Autowired
    private AgenceService agenceService;

    @GetMapping("/{id}")
    public ResponseEntity<Agence> getAgenceById(@PathVariable Long id) {
        return agenceService.getAgenceById(id);
    }

    @GetMapping
    public ResponseEntity<List<Agence>> getAllAgences() {
        return agenceService.getAllAgences();
    }

    @PostMapping
    public ResponseEntity<Agence> createAgence(@RequestBody Agence agence) {
        return agenceService.createAgence(agence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agence> updateAgence(@PathVariable Long id, @RequestBody Agence agenceDetails) {
        return agenceService.updateAgence(id, agenceDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgence(@PathVariable Long id) {
        return agenceService.deleteAgence(id);
    }
}
