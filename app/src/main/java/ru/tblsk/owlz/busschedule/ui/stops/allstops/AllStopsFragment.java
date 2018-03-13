package ru.tblsk.owlz.busschedule.ui.stops.allstops;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.data.db.model.Stop;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.stops.StopsAdapter;

public class AllStopsFragment extends BaseFragment implements AllStopsMvpView{

    @Inject
    AllStopsPresenter<AllStopsMvpView> mPresenter;

    @Inject
    StopsAdapter mStopsAdapter;

    @Inject
    LinearLayoutManager mLinearLayout;

    @BindView(R.id.allStopToolbar)
    Toolbar mToolbar;

    @BindView(R.id.allStopRv)
    RecyclerView mRecyclerView;

    @BindView(R.id.fastScroll)
    FastScroller mFastScroller;

    private List<Stop> mStops;

    public static AllStopsFragment newInstance() {
        return new AllStopsFragment();
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    protected void setUp(View view) {
        mLinearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayout);
        mRecyclerView.setAdapter(mStopsAdapter);
        mFastScroller.setRecyclerView(mRecyclerView);
        setupToolbar();
        mPresenter.getAllStops();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getBaseActivity(),
                mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                long stopId = mStops.get(position).getId();
                mPresenter.insertSearchHistoryStops(stopId);
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("Click", "LongClick!");
            }
        }));
    }

    @Override
    protected void setupToolbar() {
        getBaseActivity().setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setTitle(R.string.all_stops);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.black));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //back to history stops fragment
                FragmentManager fragmentManager = getBaseActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }

    @Override
    public void showAllStops(List<Stop> stops) {
        mStops = stops;
        mStopsAdapter.addItems(stops);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allstop, container, false);
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        setUnbinder(ButterKnife.bind(this, view));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mToolbar.setTitle(R.string.all_stops);
    }

    public static interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }

    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener mClickListener;
        private GestureDetector mGestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener ) {
            this.mClickListener = clickListener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
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
