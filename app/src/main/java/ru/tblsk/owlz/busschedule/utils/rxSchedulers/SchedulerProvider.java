package ru.tblsk.owlz.busschedule.utils.rxSchedulers;


import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler ui();
    Scheduler computation();
    Scheduler io();
}
