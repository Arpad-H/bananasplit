package com.example.bananasplit.dataModel;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * This class is used to convert complex data types to a format that Room can persist in the database.
 *
 * @author Dennis Brockmeyer, Arpad Horvath
 */
public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static List<Person> stringToPersonList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Person>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String personListToString(List<Person> someObjects) {
        return gson.toJson(someObjects);
    }

    @TypeConverter
    public static Currency fromDouble(Double value) {
        return value == null ? null : Currency.from(value);
    }

    @TypeConverter
    public static Double currencyToDouble(Currency currency) {
        return currency == null ? null : currency.getValueInEur();
    }

    /**
     * Converts a string to a Person object.
     *
     * @param data The string to convert.
     * @return The Person object.
     * @author Arpad Horvath
     */
    @TypeConverter
    public static Person stringToPerson(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<Person>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    /**
     * Converts a Person object to a string.
     *
     * @param person The Person object to convert.
     * @return The string representation of the Person object.
     * @author Arpad Horvath
     */
    @TypeConverter
    public static String personToString(Person person) {
        return gson.toJson(person);
    }

    /**
     * Converts a Long int  to a Date object.
     *
     * @param value The Long to be converted.
     * @return The Date object.
     * @author Arpad Horvath
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Converts a Date object to a Long int.
     *
     * @param date The Date object to be converted.
     * @return The Long int.\
     * @author Arpad Horvath
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
