package ru.tblsk.owlz.busschedule.ui.base;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements MvpView {
    private Unbinder mUnbinder;

    public void setUnbinder(Unbinder unbinder) {
        this.mUnbinder = unbinder;
    }

    @Override
    public void onDestroy() {
        if(mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
