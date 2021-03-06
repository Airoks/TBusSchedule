package ru.tblsk.owlz.busschedule.utils;


import android.support.v4.util.LongSparseArray;

import ru.tblsk.owlz.busschedule.di.screens.allbusstops.component.AllBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.application.component.ApplicationComponent;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.component.BusRoutesScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.component.BusScheduleScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.component.BusStopInfoScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.allbusstops.component.DaggerAllBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.component.DaggerBusRoutesScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.component.DaggerBusScheduleScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.component.DaggerBusStopInfoScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.directioninfo.component.DaggerDirectionInfoScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.favoritebusstops.component.DaggerFavoriteBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.viewedbusstops.component.DaggerViewedBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.directioninfo.component.DirectionInfoScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.favoritebusstops.component.FavoriteBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.viewedbusstops.component.ViewedBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.screens.allbusstops.module.AllBusStopsScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.busroutes.module.BusRoutesScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.busschedule.module.BusScheduleScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.busstopinfo.module.BusStopInfoScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.directioninfo.module.DirectionInfoScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.favoritebusstops.module.FavoriteBusStopsScreenModule;
import ru.tblsk.owlz.busschedule.di.screens.viewedbusstops.module.ViewedBusStopsScreenModule;

public class ComponentManager {

    private ApplicationComponent mAppComponent;

    private final LongSparseArray<FavoriteBusStopsScreenComponent> mFavoriteBusStopsScreenComponents;
    private final LongSparseArray<ViewedBusStopsScreenComponent> mViewedBusStopsScreenComponents;
    private final LongSparseArray<BusRoutesScreenComponent> mBusRoutesScreenComponents;
    private final LongSparseArray<AllBusStopsScreenComponent> mAllBusStopsScreenComponents;
    private final LongSparseArray<BusStopInfoScreenComponent> mBusStopInfoScreenComponents;
    private final LongSparseArray<BusScheduleScreenComponent> mBusScheduleScreenComponents;
    private final LongSparseArray<DirectionInfoScreenComponent> mDirectionInfoScreenComponents;

    public ComponentManager(ApplicationComponent component) {
        this.mAppComponent = component;
        this.mFavoriteBusStopsScreenComponents = new LongSparseArray<>();
        this.mViewedBusStopsScreenComponents = new LongSparseArray<>();
        this.mBusRoutesScreenComponents = new LongSparseArray<>();
        this.mAllBusStopsScreenComponents = new LongSparseArray<>();
        this.mBusStopInfoScreenComponents = new LongSparseArray<>();
        this.mBusScheduleScreenComponents = new LongSparseArray<>();
        this.mDirectionInfoScreenComponents = new LongSparseArray<>();
    }

    public FavoriteBusStopsScreenComponent getNewFavoriteBusStopsScreenComponent() {
        return DaggerFavoriteBusStopsScreenComponent.builder()
                .favoriteBusStopsScreenModule(new FavoriteBusStopsScreenModule())
                .applicationComponent(mAppComponent)
                .build();
    }

    public ViewedBusStopsScreenComponent getNewViewedBusStopsScreenComponent() {
        return DaggerViewedBusStopsScreenComponent.builder()
                .viewedBusStopsScreenModule(new ViewedBusStopsScreenModule())
                .applicationComponent(mAppComponent)
                .build();
    }

    public BusRoutesScreenComponent getNewBusRoutesScreenComponent() {
        return DaggerBusRoutesScreenComponent.builder()
                .busRoutesScreenModule(new BusRoutesScreenModule())
                .applicationComponent(mAppComponent)
                .build();
    }

    public AllBusStopsScreenComponent getNewAllBusStopsScreenComponent() {
        return DaggerAllBusStopsScreenComponent.builder()
                .allBusStopsScreenModule(new AllBusStopsScreenModule())
                .applicationComponent(mAppComponent)
                .build();
    }

    public BusStopInfoScreenComponent getNewBusStopInfoScreenComponent() {
        return DaggerBusStopInfoScreenComponent.builder()
                .busStopInfoScreenModule(new BusStopInfoScreenModule())
                .applicationComponent(mAppComponent)
                .build();
    }

    public BusScheduleScreenComponent getNewBusScheduleScreenComponent() {
        return DaggerBusScheduleScreenComponent.builder()
                .busScheduleScreenModule(new BusScheduleScreenModule())
                .applicationComponent(mAppComponent)
                .build();
    }

    public DirectionInfoScreenComponent getNewDirectionInfoScreenComponent() {
        return DaggerDirectionInfoScreenComponent.builder()
                .directionInfoScreenModule(new DirectionInfoScreenModule())
                .applicationComponent(mAppComponent)
                .build();
    }

    public void putFavoriteBusStopsScreenComponent(long fragmentId,
                                                   FavoriteBusStopsScreenComponent component) {
        mFavoriteBusStopsScreenComponents.put(fragmentId, component);
        mViewedBusStopsScreenComponents.clear();
        mBusRoutesScreenComponents.clear();
    }

    public void putViewedBusStopsScreenComponent(long fragmentId,
                                                 ViewedBusStopsScreenComponent component) {
        mViewedBusStopsScreenComponents.put(fragmentId, component);
        mFavoriteBusStopsScreenComponents.clear();
        mBusRoutesScreenComponents.clear();
    }

    public void putBusRoutesScreenComponent(long fragmentId,
                                            BusRoutesScreenComponent component) {
        mBusRoutesScreenComponents.put(fragmentId, component);
        mFavoriteBusStopsScreenComponents.clear();
        mViewedBusStopsScreenComponents.clear();
    }

    public void putAllBusStopsScreenComponent(long fragmentId,
                                              AllBusStopsScreenComponent component) {
        mAllBusStopsScreenComponents.put(fragmentId, component);
    }

    public void putBusStopInfoScreenComponent(long fragmentId,
                                              BusStopInfoScreenComponent component) {
        mBusStopInfoScreenComponents.put(fragmentId, component);
    }

    public void putBusScheduleScreenComponent(long fragmentId,
                                              BusScheduleScreenComponent component) {
        mBusScheduleScreenComponents.put(fragmentId, component);
    }

    public void putDirectionInfoScreenComponent(long fragmentId,
                                                DirectionInfoScreenComponent component) {
        mDirectionInfoScreenComponents.put(fragmentId, component);
    }

    public FavoriteBusStopsScreenComponent getFavoriteBusStopsScreenComponent(long fragmentId) {
        return mFavoriteBusStopsScreenComponents.get(fragmentId);
    }

    public ViewedBusStopsScreenComponent getViewedBusStopsScreenComponent(long fragmentId) {
        return mViewedBusStopsScreenComponents.get(fragmentId);
    }

    public BusRoutesScreenComponent getBusRoutesScreenComponent(long fragmentId) {
        return mBusRoutesScreenComponents.get(fragmentId);
    }

    public AllBusStopsScreenComponent getAllBusStopsScreenComponent(long fragmentId) {
        return mAllBusStopsScreenComponents.get(fragmentId);
    }

    public BusStopInfoScreenComponent getBusStopInfoScreenComponent(long fragmentId) {
        return mBusStopInfoScreenComponents.get(fragmentId);
    }

    public BusScheduleScreenComponent getBusScheduleScreenComponent(long fragmentId) {
        return mBusScheduleScreenComponents.get(fragmentId);
    }

    public DirectionInfoScreenComponent getDirectionInfoScreenComponent(long fragmentId) {
        return mDirectionInfoScreenComponents.get(fragmentId);
    }

    public void removeAllBusStopsScreenComponent(long fragmentId) {
        mAllBusStopsScreenComponents.remove(fragmentId);
    }

    public void removeBusStopInfoScreenComponent(long fragmentId) {
        mBusStopInfoScreenComponents.remove(fragmentId);
    }

    public void removeBusScheduleScreenComponent(long fragmentId) {
        mBusScheduleScreenComponents.remove(fragmentId);
    }

    public void removeDirectionInfoScreenComponent(long fragmentId) {
        mDirectionInfoScreenComponents.remove(fragmentId);
    }
}
