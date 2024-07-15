package coding.dojo.Pfe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Vehicule vehicule;

    @ManyToOne
    private Utilisateur chauffeur;

    @ManyToOne
    private Utilisateur client;

    private LocalDate startDate;
    private LocalDate endDate;

    private float prixTotal;

    private String etat;





}
