package coding.dojo.Pfe.repository;

import coding.dojo.Pfe.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
    public Optional<Utilisateur> findByUsername(String username);

}