import cashier.Statuses;
import cashier.dao.entity.Receipt;
import cashier.protocol.TotalReceipt;
import cashier.util.CalculateValuesByReceipts;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @author Taras Hryniuk, created on  12.10.2020
 * email : hryniuk.t@gmail.com
 */
public class CalculateValuesByReceiptsTest {

     public List<Receipt> list = new LinkedList<>();
     public List<TotalReceipt> totalReceipts = new LinkedList<>();

    {
        Receipt receipt = new Receipt();
        receipt.setReceiptId(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setUserLogin("login");
        receipt.setProductName("name");
        receipt.setCancelUserID(1);
        receipt.setId(1);
        receipt.setProcessingTime(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        list.add(receipt);

        Receipt cancelReceipt = new Receipt();
        cancelReceipt.setReceiptId(1);
        cancelReceipt.setCount(1);
        cancelReceipt.setPrice(1L);
        cancelReceipt.setUserLogin("login");
        cancelReceipt.setProductName("name");
        cancelReceipt.setCancelUserID(1);
        cancelReceipt.setId(1);
        cancelReceipt.setProcessingTime(1L);
        cancelReceipt.setStatus(Statuses.CANCELED.shortValue());
        list.add(cancelReceipt);

        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setStatus(Statuses.SUCCESS.shortValue());
        totalReceipt.setTotalAmount("1");
        totalReceipt.setDate("");
        totalReceipt.setLogin("login");
        totalReceipt.setId(1);
        totalReceipt.setActive(true);
        totalReceipts.add(totalReceipt);

        TotalReceipt totalCancelReceipt = new TotalReceipt();
        totalCancelReceipt.setStatus(Statuses.CANCELED.shortValue());
        totalCancelReceipt.setTotalAmount("1");
        totalCancelReceipt.setDate("");
        totalCancelReceipt.setLogin("login");
        totalCancelReceipt.setId(1);
        totalCancelReceipt.setActive(false);
        totalReceipts.add(totalCancelReceipt);
    }

    @Test
    public void getTotalAmount() {
        assertEquals(new Long(2), CalculateValuesByReceipts.getTotalAmount(list));
    }

    @Test
    public void getTotalSuccessReceiptsAmount() {
        assertEquals(new Long(1), CalculateValuesByReceipts.getTotalSuccessReceiptsAmount(list));
    }

    @Test
    public void getTotalSuccessReceiptsAmountTR() {
        assertEquals(new Long(100), CalculateValuesByReceipts.getTotalSuccessReceiptsAmountTR(totalReceipts));
    }

    @Test
    public void getTotalCancelReceiptsAmountTR() {
        assertEquals(new Long(100), CalculateValuesByReceipts.getTotalCancelReceiptsAmountTR(totalReceipts));
    }

    @Test
    public void getTotalReceiptsCountTR() {
        assertEquals(new Long(2), CalculateValuesByReceipts.getTotalReceiptsCountTR(totalReceipts));
    }

    @Test
    public void getTotalReceiptsCount() {
        assertEquals(new Long(2), CalculateValuesByReceipts.getTotalReceiptsCount(list));
    }

    @Test
    public void getTotalSuccessReceiptsCountTR() {
        assertEquals(new Long(1), CalculateValuesByReceipts.getTotalSuccessReceiptsCountTR(totalReceipts));
    }

    @Test
    public void getTotalSuccessReceiptsCount() {
        assertEquals(new Long(1), CalculateValuesByReceipts.getTotalSuccessReceiptsCount(list));
    }

    @Test
    public void getTotalCancelledReceiptsCountTR() {
        assertEquals(new Long(1), CalculateValuesByReceipts.getTotalCancelledReceiptsCountTR(totalReceipts));
    }

    @Test
    public void getTotalCancelReceiptsAmount() {
        assertEquals(new Long(1), CalculateValuesByReceipts.getTotalCancelReceiptsAmount(list));
    }
}
