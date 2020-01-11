package server.database.utils;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import server.database.enums.Role;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class RoleAuthorization {
    private Map<String, List<String>> roles;

    public Map<String, List<String>> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, List<String>> roles) {
        this.roles = roles;
    }

    public static boolean isAuthorized(Role role, String reqURI) {

        Yaml yaml = new Yaml(new Constructor(RoleAuthorization.class));

        InputStream inputStream = RoleAuthorization.class
            .getResourceAsStream("/role_authorization.yaml");

        RoleAuthorization roleAuth = yaml.load(inputStream);

        List<String> authorizedURIs = roleAuth.getRoles().get(role.getRole());
        
        Boolean isAuthorized = authorizedURIs.stream().anyMatch(endpoint -> reqURI.contains(endpoint));

        return isAuthorized;
    }
}
