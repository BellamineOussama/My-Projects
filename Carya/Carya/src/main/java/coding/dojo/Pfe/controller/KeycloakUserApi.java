package coding.dojo.Pfe.controller;

import coding.dojo.Pfe.entity.Utilisateur;
import coding.dojo.Pfe.service.KeycloakUserService;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class KeycloakUserApi {


    private final KeycloakUserService keycloakUserService;






    @GetMapping
    public UserRepresentation getUser(Principal principal) {

        return keycloakUserService.getUserById(principal.getName());
    }
    @GetMapping("all")
    public List<UserRepresentation> All() {

        return keycloakUserService.getAllUsers();
    }
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        keycloakUserService.deleteUserById(userId);
    }
    @PostMapping("san")
    public void san(){
        this.keycloakUserService.synchronizeUsers();
    }
    @GetMapping("Al")
    public List<Utilisateur> findAll(){
        return this.keycloakUserService.AllUser();
    }
    @PutMapping("/{userId}/send-verify-email")
    public void sendVerificationEmail(@PathVariable String userId) {
        keycloakUserService.emailVerification(userId);
    }
    @PutMapping("/update-password")
    public void updatePassword(Principal principal) {
        keycloakUserService.updatePassword(principal.getName());
    }

    @GetMapping("/{id}")
    public Utilisateur FindUserById(@PathVariable String id){
        return this.keycloakUserService.findUserById(id);
    }

    @GetMapping("/name/{username}")
    public Utilisateur findUser(@PathVariable String username){
        return this.keycloakUserService.findUserByUsername(username);
    }


    @GetMapping("{id}/role")
    public List<RoleRepresentation> getRoleUser(@PathVariable String id){
        return this.keycloakUserService.getUserRoles(id);
    }




}
