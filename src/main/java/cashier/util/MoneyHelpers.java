
package cashier.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Currency;

/**
 * @author Taras Hryniuk, created on  19.09.2020
 * email : hryniuk.t@gmail.com
 */
public class MoneyHelpers {

    /**
     * @param amount in copecks
     * @return amount in grivnas in format (0.00)
     */
    public static String convertCopecksToGrivnas(long amount) {
        return convertCopecksToGrivnas(new BigDecimal(amount));
    }

    /**
     * @param amount in copecks
     * @return amount in grivnas in format (0{@literal <}<code>delimiter</code>{@literal> }00)
     */
    public static String convertCopecksToGrivnas(long amount, char delimiter) {
//        DecimalFormat df = new DecimalFormat("#0.00");
//
//        DecimalFormatSymbols custom = new DecimalFormatSymbols();
//        custom.setDecimalSeparator(delimiter);
//        df.setDecimalFormatSymbols(custom);
//
//        return df.format(new BigDecimal(amount).movePointLeft(2));

        return convertCopecksToGrivnas(amount).replace('.', delimiter);
    }

    /**
     * @param amount in copecks
     * @return amount in grivnas in format (0.00)
     */
    public static String convertCopecksToGrivnas(BigDecimal amount) {
        return convertCopecksToGrivnas(amount, 2);
    }

    /**
     * @param amount in copecks
     * @return amount in grivnas in format (0.{<code>scale</code>})
     */
    public static String convertCopecksToGrivnas(BigDecimal amount, int scale) {
        return convertCopecksToGrivnasObj(amount, scale).toPlainString();
    }

    /**
     * @param amount in copecks
     * @return amount in grivnas in format (0.{<code>scale</code>})
     */
    public static BigDecimal convertCopecksToGrivnasObj(BigDecimal amount, int scale) {
        return amount.movePointLeft(2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @param amount in grivnas in format (0.00)
     * @return amount in copecks
     */
    public static long convertGrivnasToCopecks(String amount) {
        return convertGrivnasToCopecks(new BigDecimal(amount));
    }

    /**
     * @param amount in grivnas in format (0.00)
     * @return amount in copecks
     */
    public static long convertGrivnasToCopecks(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP).movePointRight(2).longValue();
    }

    /**
     * @param amount in grivnas in format (0{@literal <}<code>delimiter</code>{@literal> }00)
     * @return amount in copecks
     */
    public static long convertGrivnasToCopecks(String amount, char delimiter) throws ParseException {
        return convertGrivnasToCopecks(amount.replace(delimiter, '.'));
    }

    /**
     * Checks for valid credit card number using Luhn-10 algorithm
     * @param cardNumber cardNumber. All non numeric symbols will be skipped.
     * @return <code>true</code> if credit card number is valid, <code>false</code> otherwise.
     */
    public static boolean isCreditCardNumberValid (String cardNumber) {
        int sum = 0;
        for (int digit, addend, count = 1, i = cardNumber.length() - 1; i >= 0 ; i--) {
            char symbol = cardNumber.charAt(i);

            if (!Character.isDigit(symbol))
                continue;

            digit = Integer.parseInt(String.valueOf(symbol));
            if (count % 2 == 0) {
                addend = digit * 2;
                if (addend > 9) {
                    addend -= 9;
                }
            } else {
                addend = digit;
            }
            sum += addend;

            count++;
        }
        return sum % 10 == 0;
    }

    /**
     * Returns the <code>Currency</code> instance for the given numeric currency code.
     * @param code ISO 4217 numeric currency code
     * @return the <code>Currency</code> instance for the given numeric currency code
     * @exception IllegalArgumentException if <code>code</code> is not
     * a supported ISO 4217 numeric currency code.
     */
    public static Currency getCurrencyByNumericCode(int code) {
        for(Currency c : Currency.getAvailableCurrencies()) {
            if(c.getNumericCode() == code) {
                return c;
            }
        }

        throw new IllegalArgumentException("Numeric currency code is not a supported ISO 4217: " + code);
    }

    public static String hideCreditCardNumber(String cardNumber) {
        if (cardNumber.length() <= 10)
            return cardNumber;

        char[] hiddenPart = new char[cardNumber.length() - 10];
        Arrays.fill(hiddenPart, '*');

        return cardNumber.substring(0, 6) + String.valueOf(hiddenPart) + cardNumber.substring(cardNumber.length() - 4);
    }

    public static BigDecimal addIfNotNull(BigDecimal a, BigDecimal b) {
        return a != null ?
                b != null ?
                        a.add(b) : a :
                b != null ?
                        b : null;
    }
}