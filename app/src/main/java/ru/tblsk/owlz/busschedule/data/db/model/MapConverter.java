package ru.tblsk.owlz.busschedule.data.db.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapConverter implements PropertyConverter<Map<Integer, ArrayList<Integer>>, String> {
    @Override
    public Map<Integer, ArrayList<Integer>> convertToEntityProperty(String databaseValue) {
        Type type = new TypeToken<HashMap<Integer, ArrayList<Integer>>>(){}.getType();
        return new Gson().fromJson(databaseValue, type);
    }

    @Override
    public String convertToDatabaseValue(Map<Integer, ArrayList<Integer>> entityProperty) {
        Type type = new TypeToken<HashMap<Integer, ArrayList<Integer>>>(){}.getType();
        return entityProperty != null ? new Gson().toJson(entityProperty, type) : null;
    }
}
