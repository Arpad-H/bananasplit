// TextUtils.java
package com.example.bananasplit.util.text;

import com.google.cloud.language.v1.Entity;

/**
 * Utility class for text-related operations.
 * @author Arpad Horvath
 */
public class TextUtils {

    /**
     * Checks if the given text is a valid decimal number.
     * @param text The text to check.
     * @return True if the text is a valid decimal number, false otherwise.
     */
    public static boolean isValidDecimalNumber(String text) {
        return text.matches("\\d+([.,]\\d+)?") && !text.matches("\\d+");
    }

    /**
     * Extracts the numerical value from the given entity.
     * @param entity The entity to extract the value from.
     * @return The numerical value of the entity, or 0.0 if the entity is not a number.
     */
    public static double extractNumberValue(Entity entity) {
        String text = entity.getName().replace(',', '.');
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
