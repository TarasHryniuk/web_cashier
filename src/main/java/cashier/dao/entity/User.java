package cashier.dao.entity;

import java.util.Objects;

/**
 * @author Taras Hryniuk, created on  20.09.2020
 * email : hryniuk.t@gmail.com
 */
public class User {
    private Integer id;
    private boolean active;
    private String login;
    private String authCode;
    private Integer role;
    private String fullName;
    private String roleName;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

//    public Integer getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Integer roles) {
//        this.roles = roles;
//    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return active == user.active &&
                Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(authCode, user.authCode) &&
                Objects.equals(role, user.role) &&
                Objects.equals(fullName, user.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, login, authCode, role, fullName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User{id=").append(id)
                .append(", active=").append(active)
                .append(", login=").append(login)
                .append(", role=").append(role)
                .append(", fullName=").append(fullName)
                .append("}");
        return stringBuilder.toString();
    }
}
