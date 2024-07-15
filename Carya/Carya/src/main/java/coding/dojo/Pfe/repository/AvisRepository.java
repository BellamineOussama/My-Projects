package coding.dojo.Pfe.repository;

import coding.dojo.Pfe.entity.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {
    List<Avis> findByClientId(String client_id);

}