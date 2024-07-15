package coding.dojo.Pfe.service;


import coding.dojo.Pfe.repository.UtilisateurRepository;
import coding.dojo.Pfe.entity.Utilisateur;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j

public class KeycloakUserServiceImpl implements KeycloakUserService{

    @Value("${keycloak.realm}")
    private String realm;
    @Autowired
    private Keycloak keycloak;
    @Autowired
    private UtilisateurRepository userRepository;





    public List<UserRepresentation> getAllUsers() {
        return keycloak.realm(realm).users().list();
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return  getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public void deleteUserById(String userId) {
        getUsersResource().delete(userId);
        this.userRepository.deleteById(userId);
    }


    @Override
    public void emailVerification(String userId){
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    public UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    public RoleMappingResource getRoleResource(String userId){
        return getUsersResource().get(userId).roles();
    }

    @Override
    public void updatePassword(String userId) {
        UserResource userResource = getUserResource(userId);
        List<String> actions= new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);
    }

    public void synchronizeUsers() {
        List<UserRepresentation> keycloakUsers = getAllUsers();
        List<String> keycloakUsernames = keycloakUsers.stream()
                .map(UserRepresentation::getUsername)
                .collect(Collectors.toList());
        List<Utilisateur> dbUsers = userRepository.findAll();
        for (Utilisateur dbUser : dbUsers) {
            if (!keycloakUsernames.contains(dbUser.getUsername())) {
                userRepository.delete(dbUser);
            }
        }

        for (UserRepresentation keycloakUser : keycloakUsers) {
            Optional<Utilisateur> existingUser = userRepository.findByUsername(keycloakUser.getUsername());
            if (existingUser.isPresent()) {
                Utilisateur Utilisateur = existingUser.get();
                Utilisateur.setFirstName(keycloakUser.getFirstName());
                Utilisateur.setLastName(keycloakUser.getLastName());
                Utilisateur.setEmail(keycloakUser.getEmail());
                List<RoleRepresentation> roleRepresentations = getUserRoles(keycloakUser.getId());
                if(Utilisateur.getRoles() == null)
                   Utilisateur.setRoles(new ArrayList<>());
                roleRepresentations.stream()
                        .map(RoleRepresentation::getName)
                        .filter(name -> !Utilisateur.getRoles().contains(name))
                        .forEach(Utilisateur.getRoles()::add);
                userRepository.save(Utilisateur);
            } else {
                Utilisateur newUser = new Utilisateur();
                newUser.setId(keycloakUser.getId());
                newUser.setUsername(keycloakUser.getUsername());
                newUser.setEmail(keycloakUser.getEmail());
                newUser.setFirstName(keycloakUser.getFirstName());
                newUser.setLastName(keycloakUser.getLastName());
                List<RoleRepresentation> roleRepresentations = getUserRoles(keycloakUser.getId());
                newUser.setRoles(new ArrayList<>());
                roleRepresentations.forEach(r -> {
                    newUser.getRoles().add(r.getName());
                });
                userRepository.save(newUser);
            }
        }
    }


    @Override
    public List<Utilisateur> AllUser() {

        return this.userRepository.findAll();
    }

    public Utilisateur findUserById(String id){
        Optional<Utilisateur> optionalUser = this.userRepository.findById(id);
        if(optionalUser.isPresent()){
            Utilisateur user = optionalUser.get();
            return user;
        }
        return null;
    }

    @Override
    public Utilisateur findUserByUsername(String USerName) {
        Optional<Utilisateur> optionalUser = this.userRepository.findByUsername(USerName);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        return null;
    }

    public List<RoleRepresentation> getUserRoles(String userId) {
        RealmResource realmResource = keycloak.realm(realm);
        UserResource userResource = realmResource.users().get(userId);
        return userResource.roles().realmLevel().listEffective();
    }


}
