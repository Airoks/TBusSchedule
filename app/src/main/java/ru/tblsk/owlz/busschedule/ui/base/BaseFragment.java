package ru.tblsk.owlz.busschedule.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import butterknife.Unbinder;
import ru.tblsk.owlz.busschedule.di.component.ActivityComponent;


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

    public ActivityComponent getActivityComponent() {
        if(mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }

    @Override
    public void onError(String message) {
        if(mActivity != null) {
            mActivity.onError(message);
        }
    }

    protected abstract void setUp(View view);
    protected abstract void setupToolbar();

    public interface Callback {
        //калбэки для взаимодествия между фрагментами
    }
}
