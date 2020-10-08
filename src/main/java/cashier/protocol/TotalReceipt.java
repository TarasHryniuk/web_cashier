package cashier.protocol;

import java.util.Objects;

/**
 * @author Taras Hryniuk, created on  08.10.2020
 * email : hryniuk.t@gmail.com
 */
public class TotalReceipt {
    private Integer id;
    private Long totalAmount;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalReceipt that = (TotalReceipt) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(totalAmount, that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalAmount);
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
