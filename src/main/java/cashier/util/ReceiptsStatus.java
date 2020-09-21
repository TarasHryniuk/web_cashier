package cashier.util;

/**
 * @author Taras Hryniuk, created on  20.09.2020
 * email : hryniuk.t@gmail.com
 */
public enum ReceiptsStatus {
    CREATED(0), CONFIRMED(1), COMPLETED(3), CANCELED(212);

    private Integer id;


    ReceiptsStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public ReceiptsStatus valueOf(Integer id){
        for (ReceiptsStatus value : values()) {
            if (value.getId() == id)
                return value;
        }
        return null;
    }

}
