package ru.tblsk.owlz.busschedule.ui.routes.suburban;


import java.util.List;

import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface SuburbanRoutesMvpView extends MvpView{
    void showSuburbanRoutes(List<Flight> flights);
    void changedDirectionInFragment(ChangeDirectionSuburban.InFragment direction);
    void changedDirectionInAdapter(ChangeDirectionSuburban.InAdapter direction);
    void openDirectionInfoFragment(ChangeDirectionSuburban directionSuburban);
}
