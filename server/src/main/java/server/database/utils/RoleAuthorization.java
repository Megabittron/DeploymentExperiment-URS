package server.database.utils;

import java.util.List;
import java.util.Map;

public class RoleAuthorization {
    private Map<String, Map<String, List<String>>> roles;

    public Map<String, Map<String, List<String>>> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, Map<String, List<String>>> roles) {
        this.roles = roles;
    }
}
