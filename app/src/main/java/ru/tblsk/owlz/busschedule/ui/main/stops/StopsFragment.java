package ru.tblsk.owlz.busschedule.ui.main.stops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;



public class StopsFragment extends BaseFragment implements StopsMvpView {
    @Inject
    StopsMvpPresenter<StopsMvpView> mPresenter;

    @Override
    public void updateStops(List<Stop> stops) {
        //Test
        for(Stop stop : stops) {
            String stopName = stop.getStopName();
            System.out.println(stopName);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.getStops();
        return null;
    }
}
