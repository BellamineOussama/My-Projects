package coding.dojo.Pfe.service;


import coding.dojo.Pfe.entity.Utilisateur;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakUserService {




    UserRepresentation getUserById(String userId);
    void deleteUserById(String userId);
    void emailVerification(String userId);
    UserResource getUserResource(String userId);
    void updatePassword(String userId);
    public List<UserRepresentation> getAllUsers();

    public void synchronizeUsers();

    public RoleMappingResource getRoleResource(String userId);

    public List<Utilisateur> AllUser();

    public Utilisateur findUserById(String id);

    public Utilisateur findUserByUsername(String USerName);

    public List<RoleRepresentation> getUserRoles(String userId);
}