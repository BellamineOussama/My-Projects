package coding.dojo.Pfe.controller;

import coding.dojo.Pfe.entity.Avis;
import coding.dojo.Pfe.entity.Vehicule;
import coding.dojo.Pfe.service.AvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
public class AvisController {

    @Autowired
    private AvisService avisService;

    @GetMapping("/{id}")
    public ResponseEntity<Avis> getAvisById(@PathVariable Long id) {
        return avisService.getAvisById(id);
    }

    @GetMapping
    public ResponseEntity<List<Avis>> getAllAvis() {
        return avisService.getAllAvis();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Avis>> getAvisByClientId(@PathVariable String clientId) {
        return avisService.getAvisByClientId(clientId);
    }




    @PostMapping
    public ResponseEntity<Avis> createAvis(@RequestBody Avis avis) {
        return avisService.createAvis(avis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Avis> updateAvis(@PathVariable Long id, @RequestBody Avis avisDetails) {
        return avisService.updateAvis(id, avisDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvis(@PathVariable Long id) {
        return avisService.deleteAvis(id);
    }
}

