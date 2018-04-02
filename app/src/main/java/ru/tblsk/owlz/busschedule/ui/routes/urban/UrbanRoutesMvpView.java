package ru.tblsk.owlz.busschedule.ui.routes.urban;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.viewobject.FlightVO;

public interface UrbanRoutesMvpView extends MvpView{
    void showUrbanRoutes(List<FlightVO> flights);
    void showSavedUrbanRoutes();
    void updateDirectionFromDirectionInfo(ChangeDirectionUrban.InFragment inFragment);
    void updateDirectionFromAdapter(ChangeDirectionUrban.InAdapter inAdapter);
    void openDirectionInfoFragment(ChangeDirectionUrban directionUrban);
}
