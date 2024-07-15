package coding.dojo.Pfe.controller;

import coding.dojo.Pfe.entity.Categorie;
import coding.dojo.Pfe.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable Long id) {
        return categorieService.getCategorieById(id);
    }

    @GetMapping
    public ResponseEntity<List<Categorie>> getAllCategories() {
        return categorieService.getAllCategories();
    }

    @PostMapping
    public ResponseEntity<Categorie> createCategorie(@RequestBody Categorie categorie) {
        return categorieService.createCategorie(categorie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @RequestBody Categorie categorieDetails) {
        return categorieService.updateCategorie(id, categorieDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        return categorieService.deleteCategorie(id);
    }
}

