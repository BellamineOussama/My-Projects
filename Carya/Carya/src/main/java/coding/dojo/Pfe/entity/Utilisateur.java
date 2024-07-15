package coding.dojo.Pfe.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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

public class Utilisateur {

    @Id
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles = new ArrayList<>();




}
