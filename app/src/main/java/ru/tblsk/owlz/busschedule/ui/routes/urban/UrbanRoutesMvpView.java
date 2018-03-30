package ru.tblsk.owlz.busschedule.ui.routes.urban;


import java.util.List;

import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.base.MvpView;

public interface UrbanRoutesMvpView extends MvpView{
    void showUrbanRoutes(List<Flight> flights);
    void changedDirectionInFragment(List<ChangeDirectionUrban.InFragment> directions);
    void changedDirectionInAdapter(ChangeDirectionUrban.InAdapter direction);
    void openDirectionInfoFragment(ChangeDirectionUrban directionUrban);
}
