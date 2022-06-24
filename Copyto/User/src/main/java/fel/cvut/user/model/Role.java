package fel.cvut.user.model;

/**
 * Enum user's roles
 */
public enum Role {
    ADMIN("ROLE_ADMIN"),
    CLIENT("ROLE_CLIENT"),
    COPYWRITER("ROLE_COPYWRITER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
