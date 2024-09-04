package com.example.bananasplit.dataModel;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Represents an expense in a group.
 * @author Arpad Horvath, Dennis Brockemeyer
 */
@Entity(foreignKeys =   {
        @ForeignKey(entity = Person.class,
            parentColumns = "personID",
            childColumns = "spenderID",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Group.class,
            parentColumns = "groupID",
            childColumns = "groupID",
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)})
public class Expense implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private int spenderID;
    private int groupID;
    private float amount;
    private Currency currency;
    private String description;
    private Person personWhoPaid;
    private ExpenseCategory category;
    private Date date;

    public Expense(String description, Person personWhoPaid, int groupID, float amount, Currency currency, ExpenseCategory category) {
        this.spenderID = personWhoPaid.getPersonID();
        this.groupID = groupID;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.personWhoPaid = personWhoPaid;
        this.category = category;
        date = new Date();

    }

    public boolean equals(Expense expense) {
        return this.id == expense.id &&
                this.spenderID == expense.spenderID &&
                this.groupID == expense.groupID &&
                this.amount == expense.amount &&
                this.currency.equals(expense.currency) &&
                this.description.equals(expense.description) &&
                this.personWhoPaid.equals(expense.personWhoPaid) &&
                this.category.equals(expense.category) &&
                this.date.equals(expense.date);
    }
    public float getAmount() {
        return this.amount;
    }


    public int getId() {
        return id;
    }

    public int getSpenderID() {
        return spenderID;
    }

    public Currency getCurrency() {
        return currency;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setSpenderID(int spenderID) {
        this.spenderID = spenderID;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPersonWhoPaid() {
        return personWhoPaid;
    }

    public void setPersonWhoPaid(Person personWhoPaid) {
        this.personWhoPaid = personWhoPaid;
    }

    public ExpenseCategory getCategory() {
        return category;
    }
    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }
    public Date getDate() {
        return date;
    }
    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("M. dd. yyyy", Locale.GERMANY); //TODO maybe change dependent on locale like us format
        return sdf.format(date);
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
