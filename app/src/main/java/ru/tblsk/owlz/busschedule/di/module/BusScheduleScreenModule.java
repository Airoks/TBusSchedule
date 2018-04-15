package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.BusScheduleScreen;
import ru.tblsk.owlz.busschedule.di.annotation.WeekendBusSchedule;
import ru.tblsk.owlz.busschedule.di.annotation.WorkdayBusSchedule;
import ru.tblsk.owlz.busschedule.ui.schedules.ScheduleContainerContract;
import ru.tblsk.owlz.busschedule.ui.schedules.ScheduleContainerPresenter;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.ScheduleContract;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.WeekendSchedulePresenter;
import ru.tblsk.owlz.busschedule.ui.schedules.schedule.WorkdaySchedulePresenter;

@Module
public class BusScheduleScreenModule {

    @Provides
    @BusScheduleScreen
    ScheduleContainerContract.Presenter provideScheduleContainerPresenter(
            ScheduleContainerPresenter presenter) {
        return presenter;
    }

    @Provides
    @WorkdayBusSchedule
    @BusScheduleScreen
    ScheduleContract.Presenter provideWorkdaySchedulePresenter(
            WorkdaySchedulePresenter presenter) {
        return presenter;
    }

    @Provides
    @WeekendBusSchedule
    @BusScheduleScreen
    ScheduleContract.Presenter provideWeekendSchedulePresenter(
            WeekendSchedulePresenter presenter) {
        return presenter;
    }

}
