package ru.tblsk.owlz.busschedule.ui;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;
import ru.tblsk.owlz.busschedule.data.DataManager;
import ru.tblsk.owlz.busschedule.data.db.model.Flight;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroutes.BusRoutesContract;
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroutes.UrbanBusRoutesPresenter;
import ru.tblsk.owlz.busschedule.utils.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.TestSchedulerProvider;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UrbanBusRoutesPresenterTest {

    private static final int URBAN = 0;

    @Mock
    private BusRoutesContract.View mMockView;
    @Mock
    private DataManager mMockDataManager;
    @Mock
    private FlightMapper mMapper;

    private BusRoutesContract.Presenter mPresenter;
    private TestScheduler mTestScheduler;

    @Before
    public void setUp() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);
        mPresenter = new UrbanBusRoutesPresenter(
                mMockDataManager,
                compositeDisposable,
                testSchedulerProvider,
                mMapper);
        mPresenter.attachView(mMockView);
    }

    @Test
    public void testUrbanBusRoutesPresenter() throws Exception {

        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight());
        List<FlightVO> flightVOS = new ArrayList<>();
        FlightVO flightVO = new FlightVO();
        flightVO.setCurrentDirectionType(0);
        flightVOS.add(flightVO);

        when(mMapper.apply(flights))
                .thenReturn(flightVOS);
        when(mMockDataManager.getFlightByType(URBAN))
                .thenReturn(Single.just(flights));

        mPresenter.getFlights();
        mTestScheduler.triggerActions();
        verify(mMockView).showRoutes(flightVOS);

        mPresenter.clickedOnAdapterItem(0);
        mTestScheduler.triggerActions();
        verify(mMockView).openDirectionInfoFragment(flightVOS.get(0));

        System.out.println("Current direction: " + flightVOS.get(0).getCurrentDirectionType());
        mPresenter.clickedOnDirectionChangeButton(0, 1);
        mTestScheduler.triggerActions();
        System.out.println("Current direction: " + flightVOS.get(0).getCurrentDirectionType());

        flights.clear();
        flightVOS.clear();
        mPresenter.getFlights();
        mTestScheduler.triggerActions();
        verify(mMockView).showEmptyScreen();

    }

    @After
    public void clear() {
        mPresenter.detachView();
    }
}
