package coding.dojo.Pfe.controller;

import coding.dojo.Pfe.entity.Avis;
import coding.dojo.Pfe.entity.Vehicule;
import coding.dojo.Pfe.service.LocationService;
import coding.dojo.Pfe.service.VehiculeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    @Autowired
    private VehiculeService vehiculeService;

    @Autowired
    private LocationService locationService;

    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable Long id) {
        return vehiculeService.getVehiculeById(id);
    }

    @GetMapping
    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        return vehiculeService.getAllVehicules();
    }
    @GetMapping("/agence")
    public ResponseEntity<List<Vehicule>> getVehiculesByAgence(@RequestParam String agenceName) {
        return vehiculeService.getVehiculesByAgence(agenceName);
    }

    @GetMapping("/disponible")
    public ResponseEntity<List<Vehicule>> getVehiculesByDisponibilite(@RequestParam boolean disponible) {
        return vehiculeService.getVehiculesByDisponibilite(disponible);
    }

    @GetMapping("/Agence/{agence}")
    public ResponseEntity<List<Vehicule>> getVehiculesByDisponibilite(@PathVariable String agence) {
        return vehiculeService.getVehiculesByAgence(agence);
    }



    @PutMapping("{id}/Avis")
    public ResponseEntity<Vehicule> AddAvis(@PathVariable Long id,@RequestBody Avis avis) {
        return this.vehiculeService.AddAvis(id, avis);

    }

    @PostMapping("/image")
    public ResponseEntity<?> createVehicule(@RequestParam("vehicule") String vehiculeJson,
                                            @RequestParam("image") MultipartFile image) {
        try {
            System.out.println("Received vehicule JSON: " + vehiculeJson);
            if (image != null) {
                System.out.println("Received file: " + image.getOriginalFilename());
            } else {
                System.out.println("No file received.");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Vehicule vehicule = objectMapper.readValue(vehiculeJson, Vehicule.class);

            if (image != null) {
                String uploadDir = "src/main/resources/images/";
                File file = new File(uploadDir, image.getOriginalFilename());
                image.transferTo(file);
            }

            return vehiculeService.createVehicule(vehicule);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }



    @PostMapping
    public ResponseEntity<?> createVehicule(@RequestBody Vehicule vehicule){
        return vehiculeService.createVehicule(vehicule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> updateVehicule(@PathVariable Long id, @RequestBody Vehicule vehiculeDetails) {
        return vehiculeService.updateVehicule(id, vehiculeDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        return vehiculeService.deleteVehicule(id);
    }


}

