
package cashier.util;

import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Taras Hryniuk, created on  20.09.2020
 * email : hryniuk.t@gmail.com
 */
public class StringHelpers {

    public static final String EMPTY_STRING = "";

    public static boolean isNullOrEmpty(String string) {
        return (string == null || EMPTY_STRING.equals(string));
    }

    public static boolean isNumberString(String s) {
        for (int j = 0; j < s.length(); j++) {
            if (!Character.isDigit(s.charAt(j))) {
                return false;
            }
        }

        return s.length() > 0;
    }

    public static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    public static String digest(String message) {
        try {
            byte[] bytesOfMessage = message.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");

            return byteArray2Hex(md.digest(bytesOfMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
        System.out.println(StringHelpers.digest("login" + "password"));
    }

}
