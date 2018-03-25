package ru.tblsk.owlz.busschedule.ui.splash;


import android.util.Log;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.ui.base.BasePresenter;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.SchedulerProvider;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V>
        implements SplashMvpPresenter<V>{

    @Inject
    public SplashPresenter(DataManager dataManager,
                           CompositeDisposable compositeDisposable,
                           SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);

    }

    @Override
    public void attachView(V mvpView) {
        super.attachView(mvpView);
        //if(getDataManager().getFirstRunVariable()) {
            Log.d("SplashPresenter", "seeeeed!!!");
            getCompositeDisposable().add(getDataManager()
                    .seedAllTables()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            getDataManager().setFirstRunVariable(false);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //как то обработать
                            String error = throwable.getMessage();
                            Log.d("Seeding DataBase: ", error);
                            throwable.printStackTrace();
                        }
                    }));
        //}
        getMvpView().openMainActivity();
    }
}
