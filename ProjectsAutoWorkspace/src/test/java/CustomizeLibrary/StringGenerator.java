package CustomizeLibrary;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Created by dinhN on 2/21/2017.
 */
public class StringGenerator {
    public static String generateRandomString(){
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        return generatedString;
    }
}
