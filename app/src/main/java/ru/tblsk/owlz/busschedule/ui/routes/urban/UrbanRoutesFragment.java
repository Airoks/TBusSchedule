package ru.tblsk.owlz.busschedule.ui.routes.urban;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.directioninfo.DirectionInfoFragment;
import ru.tblsk.owlz.busschedule.ui.routes.RoutesContainerFragment;
import ru.tblsk.owlz.busschedule.ui.stops.allstops.AllStopsFragment;

public class UrbanRoutesFragment extends BaseFragment
        implements UrbanRoutesMvpView{

    public static final String TAG = "UrbanRoutesFragment";

    @Inject
    UrbanRoutesMvpPresenter<UrbanRoutesMvpView> mPresenter;

    @Inject
    LinearLayoutManager mLinearLayout;

    @Inject
    UrbanRoutesAdapter mUrbanRoutesAdapter;

    @BindView(R.id.urbanRouteRv)
    RecyclerView mRecyclerView;

    private List<ChangeDirectionUrban> changeDirection = new ArrayList<>();

    public static UrbanRoutesFragment newInstance() {
        Bundle args = new Bundle();
        UrbanRoutesFragment fragment = new UrbanRoutesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        mUrbanRoutesAdapter.setSwapButton(new UrbanRoutesAdapter.UrbanRoutesListener() {
            @Override
            public void swapButtonOnClick(View view, int position) {
                Log.d("Button swap", "CLICK!!!!!!!");
            }
        });

        mLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.setAdapter(mUrbanRoutesAdapter);
        //mPresenter.getUrbanFlights();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getBaseActivity(),
                mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container, DirectionInfoFragment.newInstance());
                fragmentTransaction.hide(getParentFragment());
                fragmentTransaction.addToBackStack(DirectionInfoFragment.TAG);
                fragmentTransaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_urban_routes, container, false);
        getBaseActivity().getActivityComponent()
                .fragmentComponent(new FragmentModule(this)).inject(this);
        setUnbinder(ButterKnife.bind(this, view));
        mPresenter.attachView(this);
            mPresenter.getUrbanFlights();
        return view;
    }

    @Override
    public void showUrbanRoutes(List<Flight> flights) {
        mUrbanRoutesAdapter.addItem(flights);
    }

    @Override
    public void changedDirectionInFragment(ChangeDirectionUrban.InFragment direction) {

    }

    @Override
    public void changedDirectionInAdapter(ChangeDirectionUrban.InAdapter direction) {

    }

    public static interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private UrbanRoutesFragment.ClickListener mClickListener;
        private GestureDetector mGestureDetector;

        public RecyclerTouchListener(Context context,
                                     final RecyclerView recyclerView,
                                     final UrbanRoutesFragment.ClickListener clickListener ) {
            this.mClickListener = clickListener;
            mGestureDetector = new GestureDetector(context,
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public  boolean onSingleTapUp(MotionEvent e) {
                            return true;
                        }
                        @Override
                        public void onLongPress(MotionEvent e) {
                            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                            if (child != null && clickListener != null) {
                                clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                            }
                        }
                    });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if( child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)) {
                mClickListener.onClick( child, rv.getChildAdapterPosition(child));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
