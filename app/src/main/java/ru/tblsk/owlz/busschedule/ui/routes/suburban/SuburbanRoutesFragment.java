package ru.tblsk.owlz.busschedule.ui.routes.suburban;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.di.annotation.FlightType;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoFragment;
import ru.tblsk.owlz.busschedule.ui.main.MainActivity;
import ru.tblsk.owlz.busschedule.ui.routes.Event;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesAdapter;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class SuburbanRoutesFragment extends BaseFragment
        implements SuburbanRoutesMvpView, Event{

    public static final String TAG = "SuburbanRoutesFragment";
    public static final String FLIGHTS = "flights";
    public static final String DIRECTION_ROUTS = "directionRouts";
    public static final String DIRECT = "direct";
    public static final String REVERSE = "reverse";

    @Inject
    SuburbanRoutesMvpPresenter<SuburbanRoutesMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLinearLayout;

    @Inject
    @FlightType("suburban")
    RoutesAdapter mAdapter;

    @Inject
    CompositeDisposable mCompositeDisposable;

    @Inject
    RxEventBus mEventBus;

    @Inject
    SchedulerProvider mSchedulerProvider;

    @BindView(R.id.suburbanRouteRv)
    RecyclerView mRecyclerView;

    private List<Flight> mFlights;
    private List<String> mDirectionRoutes;
    private List<ChangeDirectionSuburban.InFragment> mChangeDirectionFragment;
    private Map<Integer, String> mChangeDirectionAdapter;

    public static SuburbanRoutesFragment newInstance() {
        Bundle args = new Bundle();
        SuburbanRoutesFragment fragment = new SuburbanRoutesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mChangeDirectionAdapter = new HashMap<>();
        mChangeDirectionFragment = new ArrayList<>();

        if(savedInstanceState != null) {
            mFlights = savedInstanceState.getParcelableArrayList(FLIGHTS);
            mDirectionRoutes = savedInstanceState.getStringArrayList(DIRECTION_ROUTS);
        }

    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void setUp(View view) {
        mLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.setAdapter(mAdapter);

        ((MainActivity)getBaseActivity()).unlockDrawer();
        ((MainActivity)getBaseActivity()).showBottomNavigationView();

        if(mFlights == null) {
            mPresenter.getSuburbanFlights();
        } else {
            showSavedRouts();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suburbanroutes, container, false);
        getBaseActivity().getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);

        if(mFlights == null) {
            subscribeOnEvents();
        }

        setUnbinder(ButterKnife.bind(this, view));
        mPresenter.attachView(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        updateDirectionRouts();
        outState.putParcelableArrayList(FLIGHTS, (ArrayList<? extends Parcelable>) mFlights);
        outState.putStringArrayList(DIRECTION_ROUTS, (ArrayList<String>) mDirectionRoutes);
    }

    @Override
    public void showSuburbanRoutes(List<Flight> flights) {
        mFlights = flights;
        mDirectionRoutes = new ArrayList<>();
        //при первом запуске пользователь видит прямое направление
        for(int i = 0; i < mFlights.size(); i++) {
            mDirectionRoutes.add(DIRECT);
        }
        mAdapter.addItems(flights, mDirectionRoutes);
    }

    @Override
    public void changedDirectionInFragment(ChangeDirectionSuburban.InFragment direction) {
        mChangeDirectionFragment.add(direction);
    }

    @Override
    public void changedDirectionInAdapter(ChangeDirectionSuburban.InAdapter direction) {
        int position = direction.getPosition();
        String directionType = direction.getDirectionType();
        mChangeDirectionAdapter.put(position, directionType);
    }

    @Override
    public void showSavedRouts() {
        updateDirectionRouts();
        mAdapter.addItems(mFlights, mDirectionRoutes);
    }

    @Override
    public void updateDirectionRouts() {
        //приоритет принадлежит изменениям в DirectionInfoFragment
        if(!mChangeDirectionAdapter.isEmpty()) {
            for(Map.Entry entry : mChangeDirectionAdapter.entrySet()) {
                int keyPos = (Integer) entry.getKey();
                String keyDir = entry.getValue().toString();
                mDirectionRoutes.set(keyPos, keyDir);
            }
        }
        if(!mChangeDirectionFragment.isEmpty()) {
            int position = mChangeDirectionFragment.size() - 1;
            mDirectionRoutes.set(mChangeDirectionFragment.get(position).getPosition(),
                    mChangeDirectionFragment.get(position).getDirectionType());
        }

        mChangeDirectionFragment.clear();
        mChangeDirectionAdapter.clear();
    }

    @Override
    public void subscribeOnEvents() {
        mCompositeDisposable.add(mEventBus.filteredObservable(ChangeDirectionSuburban.InFragment.class)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<ChangeDirectionSuburban.InFragment>() {
                    @Override
                    public void accept(ChangeDirectionSuburban.InFragment inFragment) throws Exception {
                        changedDirectionInFragment(inFragment);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
        mCompositeDisposable.add(mEventBus.filteredObservable(ChangeDirectionSuburban.InAdapter.class)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<ChangeDirectionSuburban.InAdapter>() {
                    @Override
                    public void accept(ChangeDirectionSuburban.InAdapter inAdapter) throws Exception {
                        changedDirectionInAdapter(inAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));

        mCompositeDisposable.add(mEventBus.filteredObservable(ChangeDirectionSuburban.class)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<ChangeDirectionSuburban>() {
                    @Override
                    public void accept(ChangeDirectionSuburban directionSuburban) throws Exception {
                        FragmentManager fragmentManager = getBaseActivity()
                                .getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.container,
                                DirectionInfoFragment.newInstance(
                                        directionSuburban.getDirection().getId()));
                        transaction.addToBackStack(DirectionInfoFragment.TAG);
                        transaction.commit();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }));
    }
}
