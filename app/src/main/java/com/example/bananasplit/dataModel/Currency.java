package com.example.bananasplit.dataModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum for the different currencies
 * Each currency has a value in EUR for conversion and a symbol
 *
 * @author Arpad Horvath, Dennis Brockmeyer (where specified)
 */

public enum Currency implements Serializable {
    EUR(1, "€"),
    USD(0.9153, "$"), // USA
    CNY(0.126, "CN¥"), // China
    CHF(1.035, "CHF"), // Schweiz
    GBP(1.190, "£"), // Großbritannien
    TRY(0.02765, "₺"), // Türkei
    JPY(0.0058, "¥"); // Japan

    private final double valueInEur;
    private final String currencySymbol;
    private static final Map<Double, Currency> _map = new HashMap<>();

    // idea from https://stackoverflow.com/a/49179205
    static {
        for (Currency currency : Currency.values())
            _map.put(currency.valueInEur, currency);
    }

    Currency(double valueInEur, String currencySymbol) {
        this.valueInEur = valueInEur;
        this.currencySymbol = currencySymbol;
    }

    /**
     * Returns the EUR Value for the Currency
     *
     * @return the EUR Value
     * @author Dennis Brockmeyer
     */
    public double getValueInEur() {
        return valueInEur;
    }


    /**
     * Creates a Currency Object from the given double value
     *
     * @param value the double value to create to currency from
     * @author Dennis Brockmeyer
     */
    public static Currency from(Double value) {
        return _map.get(value);
    }

    /**
     * Returns the currency symbol for displaying purposes
     *
     * @return a string containing the currency symbol
     * @author Dennis Brockmeyer
     */
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     * Creates a Currency Object from the given currency Symbol
     *
     * @param text the currency symbol
     * @return the currency object or null
     * @author Dennis Brockmeyer, Arpad Horvath
     * */
    public static Currency fromString(String text) {
        for (Currency c : Currency.values()) {
            if (c.currencySymbol.equalsIgnoreCase(text)) {
                return c;
            }
        }
        return null;
    }
}