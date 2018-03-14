package ru.tblsk.owlz.busschedule.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import butterknife.Unbinder;
import ru.tblsk.owlz.busschedule.di.component.ActivityComponent;
import ru.tblsk.owlz.busschedule.di.component.FragmentComponent;
import ru.tblsk.owlz.busschedule.di.module.FragmentModule;


public abstract class BaseFragment extends Fragment implements MvpView {

    private Unbinder mUnbinder;
    private BaseActivity mActivity;

    public void setUnbinder(Unbinder unbinder) {
        this.mUnbinder = unbinder;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onDestroyView() {
        if(mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    @Override
    public void onError(String message) {
        if(mActivity != null) {
            mActivity.onError(message);
        }
    }

    protected abstract void setUp(View view);

    public interface Callback {
        //калбэки для взаимодествия между фрагментами
    }
}
