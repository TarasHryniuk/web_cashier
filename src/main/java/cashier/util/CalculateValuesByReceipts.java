package cashier.util;

import cashier.Statuses;
import cashier.dao.entity.Receipt;
import cashier.protocol.TotalReceipt;

import java.io.File;
import java.math.BigDecimal;
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

    public static Long getTotalSuccessReceiptsAmountTR(List<TotalReceipt> receipts) {
        Long startPrice = 0L;

        for (TotalReceipt receipt : receipts) {
            if(receipt.getStatus() == Statuses.SUCCESS.shortValue())
                startPrice += new BigDecimal(receipt.getTotalAmount()).movePointRight(2).longValue();
        }

        return startPrice;
    }

    public static Long getTotalCancelReceiptsAmountTR(List<TotalReceipt> receipts) {
        Long startPrice = 0L;

        for (TotalReceipt receipt : receipts) {
            if(receipt.getStatus() == Statuses.CANCELED.shortValue())
                startPrice += new BigDecimal(receipt.getTotalAmount()).movePointRight(2).longValue();
        }

        return startPrice;
    }

    public static Long getTotalReceiptsCountTR(List<TotalReceipt> receipts) {
        return receipts.stream().count();
    }

    public static Long getTotalReceiptsCount(List<Receipt> receipts) {
        return receipts.stream().count();
    }

    public static Long getTotalSuccessReceiptsCountTR(List<TotalReceipt> receipts) {
        return receipts.stream().filter(receipt -> receipt.getStatus() == Statuses.SUCCESS.shortValue()).count();
    }

    public static Long getTotalSuccessReceiptsCount(List<Receipt> receipts) {
        return receipts.stream().filter(receipt -> receipt.getStatus() == Statuses.SUCCESS.shortValue()).count();
    }

    public static Long getTotalCancelledReceiptsCountTR(List<TotalReceipt> receipts) {
        return receipts.stream().filter(receipt -> receipt.getStatus() == Statuses.CANCELED.shortValue()).count();
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
