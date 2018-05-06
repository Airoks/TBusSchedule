package ru.tblsk.owlz.busschedule.ui.busstopinfoscreen;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.BusStopInfoScreen;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.NextFlight;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DepartureTimeVO;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

@BusStopInfoScreen
public class BusStopInfoPresenter extends BasePresenter<BusStopInfoContract.View>
        implements BusStopInfoContract.Presenter {

    private static final int WORKDAY = 0;
    private static final int WEEKEND = 1;

    private DepartureTimeMapper mTimeMapper;
    private List<DirectionVO> mDirections;
    private List<DepartureTimeVO> mSchedule;
    private List<NextFlight> mNextFlights;
    private RxEventBus mEventBus;
    private Disposable mTimerDisposable;
    private boolean mFirstStart;

    @Inject
    public BusStopInfoPresenter(DataManager dataManager,
                                CompositeDisposable compositeDisposable,
                                SchedulerProvider schedulerProvider,
                                RxEventBus eventBus,
                                DepartureTimeMapper departureTimeMapper) {
        super(dataManager, compositeDisposable, schedulerProvider);

        mDirections = new ArrayList<>();
        mSchedule = new ArrayList<>();
        mNextFlights = new ArrayList<>();
        mTimeMapper = departureTimeMapper;
        mEventBus = eventBus;
        mFirstStart = true;
    }

    @Override
    public void getDirectionsByStop(Long stopId) {
        if(mDirections.isEmpty()) {
            getDirection(stopId);
        } else {
            getMvpView().showDirectionsByStop(mDirections);
        }
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }

    @Override
    public void clickedOnButtonAddFavorites() {
        getMvpView().openFavoritesDirectionsDialog(mDirections);
    }

    @Override
    public void isFavoriteStop(Long stopId) {
        getCompositeDisposable().add(getDataManager().isFavoriteStop(stopId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isFavorite) throws Exception {
                        getMvpView().setFavoriteIcon(isFavorite);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    @Override
    public void deleteFavoriteStop(Long stopId) {
        getCompositeDisposable().add(getDataManager().deleteFavoriteStop(stopId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().showSnackBarDeleted();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    @Override
    public void clickedOnAdapterItem(long directionId, final int directionType) {
        getCompositeDisposable().add(getDataManager().getFlightByDirection(directionId)
                .subscribeOn(getSchedulerProvider().io())
                .map(new Function<Flight, FlightVO>() {
                    @Override
                    public FlightVO apply(Flight flight) throws Exception {
                        FlightVO flightVO = new FlightVO();
                        flightVO.setId(flight.getId());
                        flightVO.setFlightNumber(flight.getFlightNumber());
                        flightVO.setFlightType(flight.getFlightType().id);
                        flightVO.setDirections(flight.getDirections());
                        flightVO.setPosition(0);
                        flightVO.setCurrentDirectionType(directionType);
                        return flightVO;
                    }
                })
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<FlightVO>() {
                    @Override
                    public void accept(FlightVO flightVO) throws Exception {
                        getMvpView().openScheduleContainerFragment(flightVO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    @Override
    public void setClickListenerOnAddButtonInDialog() {
        getCompositeDisposable().add(mEventBus.filteredObservable(DirectionAdditionEvent.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<DirectionAdditionEvent>() {
                    @Override
                    public void accept(DirectionAdditionEvent event) throws Exception {
                        boolean isAdded = event.isAdded();
                        getMvpView().setFavoriteIcon(isAdded);
                        if(isAdded) {
                            getMvpView().showSnackBarAdded(false);
                        } else {
                            getMvpView().showSnackBarNotSelected();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }

    private void getDirection(final long stopId) {

        getCompositeDisposable().add(getDataManager().getDirectionsByStop(stopId)
                .subscribeOn(getSchedulerProvider().io())
                .flatMap(new Function<List<Direction>, SingleSource<List<DirectionVO>>>() {
                    @Override
                    public SingleSource<List<DirectionVO>> apply(final List<Direction> directions) throws Exception {
                        return getDataManager().getFlightNumbers(directions)
                                .map(new Function<List<String>, List<DirectionVO>>() {
                                    @Override
                                    public List<DirectionVO> apply(List<String> flightNumber) throws Exception {
                                        List<DirectionVO> directionsVO = new ArrayList<>();
                                        for(int i = 0; i < directions.size(); i ++) {
                                            DirectionVO directionVO = new DirectionVO();
                                            directionVO.setId(directions.get(i).getId());
                                            directionVO.setDirectionName(directions.get(i).getDirectionName());
                                            directionVO.setDirectionType(directions.get(i).getDirectionType().id);
                                            directionVO.setFlightId(directions.get(i).getFlightId());
                                            directionVO.setFlightNumber(flightNumber.get(i));
                                            directionVO.setFavorite(true);
                                            directionsVO.add(directionVO);
                                        }
                                        return directionsVO;
                                    }
                                });
                    }
                })
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<DirectionVO>>() {
                    @Override
                    public void accept(List<DirectionVO> directionVOS) throws Exception {
                        mDirections.clear();
                        mDirections.addAll(directionVOS);
                        getMvpView().showDirectionsByStop(directionVOS);
                        getSchedule(stopId);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));

    }

    @Override
    public void getSchedule(long stopId) {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if(day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
            getScheduleByType(stopId, WEEKEND);
        } else {
            getScheduleByType(stopId, WORKDAY);
        }
    }

    @Override
    public void getScheduleByType(long stopId, int scheduleType) {
        mSchedule.clear();
        mNextFlights.clear();
        getScheduleByStop(stopId, scheduleType);
    }

    private void getScheduleByStop(long stopId, int scheduleType) {
        getCompositeDisposable().add(getDataManager().getScheduleByStop(stopId, scheduleType)
                .subscribeOn(getSchedulerProvider().io())
                .map(mTimeMapper)
                .observeOn(getSchedulerProvider().ui())
                .subscribeWith(new DisposableObserver<DepartureTimeVO>() {
                    @Override
                    public void onNext(DepartureTimeVO time) {
                        mSchedule.add(time);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        setTimer();
                    }
                }));
    }

    @Override
    public void setTimer() {
        cancelTimer();
        mNextFlights.clear();
        for(int i = 0; i < mDirections.size(); i ++) {
            getNextFlight(i, false);
        }
        getMvpView().showTimeOfNextFlight(mNextFlights);
        mFirstStart = false;

        mTimerDisposable = Observable.interval(1, TimeUnit.MINUTES)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        for(int i = 0; i < mNextFlights.size(); i ++) {
                            if(mNextFlights.get(i).isInitialized()) {
                                int timeBefore = mNextFlights.get(i).getTimeBeforeDeparture() - 1;
                                NextFlight next = newNextFlight(mNextFlights.get(i));
                                mNextFlights.set(i, next);
                                if(timeBefore < 0) {
                                    getNextFlight(i, true);
                                }
                            }
                        }
                        getMvpView().showTimeOfNextFlight(mNextFlights);
                    }
                });
    }

    @Override
    public NextFlight newNextFlight(NextFlight old) {
        NextFlight next = new NextFlight();
        next.setHour(old.getHour());
        next.setMinute(old.getMinute());
        next.setTimeBeforeDeparture(old.getTimeBeforeDeparture() - 1);
        next.setInitialized(true);
        return next;
    }

    @Override
    public void getNextFlight(int position, boolean set) {
        DepartureTimeVO schedule = mSchedule.get(position);
        NextFlight nextFlight = new NextFlight();
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        for(int hour : schedule.getHours()) {
            if(currentHour == hour) {
                for(int minute : schedule.getTime().get(hour)) {
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    if(currentMinute <= minute) {
                        nextFlight.setHour(hour);
                        nextFlight.setMinute(minute);
                        int timeBefore = minute - currentMinute;
                        nextFlight.setTimeBeforeDeparture(timeBefore);
                        nextFlight.setInitialized(true);
                        if(set) {
                            mNextFlights.set(position, nextFlight);
                        } else {
                            mNextFlights.add(nextFlight);
                        }
                        return;
                    }
                }
            } else if(currentHour < hour) {
                int currentMinute = calendar.get(Calendar.MINUTE);
                int minute = schedule.getTime().get(hour).get(0);

                nextFlight.setHour(hour);
                nextFlight.setMinute(minute);
                int timeBefore = (hour - currentHour)* 60 + (minute - currentMinute);
                nextFlight.setTimeBeforeDeparture(timeBefore);
                nextFlight.setInitialized(true);
                if(set) {
                    mNextFlights.set(position, nextFlight);
                } else {
                    mNextFlights.add(nextFlight);
                }
                return;
            }
        }

        if(set) {
            mNextFlights.set(position, nextFlight);
        } else {
            mNextFlights.add(nextFlight);
        }
    }

    @Override
    public void cancelTimer() {
        if(mTimerDisposable != null && !mTimerDisposable.isDisposed()) {
            mTimerDisposable.dispose();
        }
    }

    @Override
    public void startTimer() {
        if(!mFirstStart) {
            setTimer();
        }
    }

}
