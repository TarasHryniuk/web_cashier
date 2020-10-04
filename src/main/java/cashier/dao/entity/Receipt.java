package cashier.dao.entity;

import java.util.Objects;

/**
 * @author Taras Hryniuk, created on  20.09.2020
 * email : hryniuk.t@gmail.com
 */
public class Receipt {
    private Integer id;
    private Integer productID;
    private Integer userID;
    private Integer cancelUserID;
    private Integer count;
    private Long price;
    private Short status;
    private Long processingTime;
    private Long cancelTime;


    public Receipt() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getCancelUserID() {
        return cancelUserID;
    }

    public void setCancelUserID(Integer cancelUserID) {
        this.cancelUserID = cancelUserID;
    }

    public Long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Long processingTime) {
        this.processingTime = processingTime;
    }

    public Long getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Long cancelTime) {
        this.cancelTime = cancelTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(id, receipt.id) &&
                Objects.equals(productID, receipt.productID) &&
                Objects.equals(userID, receipt.userID) &&
                Objects.equals(cancelUserID, receipt.cancelUserID) &&
                Objects.equals(count, receipt.count) &&
                Objects.equals(price, receipt.price) &&
                Objects.equals(status, receipt.status) &&
                Objects.equals(processingTime, receipt.processingTime) &&
                Objects.equals(cancelTime, receipt.cancelTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productID, userID, cancelUserID, count, price, status, processingTime, cancelTime);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Receipts{id=").append(id)
                .append(", productID=").append(productID)
                .append(", userID=").append(userID)
                .append(", cancelUserID=").append(cancelUserID)
                .append(", count=").append(count)
                .append(", price=").append(price)
                .append(", status=").append(status)
                .append(", processingTime=").append(processingTime)
                .append(", cancelTime=").append(cancelTime)
                .append('}');
        return stringBuilder.toString();
    }
}
