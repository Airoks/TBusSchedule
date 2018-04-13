package ru.tblsk.owlz.busschedule.ui.stopinfo;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.ui.mappers.DirectionMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class StopInfoPresenter<V extends StopInfoMvpView> extends BasePresenter<V>
        implements StopInfoMvpPresenter<V>{

    private DirectionMapper mDirectionMapper;
    private FlightMapper mFlightMapper;
    private RxEventBus mEventBus;
    private Disposable mDisposable;

    @Inject
    public StopInfoPresenter(DataManager dataManager,
                             CompositeDisposable compositeDisposable,
                             SchedulerProvider schedulerProvider,
                             DirectionMapper directionMapper,
                             FlightMapper flightMapper,
                             RxEventBus eventBus) {
        super(dataManager, compositeDisposable, schedulerProvider);

        this.mDirectionMapper = directionMapper;
        this.mFlightMapper = flightMapper;
        this.mEventBus = eventBus;
    }

    @Override
    public void getDirectionsByStop(Long stopId) {
        getCompositeDisposable().add(getDataManager().getDirectionsByStop(stopId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
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
                        getMvpView().showDirectionsByStop(directionVOS);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));

    }

    @Override
    public void getSavedDirectionsByStop() {
        getMvpView().showSavedDirectionsByStop();
    }

    @Override
    public void clickedOnBackButton() {
        getMvpView().openPreviousFragment();
    }

    @Override
    public void clickedOnButtonAddFavorites() {
        getMvpView().openFavoritesDirectionsDialog();
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
    public void setClickListenerForAdapter() {
        if(mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }

        mDisposable = mEventBus.filteredObservable(StopInfoEvent.class)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .flatMapSingle(new Function<StopInfoEvent, SingleSource<FlightVO>>() {
                    @Override
                    public SingleSource<FlightVO> apply(final StopInfoEvent stopInfoEvent) throws Exception {
                        long directionId = stopInfoEvent.getDirectionId();
                        return getDataManager().getFlightByDirection(directionId)
                                .map(new Function<Flight, FlightVO>() {
                                    @Override
                                    public FlightVO apply(Flight flight) throws Exception {
                                        FlightVO flightVO = new FlightVO();
                                        flightVO.setId(flight.getId());
                                        flightVO.setFlightNumber(flight.getFlightNumber());
                                        flightVO.setFlightType(flight.getFlightType().id);
                                        flightVO.setDirections(flight.getDirections());
                                        flightVO.setPosition(0);
                                        flightVO.setCurrentDirectionType(stopInfoEvent.getDirectionType());
                                        return flightVO;
                                    }
                                });
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
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void detachView() {
        if(mDisposable != null) {
            mDisposable.dispose();
        }
        super.detachView();
    }
}
