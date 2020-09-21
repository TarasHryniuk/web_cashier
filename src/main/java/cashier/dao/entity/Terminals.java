package cashier.dao.entity;

import java.util.Objects;

/**
 * @author Taras Hryniuk, created on  20.09.2020
 * email : hryniuk.t@gmail.com
 */
public class Terminals {
    private Integer id;
    private boolean active;
    private String address;

    public Terminals() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Terminals terminals = (Terminals) o;
        return active == terminals.active &&
                Objects.equals(id, terminals.id) &&
                Objects.equals(address, terminals.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, address);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Terminals{id=").append(id)
                .append(", active=").append(active)
                .append(", address='").append(address)
                .append("\'}");
        return stringBuilder.toString();
    }
}
