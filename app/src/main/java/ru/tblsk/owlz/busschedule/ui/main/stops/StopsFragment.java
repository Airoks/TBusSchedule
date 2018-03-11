package ru.tblsk.owlz.busschedule.ui.main.stops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.BaseActivity;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;


public class StopsFragment extends BaseFragment implements StopsMvpView {
    @Inject
    StopsMvpPresenter<StopsMvpView> mPresenter;

    @Inject
    StopsAdapter mStopsAdapter;

    @BindView(R.id.stopToolbar)
    Toolbar toolbar;

    @Override
    public void updateStops(List<Stop> stops) {
        mStopsAdapter.addItems(stops);
    }

    @Override
    public void deleteStops() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //зменить лайаут
        View view = inflater.inflate(R.layout.fragment_stop, container, false);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPresenter.getSearchHistoryStops();
        setUnbinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        setupToolbar();
    }

    private void setupToolbar() {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        toolbar.setTitle(R.string.stops);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).openDrawer();
            }
        });

    }

    @OnClick(R.id.deleteButton)
    public void deleteSearchHistoryStops() {

    }

    @OnClick(R.id.allStopsButton)
    public void allStops() {
        //транзакия для запуска фрагмента олстопс
    }
}
