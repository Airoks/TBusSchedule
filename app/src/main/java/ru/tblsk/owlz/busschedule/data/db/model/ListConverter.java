package ru.tblsk.owlz.busschedule.data.db.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListConverter implements PropertyConverter<List<Integer>, String> {
    @Override
    public List<Integer> convertToEntityProperty(String databaseValue) {
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        return new Gson().fromJson(databaseValue, type);
    }

    @Override
    public String convertToDatabaseValue(List<Integer> entityProperty) {
        return entityProperty != null ? new Gson().toJson(entityProperty) : null;
    }
}
