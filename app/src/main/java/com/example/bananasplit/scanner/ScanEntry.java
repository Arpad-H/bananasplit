package com.example.bananasplit.scanner;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Represents an entry for a scanned item.
 * Implements Parcelable to allow the object to be passed between Android components.
 * @author Arpad Horvath
 */
public class ScanEntry implements Parcelable {
    private final String name;
    private final int quantity;
    private final double unitPrice;
    private final double totalPrice;

    /**
     * Constructs a ScanEntry instance.
     *
     * @param name       The name of the item.
     * @param quantity   The quantity of the item.
     * @param unitPrice  The price per unit of the item.
     * @param totalPrice The total price of the item (quantity * unit price).
     */
    public ScanEntry(String name, int quantity, double unitPrice, double totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the name of the item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the quantity of the item.
     *
     * @return The quantity of the item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the unit price of the item.
     *
     * @return The unit price of the item.
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Gets the total price of the item.
     *
     * @return The total price of the item.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Parcelable.Creator implementation for ScanEntry.
     */
    public static final Creator<ScanEntry> CREATOR = new Creator<ScanEntry>() {
        @Override
        public ScanEntry createFromParcel(Parcel in) {
            return new ScanEntry(in);
        }

        @Override
        public ScanEntry[] newArray(int size) {
            return new ScanEntry[size];
        }
    };

    /**
     * Constructor to create a ScanEntry from a Parcel.
     *
     * @param in The Parcel containing the ScanEntry data.
     */
    protected ScanEntry(Parcel in) {
        name = in.readString();
        quantity = in.readInt();
        unitPrice = in.readDouble();
        totalPrice = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeDouble(unitPrice);
        dest.writeDouble(totalPrice);
    }
}
