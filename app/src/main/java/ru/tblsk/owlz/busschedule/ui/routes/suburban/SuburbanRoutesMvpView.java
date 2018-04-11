package ru.tblsk.owlz.busschedule.ui.routes.suburban;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;

public interface SuburbanRoutesMvpView extends MvpView{
    void showSuburbanRoutes(List<FlightVO> flights);
    void showSavedSuburbanRoutes();
    void changeDirection(int position, int directionType);
    void openDirectionInfoFragment(int position);
}
