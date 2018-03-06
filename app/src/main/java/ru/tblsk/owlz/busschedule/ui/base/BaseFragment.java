package ru.tblsk.owlz.busschedule.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

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
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null) {
            mUnbinder.unbind();
        }
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

    public interface Callback {
        //калбэки для взаимодествия между фрагментами
    }
}
