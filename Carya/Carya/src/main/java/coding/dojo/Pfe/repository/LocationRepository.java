package coding.dojo.Pfe.repository;

import coding.dojo.Pfe.entity.Location;
import coding.dojo.Pfe.entity.Utilisateur;
import coding.dojo.Pfe.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByVehiculeAndEndDateAfterAndStartDateBefore(Vehicule vehicule, LocalDate startDate, LocalDate endDate);
    List<Location> findByVehiculeId(Long vehiculeId);

    @Query("SELECT l FROM Location l WHERE l.vehicule = :vehicule AND l.endDate > :startDate AND l.startDate < :endDate")
    List<Location> findByVehiculeAndDateRange(
            @Param("vehicule") Vehicule vehicule,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT l FROM Location l WHERE l.chauffeur = :chauffeur AND l.endDate > :startDate AND l.startDate < :endDate")
    List<Location> findByChauffeurAndDateRange(
            @Param("chauffeur") Utilisateur chauffeur,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


}
