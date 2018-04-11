package ru.tblsk.owlz.busschedule.ui.directioninfo;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.StopMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.StopVO;
import ru.tblsk.owlz.busschedule.ui.routes.suburban.ChangeDirectionSuburban;
import ru.tblsk.owlz.busschedule.ui.routes.urban.ChangeDirectionUrban;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class DirectionInfoPresenter<V extends DirectionInfoMvpView> extends BasePresenter<V>
        implements DirectionInfoMvpPresenter<V>{

    private static final int DIRECT = 0;
    private static final int REVERSE = 1;
    private static final int URBAN = 0;
    private static final int SUBURBAN = 1;

    private RxEventBus mEventBus;
    private StopMapper mStopMapper;
    private FlightVO mFlight;
    private List<StopVO> mStops;

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
    public void getStopsOnDirection() {
        getMvpView().setFlightNumber(mFlight.getFlightNumber());
        if(mStops != null) {
            getMvpView().showStopsOnDirection(mStops);
            getMvpView().setDirectionTitle(mFlight.getCurrentDirection().getDirectionName());
        } else {
           updateStops();
        }
    }

    @Override
    public void clickedOnChangeDirectionButton() {
        int position = mFlight.getPosition();
        if(mFlight.getFlightType() == URBAN) {
            if(mFlight.getCurrentDirectionType() == DIRECT) {
                mFlight.setCurrentDirectionType(REVERSE);

                ChangeDirectionUrban.InFragment inFragment =
                        new ChangeDirectionUrban.InFragment(position, REVERSE);
                mEventBus.post(inFragment);
            } else {
                mFlight.setCurrentDirectionType(DIRECT);

                ChangeDirectionUrban.InFragment inFragment =
                        new ChangeDirectionUrban.InFragment(position, DIRECT);
                mEventBus.post(inFragment);
            }
        }
        if(mFlight.getFlightType() == SUBURBAN) {
            if(mFlight.getCurrentDirectionType() == DIRECT) {
                mFlight.setCurrentDirectionType(REVERSE);

                ChangeDirectionSuburban.InFragment inFragment =
                        new ChangeDirectionSuburban.InFragment(position, REVERSE);
                mEventBus.post(inFragment);
            } else {
                mFlight.setCurrentDirectionType(DIRECT);

                ChangeDirectionSuburban.InFragment inFragment =
                        new ChangeDirectionSuburban.InFragment(position, DIRECT);
                mEventBus.post(inFragment);
            }
        }
        updateStops();
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }

    @Override
    public void clearData() {
        mFlight = null;
        mStops = null;
    }

    @Override
    public void setData(FlightVO flight) {
        if(mFlight == null) {
            mFlight = flight;
        }
    }

    @Override
    public void setChangeButton() {
        boolean flag = mFlight.getDirections().size() > 1;
        getMvpView().showChangeButton(flag);
    }

    private void updateStops() {
        long directionId = mFlight.getCurrentDirection().getId();
        getCompositeDisposable().add(getDataManager().getStopsOnDirection(directionId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .map(mStopMapper)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<StopVO>>() {
                    @Override
                    public void accept(List<StopVO> stops) throws Exception {
                        mStops = stops;
                        getMvpView().showStopsOnDirection(stops);
                        getMvpView().setDirectionTitle(mFlight.getCurrentDirection().getDirectionName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }
}
