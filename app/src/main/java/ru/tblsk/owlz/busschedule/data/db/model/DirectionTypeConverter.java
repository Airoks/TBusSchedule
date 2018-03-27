package ru.tblsk.owlz.busschedule.data.db.model;


import org.greenrobot.greendao.converter.PropertyConverter;

public class DirectionTypeConverter
        implements PropertyConverter<DirectionType, Integer> {

    @Override
    public DirectionType convertToEntityProperty(Integer databaseValue) {
        for(DirectionType type : DirectionType.values()) {
            if(type.id == databaseValue) {
                return type;
            }
        }
        return null;
    }

    @Override
    public Integer convertToDatabaseValue(DirectionType entityProperty) {
        return entityProperty == null ? null : entityProperty.id;
    }
}
