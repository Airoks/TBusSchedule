package ru.tblsk.owlz.busschedule.data.db.model;


import org.greenrobot.greendao.converter.PropertyConverter;

public class ScheduleTypeConverter
        implements PropertyConverter<ScheduleType, Integer> {

    @Override
    public ScheduleType convertToEntityProperty(Integer databaseValue) {
        for(ScheduleType type : ScheduleType.values()) {
            if(type.id == databaseValue) {
                return type;
            }
        }
        return null;
    }

    @Override
    public Integer convertToDatabaseValue(ScheduleType entityProperty) {
        return entityProperty == null ? null : entityProperty.id;
    }
}
