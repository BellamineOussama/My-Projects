package coding.dojo.Pfe.entity;

import jakarta.persistence.*;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modele;
    private String NbPlace;
    private String energ;
    private String years;
    private String tronsmition;
    private String consomation;
    private String immatriculation;
    private float prixParJour;
    private boolean disponible ;
    private String image;
    @Enumerated(EnumType.STRING)
    private TypeVehicule typeVehicule;

    private String agenceName;

    @ManyToOne
    private Categorie categorie;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Avis> avis;


}