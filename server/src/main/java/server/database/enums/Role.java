package server.database.enums;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ADMIN("admin"),
    CHAIR("chair"),
    REVIEWER("reviewer"),
    USER("user");

    private static final Map<String, Role> BY_ROLE = new HashMap<>();

    static {
        for (Role r : values()) {
            BY_ROLE.put(r.role, r);
        }
    }

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static Role valueOfRole(String role) {
        return BY_ROLE.get(role);
    }
}
