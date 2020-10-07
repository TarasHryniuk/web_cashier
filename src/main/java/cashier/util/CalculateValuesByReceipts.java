package cashier.util;

import cashier.Statuses;
import cashier.dao.entity.Receipt;

import java.util.List;

/**
 * @author Taras Hryniuk, created on  07.10.2020
 * email : hryniuk.t@gmail.com
 */
public class CalculateValuesByReceipts {

    public static Long getTotalAmount(List<Receipt> receipts) {
        Long startPrice = 0L;

        for (Receipt receipt : receipts) {
            startPrice += (receipt.getPrice() * receipt.getCount());
        }

        return startPrice;
    }

    public static Long getTotalSuccessReceiptsAmount(List<Receipt> receipts) {
        Long startPrice = 0L;

        for (Receipt receipt : receipts) {
            if(receipt.getStatus() == Statuses.SUCCESS.shortValue()) startPrice += (receipt.getPrice() * receipt.getCount());
        }

        return startPrice;
    }

    public static Long getTotalReceiptsCount(List<Receipt> receipts) {
        return receipts.stream().count();
    }
    public static Long getTotalSuccessReceiptsCount(List<Receipt> receipts) {
        return receipts.stream().filter(receipt -> receipt.getStatus() == Statuses.SUCCESS.shortValue()).count();
    }

    public static Long getTotalCancelledReceiptsCount(List<Receipt> receipts) {
        return receipts.stream().filter(receipt -> receipt.getStatus() == Statuses.CANCELED.shortValue()).count();
    }

    public static Long getTotalCancelReceiptsAmount(List<Receipt> receipts) {
        Long startPrice = 0L;

        for (Receipt receipt : receipts) {
            if(receipt.getStatus() == Statuses.CANCELED.shortValue()) startPrice += (receipt.getPrice() * receipt.getCount());
        }

        return startPrice;
    }

}
