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
import ru.tblsk.owlz.busschedule.ui.busroutesscreen.busroutes.SuburbanBusRoutesPresenter;
import ru.tblsk.owlz.busschedule.utils.mappers.FlightMapper;
import ru.tblsk.owlz.busschedule.utils.mappers.viewobject.FlightVO;
import ru.tblsk.owlz.busschedule.utils.rxSchedulers.TestSchedulerProvider;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SuburbanBusRoutesPresenterTest {
    private static final int SUBURBAN = 1;

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
        mPresenter = new SuburbanBusRoutesPresenter(
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
    public void testSuburbanBusRoutesPresenter() throws Exception {

        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight());
        List<FlightVO> flightVOS = new ArrayList<>();
        FlightVO flightVO = new FlightVO();
        flightVO.setCurrentDirectionType(0);
        flightVOS.add(flightVO);

        when(mMapper.apply(flights))
                .thenReturn(flightVOS);
        when(mMockDataManager.getFlightByType(SUBURBAN))
                .thenReturn(Single.just(flights));

        mPresenter.getFlights();
        mTestScheduler.triggerActions();
        verify(mMockView).showRoutes(flightVOS);

        mPresenter.clickedOnAdapterItem(0);
        mTestScheduler.triggerActions();
        verify(mMockView).openDirectionInfoFragment(flightVOS.get(0));

        mPresenter.clickedOnDirectionChangeButton(0, 1);
        mTestScheduler.triggerActions();
        assertThat(flightVOS.get(0).getCurrentDirectionType(), is(1));

        flights.clear();
        flightVOS.clear();
        mPresenter.getFlights();
        mTestScheduler.triggerActions();
        verify(mMockView).showEmptyScreen();

    }

}
