package ru.tblsk.owlz.busschedule.data.db.model;


import org.greenrobot.greendao.converter.PropertyConverter;

public class FlightTypeConverter
        implements PropertyConverter<FlightType, Integer> {

    @Override
    public FlightType convertToEntityProperty(Integer databaseValue) {
        for(FlightType type : FlightType.values()) {
            if(type.id == databaseValue) {
                return type;
            }
        }
        return null;
    }

    @Override
    public Integer convertToDatabaseValue(FlightType entityProperty) {
        return entityProperty == null ? null : entityProperty.id;
    }
}
