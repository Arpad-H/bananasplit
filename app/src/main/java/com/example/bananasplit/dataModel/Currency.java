package com.example.bananasplit.dataModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum Currency implements Serializable {
    EUR(1),
    USD(0.9153), // USA
    CNY(0.126), // China
    CHF(1.035), // Schweiz
    GBP(1.190), // Großbrittanien
    TRY(0.02765); // Türkey

    private final double valueInEur;

    Currency(double valueInEur) {
        this.valueInEur = valueInEur;
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
}