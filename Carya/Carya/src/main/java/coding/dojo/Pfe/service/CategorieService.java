package coding.dojo.Pfe.service;

import coding.dojo.Pfe.entity.Categorie;
import coding.dojo.Pfe.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public ResponseEntity<Categorie> getCategorieById(Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        if (categorie.isPresent()) {
            return new ResponseEntity<>(categorie.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Categorie>> getAllCategories() {
        List<Categorie> categories = categorieRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    public ResponseEntity<Categorie> createCategorie(Categorie categorie) {
        Categorie savedCategorie = categorieRepository.save(categorie);
        return new ResponseEntity<>(savedCategorie, HttpStatus.CREATED);
    }

    public ResponseEntity<Categorie> updateCategorie(Long id, Categorie categorieDetails) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        if (categorie.isPresent()) {
            Categorie updatedCategorie = categorie.get();
            updatedCategorie.setNom(categorieDetails.getNom());
            updatedCategorie.setDescription(categorieDetails.getDescription());
            categorieRepository.save(updatedCategorie);
            return new ResponseEntity<>(updatedCategorie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteCategorie(Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        if (categorie.isPresent()) {
            categorieRepository.delete(categorie.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
