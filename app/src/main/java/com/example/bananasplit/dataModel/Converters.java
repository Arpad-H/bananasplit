package com.example.bananasplit.dataModel;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
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
    public static Currency fromFloat(Float value) {
        return value == null ? null : new Currency(value);
    }

    @TypeConverter
    public static Float currencyToFloat(Currency currency) {
        return currency == null ? null : currency.valueInEur;
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
