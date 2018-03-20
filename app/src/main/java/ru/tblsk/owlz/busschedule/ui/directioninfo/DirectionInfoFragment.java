package ru.tblsk.owlz.busschedule.ui.directioninfo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.tblsk.owlz.busschedule.App;
import ru.tblsk.owlz.busschedule.R;
import ru.tblsk.owlz.busschedule.ui.base.BaseFragment;
import ru.tblsk.owlz.busschedule.ui.routes.urban.ChangeDirectionUrban;

public class DirectionInfoFragment extends BaseFragment{

    public static final String TAG = "DirectionInfoFragment";
    public static final String DIRECTION_ID = "directionId";

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
        setUnbinder(ButterKnife.bind(this, view));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeDirectionUrban.InFragment inFragment =
                        new ChangeDirectionUrban.InFragment(0, "reverse");
                App.getApp(getBaseActivity()).getEventBus().post(inFragment);
            }
        });
        return view;
    }
}
