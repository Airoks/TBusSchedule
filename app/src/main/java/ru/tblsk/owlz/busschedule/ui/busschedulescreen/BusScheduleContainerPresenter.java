package ru.tblsk.owlz.busschedule.ui.busschedulescreen;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class BusScheduleContainerPresenter extends BasePresenter<BusScheduleContainerContract.View>
        implements BusScheduleContainerContract.Presenter{

    private static final String BUSSTOPINFO_FRAGMENT_TAG = "BusStopInfoFragment";
    private static final String DIRECTIONINFO_FRAGMENT_TAG = "DirectionInfoFragment";

    @Inject
    public BusScheduleContainerPresenter(DataManager dataManager,
                                         CompositeDisposable compositeDisposable,
                                         SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }

    @Override
    public void clickedOnDirection(String currentTopTag) {
        if(currentTopTag.equals(BUSSTOPINFO_FRAGMENT_TAG)) {
            getMvpView().openPreviousFragment();
        } else if(currentTopTag.equals(DIRECTIONINFO_FRAGMENT_TAG)) {
            getMvpView().openStopInfoFragment();
        }
    }

    @Override
    public void clickedOnBusStop(String currentTopTag) {
        if(currentTopTag.equals(BUSSTOPINFO_FRAGMENT_TAG)) {
            getMvpView().openDirectionInfoFragment();
        } else if(currentTopTag.equals(DIRECTIONINFO_FRAGMENT_TAG)) {
            getMvpView().openPreviousFragment();
        }
    }
}
