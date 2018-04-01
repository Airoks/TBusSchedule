package ru.tblsk.owlz.busschedule.ui.routes.suburban;


import java.util.List;

import ru.tblsk.owlz.busschedule.ui.base.MvpView;
import ru.tblsk.owlz.busschedule.ui.viewobject.FlightVO;

public interface SuburbanRoutesMvpView extends MvpView{
    void showSuburbanRoutes(List<FlightVO> flights);
    void showSavedSuburbanRoutes();
    void updateDirectionFromDirectionInfo(List<ChangeDirectionSuburban.InFragment> inFragments);
    void updateDirectionFromAdapter(ChangeDirectionSuburban.InAdapter inAdapter);
    void openDirectionInfoFragment(ChangeDirectionSuburban directionSuburban);
}
