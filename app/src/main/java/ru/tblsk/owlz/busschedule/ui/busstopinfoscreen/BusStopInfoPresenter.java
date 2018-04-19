package ru.tblsk.owlz.busschedule.ui.busstopinfoscreen;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.Direction;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DirectionVO;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class BusStopInfoPresenter extends BasePresenter<BusStopInfoContract.View>
        implements BusStopInfoContract.Presenter{

    private List<DirectionVO> mDirections;

    @Inject
    public BusStopInfoPresenter(DataManager dataManager,
                                CompositeDisposable compositeDisposable,
                                SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void getDirectionsByStop(Long stopId, boolean isFavoriteStop) {
        if(isFavoriteStop) {
            getFavoriteDirection(stopId);
        } else {
            if(mDirections == null) {
                getDirection(stopId);
            } else {
                getMvpView().showDirectionsByStop(mDirections);
            }
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

    private void getDirection(long stopId) {

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
                                        mDirections = directionsVO;
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

    private void getFavoriteDirection(long stopId) {
        getCompositeDisposable().add(getDataManager().getFavoriteDirection(stopId)
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
                                        mDirections = directionsVO;
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
                        throwable.printStackTrace();
                    }
                }));
    }
}
