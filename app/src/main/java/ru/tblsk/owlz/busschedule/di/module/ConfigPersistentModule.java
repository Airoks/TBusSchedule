package ru.tblsk.owlz.busschedule.di.module;


import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.tblsk.owlz.busschedule.di.annotation.ConfigPersistent;

@Module
public class ConfigPersistentModule {

    @Provides
    @ConfigPersistent
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
