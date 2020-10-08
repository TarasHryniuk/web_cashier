package cashier.protocol;

import java.util.Objects;

/**
 * @author Taras Hryniuk, created on  08.10.2020
 * email : hryniuk.t@gmail.com
 */
public class TotalReceipt {
    private Integer id;
    private Boolean active;
    private String totalAmount;
    private String login;
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalReceipt that = (TotalReceipt) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TotalReceipt{id=").append(id)
                .append(", totalAmount=").append(totalAmount)
                .append('}');
        return stringBuilder.toString();
    }
}
