package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import ru.tblsk.owlz.busschedule.di.annotation.DirectionInfoScreen;
import ru.tblsk.owlz.busschedule.ui.directioninfoscreen.DirectionInfoContract;
import ru.tblsk.owlz.busschedule.ui.directioninfoscreen.DirectionInfoPresenter;

@Module
public class DirectionInfoScreenModule {

    @Provides
    @DirectionInfoScreen
    DirectionInfoContract.Presenter provideDirectionInfoPresenter(
            DirectionInfoPresenter presenter) {
        return presenter;
    }

}
