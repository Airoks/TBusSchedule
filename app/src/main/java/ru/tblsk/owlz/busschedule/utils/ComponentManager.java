package ru.tblsk.owlz.busschedule.utils;


import android.support.v4.util.LongSparseArray;

import ru.tblsk.owlz.busschedule.di.component.AllBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.BusRoutesScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.BusScheduleScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.BusStopInfoScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.DirectionInfoScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.FavoriteBusStopsScreenComponent;
import ru.tblsk.owlz.busschedule.di.component.ViewedBusStopsScreenComponent;

public class ComponentManager {

    private final LongSparseArray<FavoriteBusStopsScreenComponent> mFavoriteBusStopsScreenComponents;
    private final LongSparseArray<ViewedBusStopsScreenComponent> mViewedBusStopsScreenComponents;
    private final LongSparseArray<BusRoutesScreenComponent> mBusRoutesScreenComponents;
    private final LongSparseArray<AllBusStopsScreenComponent> mAllBusStopsScreenComponents;
    private final LongSparseArray<BusStopInfoScreenComponent> mBusStopInfoScreenComponents;
    private final LongSparseArray<BusScheduleScreenComponent> mBusScheduleScreenComponents;
    private final LongSparseArray<DirectionInfoScreenComponent> mDirectionInfoScreenComponents;

    public ComponentManager() {
        this.mFavoriteBusStopsScreenComponents = new LongSparseArray<>();
        this.mViewedBusStopsScreenComponents = new LongSparseArray<>();
        this.mBusRoutesScreenComponents = new LongSparseArray<>();
        this.mAllBusStopsScreenComponents = new LongSparseArray<>();
        this.mBusStopInfoScreenComponents = new LongSparseArray<>();
        this.mBusScheduleScreenComponents = new LongSparseArray<>();
        this.mDirectionInfoScreenComponents = new LongSparseArray<>();
    }
}
