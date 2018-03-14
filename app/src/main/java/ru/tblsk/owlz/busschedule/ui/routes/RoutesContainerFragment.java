package ru.tblsk.owlz.busschedule.ui.routes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.base.SetupToolbar;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;

public class RoutesContainerFragment extends BaseFragment implements SetupToolbar{

    @Inject
    RoutesContainerMvpPresenter<RoutesContainerMvpView> mPresenter;

    @BindView(R.id.routesContainerToolbar)
    Toolbar mToolbar;

    public static RoutesContainerFragment newInstance() {
        Bundle args = new Bundle();
        RoutesContainerFragment fragment = new RoutesContainerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes_container, container, false);
        setUnbinder(ButterKnife.bind(this, view));
        getActivityComponent().inject(this);

        return view;
    }

    @Override
    protected void setUp(View view) {
        setupToolbar();
    }

    @Override
    public void setupToolbar() {
        getBaseActivity().setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        mToolbar.setTitle(R.string.routs);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getBaseActivity()).openDrawer();
            }
        });
    }
}
