package ru.tblsk.owlz.busschedule.ui.routes.urban;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.mappers.viewobject.FlightVO;

public interface UrbanRoutesMvpView extends MvpView{
    void showUrbanRoutes(List<FlightVO> flights);
    void showSavedUrbanRoutes();
    void openDirectionInfoFragment(int position);
    void changeDirection(int position, int directionType);
}
