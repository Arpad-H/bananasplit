package com.example.bananasplit.dataModel;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static List<Person> stringToPersonList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Person>>() {}.getType();
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
    @TypeConverter
    public static Person stringToPerson(String data) {
        if (data == null) {
            return null;
        }

        Type listType = new TypeToken<Person>() {}.getType();
        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String personToString(Person person) {
        return gson.toJson(person);
    }
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
//
//    @TypeConverter
//    public static int[][] stringToIntArray(String data) {
//        if (data == null) {
//            return null;
//        }
//
//        Type listType = new TypeToken<Game>() {}.getType();
//        return gson.fromJson(data, listType);
//    }
//    @TypeConverter
//    public static String intArrayToString(int[][] strokes) {
//        return gson.toJson(strokes);
//    }
//
//
//
//    @TypeConverter
//    public static Game stringToGame(String data) {
//        if (data == null) {
//            return null;
//        }
//
//        Type listType = new TypeToken<Game>() {}.getType();
//        return gson.fromJson(data, listType);
//    }
//    @TypeConverter
//    public static String gameToString(Game game) {
//        return gson.toJson(game);
//    }
}
