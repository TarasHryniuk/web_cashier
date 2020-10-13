package cashier.util;

import cashier.dao.ReceiptsDaoImpl;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Taras Hryniuk, created on  06.10.2020
 * email : hryniuk.t@gmail.com
 */
public class GenerateReceiptNumber {
    private static ReceiptsDaoImpl receiptsDao;

    private static AtomicInteger no = new AtomicInteger(1);

    private GenerateReceiptNumber(AtomicInteger no) {
        this.no = no;
    }

    public static Integer getReceiptNo() {

        if (no.get() == 1) {
            receiptsDao = new ReceiptsDaoImpl();
            no = new AtomicInteger(receiptsDao.getLastReceiptNo());
        }

        return no.incrementAndGet();
    }
}
