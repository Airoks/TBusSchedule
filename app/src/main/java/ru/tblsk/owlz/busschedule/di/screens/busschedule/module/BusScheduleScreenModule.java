package ru.tblsk.owlz.busschedule.di.screens.busschedule.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.BusScheduleScreen;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.WeekendBusSchedule;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.WorkdayBusSchedule;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.BusScheduleContainerContract;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.BusScheduleContainerPresenter;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule.BusScheduleContract;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule.WeekendSchedulePresenter;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule.WorkdaySchedulePresenter;

@Module
public class BusScheduleScreenModule {

    @Provides
    @BusScheduleScreen
    BusScheduleContainerContract.Presenter provideScheduleContainerPresenter(
            BusScheduleContainerPresenter presenter) {
        return presenter;
    }

    @Provides
    @WorkdayBusSchedule
    @BusScheduleScreen
    BusScheduleContract.Presenter provideWorkdaySchedulePresenter(
            WorkdaySchedulePresenter presenter) {
        return presenter;
    }

    @Provides
    @WeekendBusSchedule
    @BusScheduleScreen
    BusScheduleContract.Presenter provideWeekendSchedulePresenter(
            WeekendSchedulePresenter presenter) {
        return presenter;
    }

}
