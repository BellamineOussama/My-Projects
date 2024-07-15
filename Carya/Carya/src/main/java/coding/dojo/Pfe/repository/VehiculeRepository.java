package coding.dojo.Pfe.repository;

import coding.dojo.Pfe.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByDisponible(boolean disponible);
    List<Vehicule> findByAgenceName(String agenceName);
}
