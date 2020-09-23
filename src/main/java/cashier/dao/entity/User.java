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
    private Long roles;
    private Integer terminalId;
    private String fullName;

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


    public Long getRoles() {
        return roles;
    }

    public void setRoles(Long roles) {
        this.roles = roles;
    }

    public Integer getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Integer terminalId) {
        this.terminalId = terminalId;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
                Objects.equals(roles, user.roles) &&
                Objects.equals(terminalId, user.terminalId) &&
                Objects.equals(fullName, user.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, login, authCode, roles, terminalId, fullName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("User{id=").append(id)
                .append(", active=").append(active)
                .append(", login=").append(login)
                .append(", authCode='").append(authCode).append('\'')
                .append(", roles=").append(roles)
                .append(", terminalId=").append(terminalId)
                .append(", fullName=").append(fullName)
                .append("}");
        return stringBuilder.toString();
    }

    public boolean hasRole(Role role) {
        return (roles & role.getMask()) != 0;
    }

    public void addRole(Role role) {
        roles = roles | role.getMask();
    }

    public void removeRole(Role role) {
        roles = roles ^ role.getMask();
    }

    public static void main(String[] args) {
        User uo = new User();
        uo.setRoles(666L);
        System.out.println(uo.hasRole(Role.HIGH_CASHIER));
        uo.addRole(Role.CASHIER);
//        uo.addRole(Role.CHART_ACCOUNT);
        System.out.println(uo.hasRole(Role.HIGH_CASHIER));
//        uo.removeRole(Role.CASHIER);
//        System.out.println(uo.hasRole(Role.CASHIER));
    }
}
