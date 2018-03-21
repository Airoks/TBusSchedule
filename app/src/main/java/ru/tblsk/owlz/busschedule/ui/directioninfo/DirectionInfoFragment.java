package ru.tblsk.owlz.busschedule.ui.directioninfo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.routes.urban.ChangeDirectionUrban;
import ru.tblsk.owlz.busschedule.utils.RxEventBus;

public class DirectionInfoFragment extends BaseFragment{

    public static final String TAG = "DirectionInfoFragment";
    public static final String DIRECTION_ID = "directionId";

    @Inject
    RxEventBus mEventBus;

    @BindView(R.id.testButton)
    Button button;

    public static DirectionInfoFragment newInstance(Long directionId) {
        Bundle bundle = new Bundle();
        bundle.putLong(DIRECTION_ID, directionId);
        DirectionInfoFragment newInstance = new DirectionInfoFragment();
        newInstance.setArguments(bundle);
        return newInstance;
    }

    @Override
    protected void setUp(View view) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direction_info, container, false);
        getBaseActivity().getActivityComponent().fragmentComponent(new FragmentModule(this))
                .inject(this);
        setUnbinder(ButterKnife.bind(this, view));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDirectionUrban.InFragment inFragment =
                        new ChangeDirectionUrban.InFragment(0, "reverse");
                mEventBus.post(inFragment);
            }
        });
        return view;
    }
}
