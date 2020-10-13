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
    public void tesTotalReceiptGetProductID(){
        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setStatus(Statuses.SUCCESS.shortValue());
        totalReceipt.setTotalAmount("1");
        totalReceipt.setDate("");
        totalReceipt.setLogin("login");
        totalReceipt.setId(1);
        totalReceipt.setActive(true);
        totalReceipts.add(totalReceipt);
        assertEquals(new Integer(1), totalReceipt.getId());
    }

    @Test
    public void tesTotalReceiptGetStatus(){
        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setStatus(Statuses.SUCCESS.shortValue());
        totalReceipt.setTotalAmount("1");
        totalReceipt.setDate("");
        totalReceipt.setLogin("login");
        totalReceipt.setId(1);
        totalReceipt.setActive(true);
        totalReceipts.add(totalReceipt);
        assertEquals((Short) Statuses.SUCCESS.shortValue(), totalReceipt.getStatus());
    }

    @Test
    public void tesTotalReceiptGetTotalAmount(){
        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setStatus(Statuses.SUCCESS.shortValue());
        totalReceipt.setTotalAmount("1");
        totalReceipt.setDate("");
        totalReceipt.setLogin("login");
        totalReceipt.setId(1);
        totalReceipt.setActive(true);
        totalReceipts.add(totalReceipt);
        assertEquals("1", totalReceipt.getTotalAmount());
    }

    @Test
    public void tesTotalReceiptGetDate(){
        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setStatus(Statuses.SUCCESS.shortValue());
        totalReceipt.setTotalAmount("1");
        totalReceipt.setDate("");
        totalReceipt.setLogin("login");
        totalReceipt.setId(1);
        totalReceipt.setActive(true);
        totalReceipts.add(totalReceipt);
        assertEquals("", totalReceipt.getDate());
    }

    @Test
    public void tesTotalReceiptGetLongin(){
        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setStatus(Statuses.SUCCESS.shortValue());
        totalReceipt.setTotalAmount("1");
        totalReceipt.setDate("");
        totalReceipt.setLogin("login");
        totalReceipt.setId(1);
        totalReceipt.setActive(true);
        totalReceipts.add(totalReceipt);
        assertEquals("login", totalReceipt.getLogin());
    }

    @Test
    public void tesTotalReceiptGetActive(){
        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setStatus(Statuses.SUCCESS.shortValue());
        totalReceipt.setTotalAmount("1");
        totalReceipt.setDate("");
        totalReceipt.setLogin("login");
        totalReceipt.setId(1);
        totalReceipt.setActive(true);
        totalReceipts.add(totalReceipt);
        assertEquals(true, totalReceipt.getActive());
    }

    @Test
    public void tesTotalReceiptToString(){
        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setStatus(Statuses.SUCCESS.shortValue());
        totalReceipt.setTotalAmount("1");
        totalReceipt.setDate("");
        totalReceipt.setLogin("login");
        totalReceipt.setId(1);
        totalReceipt.setActive(true);
        totalReceipts.add(totalReceipt);
        assertEquals("TotalReceipt{id=1, totalAmount=1}", totalReceipt.toString());
    }

    @Test
    public void tesTotalHashCode(){
        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setStatus(Statuses.SUCCESS.shortValue());
        totalReceipt.setTotalAmount("1");
        totalReceipt.setDate("");
        totalReceipt.setLogin("login");
        totalReceipt.setId(1);
        totalReceipt.setActive(true);
        totalReceipts.add(totalReceipt);
        assertEquals(32, totalReceipt.hashCode());
    }

    @Test
    public void tesTotalEquals(){
        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setStatus(Statuses.SUCCESS.shortValue());
        totalReceipt.setTotalAmount("1");
        totalReceipt.setDate("");
        totalReceipt.setLogin("login");
        totalReceipt.setId(1);
        totalReceipt.setActive(true);
        totalReceipts.add(totalReceipt);
        assertEquals(true, totalReceipt.equals(totalReceipt));
    }

    @Test
    public void testGetId(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals(new Integer(1), receipt.getId());
    }

    @Test
    public void testGetReceiptId(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals(new Integer(1), receipt.getReceiptId());
    }

    @Test
    public void testGetProductID(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals(new Integer(1), receipt.getProductID());
    }

    @Test
    public void testGetProductName(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals("name", receipt.getProductName());
    }

    @Test
    public void testGetUserID(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals(new Integer(1), receipt.getUserID());
    }

    @Test
    public void testGetCancelUserID(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals(new Integer(1), receipt.getCancelUserID());
    }

    @Test
    public void testGetCount(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals(new Integer(1), receipt.getCount());
    }

    @Test
    public void testGetPrice(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals(new Long(1), receipt.getPrice());
    }

    @Test
    public void testGetStatus(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals((Short) Statuses.SUCCESS.shortValue(), receipt.getStatus());
    }

    @Test
    public void testGetProcessingTime(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals(new Long(1), receipt.getProcessingTime());
    }

    @Test
    public void testGetCancelTime(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals(new Long(1), receipt.getCancelTime());
    }

    @Test
    public void testGetUserLogin(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals("login", receipt.getUserLogin());
    }

    @Test
    public void testToString(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals("Receipts{id=1, productID=1, receiptId=1, productName=name, userID=1, cancelUserID=1, count=1, price=1, status=3, processingTime=1, cancelTime=1, userLogin=login}", receipt.toString());
    }

    @Test
    public void testHashCode(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");
        assertEquals(-1140962273, receipt.hashCode());
    }

    @Test
    public void testEquals(){
        Receipt receipt = new Receipt();
        receipt.setId(1);
        receipt.setReceiptId(1);
        receipt.setProductID(1);
        receipt.setProductName("name");
        receipt.setUserID(1);
        receipt.setCancelUserID(1);
        receipt.setCount(1);
        receipt.setPrice(1L);
        receipt.setStatus(Statuses.SUCCESS.shortValue());
        receipt.setProcessingTime(1L);
        receipt.setCancelTime(1L);
        receipt.setUserLogin("login");

        Receipt receipt1 = new Receipt();
        receipt1.setId(1);
        receipt1.setReceiptId(1);
        receipt1.setProductID(1);
        receipt1.setProductName("name");
        receipt1.setUserID(1);
        receipt1.setCancelUserID(1);
        receipt1.setCount(1);
        receipt1.setPrice(1L);
        receipt1.setStatus(Statuses.SUCCESS.shortValue());
        receipt1.setProcessingTime(1L);
        receipt1.setCancelTime(1L);
        receipt1.setUserLogin("login");
        assertEquals(true, receipt.equals(receipt1));
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

    @Test
    public void totalReceiptToStringTest(){

        TotalReceipt totalReceipt = new TotalReceipt();
        totalReceipt.setActive(true);
        totalReceipt.setId(1);
        totalReceipt.setDate("");
        totalReceipt.setTotalAmount("0");
        totalReceipt.setStatus(Statuses.CREATED.shortValue());

        assertEquals("TotalReceipt{id=1, totalAmount=0}", totalReceipt.toString());

    }
}
