package ru.tblsk.owlz.busschedule.ui;

import android.icu.util.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.DepartureTime;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule.BusScheduleContract;
import ru.tblsk.owlz.busschedule.ui.busschedulescreen.busschedule.WeekendSchedulePresenter;
import ru.tblsk.owlz.busschedule.utils.mappers.DepartureTimeMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.DepartureTimeVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.TestSchedulerProvider;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class WeekendSchedulePresenterTest {

    private static final int HOUR_ONE = 0;
    private static final int HOUR_TWO = 1;
    private static final int HOUR_THREE = 2;
    private static final int MINUTE_START = 0;
    private static final int MINUTE_END = 60;

    @Mock
    private BusScheduleContract.View mMockView;
    @Mock
    private DataManager mMockDataManager;
    @Mock
    private DepartureTimeMapper mMapper;

    private BusScheduleContract.Presenter mPresenter;
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mPresenter = new WeekendSchedulePresenter(
                mMockDataManager,
                compositeDisposable,
                testSchedulerProvider,
                mMapper);
        mPresenter.attachView(mMockView);
    }

    @After
    public void tearDown() {
        mPresenter.detachView();
    }

    @Test
    public void testWeekendSchedulePresenter() throws Exception {
        DepartureTime departureTime = new DepartureTime();
        DepartureTimeVO mockedDepartureTime = mock(DepartureTimeVO.class);
        long stopId = 11; long directionId = 10; int directionType = 0;
        List<Integer> hours = new ArrayList<>();
        List<Integer> minute = new ArrayList<>();
        hours.add(HOUR_ONE);hours.add(HOUR_TWO);hours.add(HOUR_THREE);
        Map<Integer, ArrayList<Integer>> time = new HashMap<>();
        for(int i = MINUTE_START; i < MINUTE_END; i ++) {
            minute.add(i);
        }
        time.put(HOUR_ONE, (ArrayList<Integer>) minute);
        time.put(HOUR_TWO, (ArrayList<Integer>) minute);
        time.put(HOUR_THREE, (ArrayList<Integer>) minute);


        when(mockedDepartureTime.getHours()).thenReturn(hours);
        when(mockedDepartureTime.getTime()).thenReturn(time);

        when(mMockDataManager.getSchedule(stopId, directionId, directionType))
                .thenReturn(Single.just(departureTime));
        when(mMapper.apply(departureTime)).thenReturn(mockedDepartureTime);

        when(mockedDepartureTime.isEmpty()).thenReturn(false);
        mPresenter.getSchedule(stopId, directionId, directionType);
        mTestScheduler.triggerActions();
        verify(mMockView).showSchedule(mockedDepartureTime);
        verify(mMockView).setColorItem(HOUR_TWO);

        when(mockedDepartureTime.isEmpty()).thenReturn(true);
        mPresenter.getSchedule(stopId, directionId, directionType);
        mTestScheduler.triggerActions();
        verify(mMockView).showEmptyScreen();


    }
}
