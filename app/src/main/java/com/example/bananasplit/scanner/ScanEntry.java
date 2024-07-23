package com.example.bananasplit.scanner;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bananasplit.dataModel.Currency;

public class ScanEntry implements Parcelable {
    private final String name;
    private final int quantity;
    private final double unitPrice;
    private final double totalPrice;

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public ScanEntry(String name, int quantity, double unitPrice, double totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }


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

    @Override
    public int describeContents() {
        return 0;

    }
    protected ScanEntry(Parcel in) {
        name = in.readString();
        quantity = in.readInt();
        unitPrice = in.readDouble();
        totalPrice = in.readDouble();
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeDouble(unitPrice);
        dest.writeDouble(totalPrice);
    }
}
