package ru.tblsk.owlz.busschedule.ui.directioninfo;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.DirectionType;
import ru.tblsk.owlz.busschedule.data.db.model.FlightType;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.routes.suburban.ChangeDirectionSuburban;
import ru.tblsk.owlz.busschedule.ui.routes.urban.ChangeDirectionUrban;
import ru.tblsk.owlz.busschedule.ui.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.ui.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class DirectionInfoPresenter<V extends DirectionInfoMvpView> extends BasePresenter<V>
        implements DirectionInfoMvpPresenter<V>{

    private static final int DIRECT = DirectionType.DIRECT.id;
    private static final int REVERSE = DirectionType.REVERSE.id;

    private RxEventBus mEventBus;
    private StopMapper mStopMapper;

    @Inject
    public DirectionInfoPresenter(DataManager dataManager,
                                  CompositeDisposable compositeDisposable,
                                  SchedulerProvider schedulerProvider,
                                  RxEventBus eventBus,
                                  StopMapper stopMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mEventBus = eventBus;
        this.mStopMapper = stopMapper;
    }

    @Override
    public void getStopsOnDirection(Long directionId) {
        getCompositeDisposable().add(getDataManager().getStopsOnDirection(directionId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .map(mStopMapper)
                .subscribe(new Consumer<List<StopVO>>() {
                    @Override
                    public void accept(List<StopVO> stops) throws Exception {
                        getMvpView().showStopsOnDirection(stops);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void getSavedStopsOnDirection() {
        getMvpView().showSavedStopsOnDirection();
    }

    @Override
    public void clickedOnChangeDirectionButton(FlightVO flight) {
        int position = flight.getPosition();
        if(flight.getFlightType() == FlightType.URBAN.id) {
            if(flight.getCurrentDirectionType() == DIRECT) {
                flight.setCurrentDirectionType(REVERSE);

                ChangeDirectionUrban.InFragment inFragment =
                        new ChangeDirectionUrban.InFragment(position, REVERSE);
                mEventBus.post(inFragment);
            } else {
                flight.setCurrentDirectionType(DIRECT);

                ChangeDirectionUrban.InFragment inFragment =
                        new ChangeDirectionUrban.InFragment(position, DIRECT);
                mEventBus.post(inFragment);
            }
        }
        if(flight.getFlightType() == FlightType.SUBURBAN.id) {
            if(flight.getCurrentDirectionType() == DIRECT) {
                flight.setCurrentDirectionType(REVERSE);

                ChangeDirectionSuburban.InFragment inFragment =
                        new ChangeDirectionSuburban.InFragment(position, REVERSE);
                mEventBus.post(inFragment);
            } else {
                flight.setCurrentDirectionType(DIRECT);

                ChangeDirectionSuburban.InFragment inFragment =
                        new ChangeDirectionSuburban.InFragment(position, DIRECT);
                mEventBus.post(inFragment);
            }
        }

        getMvpView().updateFlight(flight);
        this.getStopsOnDirection(flight.getCurrentDirection().getId());
        getMvpView().setDirectionTitle();
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }
}
