package coding.dojo.Pfe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String telephone;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Vehicule> vehicules=new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<Utilisateur> chauffeurs=new ArrayList<>();


}