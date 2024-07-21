package com.example.bananasplit.scanner;

public class ScanEntry {
    private final String name;
    private final int quantity;
    private final double unitPrice;
    private final double totalPrice;

    public ScanEntry(String name, int quantity, double unitPrice, double totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ScanEntry{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
