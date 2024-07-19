package com.example.bananasplit.dataModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum Currency implements Serializable {
    EUR(1, "€"),
    USD(0.9153, "$"), // USA
    CNY(0.126, "CN¥"), // China
    CHF(1.035, "CHF" ), // Schweiz
    GBP(1.190, "£" ), // Großbrittanien
    TRY(0.02765, "₺" ), // Türkey
    JPY(0.0058, "¥"); // Japan

    private final double valueInEur;
    private final String currencySymbol;

    Currency(double valueInEur, String currencySymbol) {
        this.valueInEur = valueInEur;
        this.currencySymbol = currencySymbol;
    }

    public double getValueInEur() {
        return valueInEur;
    }

    // idea from https://stackoverflow.com/a/49179205
    private static final Map<Double, Currency> _map = new HashMap<>();
    static
    {
        for (Currency currency : Currency.values())
            _map.put(currency.valueInEur, currency);
    }

    public static Currency from(Double value) {
        return _map.get(value);
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }
    public static Currency fromString(String text) {
        for (Currency c : Currency.values()) {
            if (c.currencySymbol.equalsIgnoreCase(text)) {
                return c;
            }
        }
        return null;
    }
}