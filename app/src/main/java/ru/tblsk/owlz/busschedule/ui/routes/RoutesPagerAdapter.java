package ru.tblsk.owlz.busschedule.ui.routes;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.tblsk.owlz.busschedule.ui.routes.suburban.SuburbanRouteFragment;
import ru.tblsk.owlz.busschedule.ui.routes.urban.UrbanRouteFragment;

public class RoutesPagerAdapter extends FragmentPagerAdapter{

    private final List<Fragment> Fragments = new ArrayList<>();
    private final List<String> FragmentTitles = new ArrayList<>();

    public RoutesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return Fragments.get(position);
    }

    @Override
    public int getCount() {
        return Fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentTitles.get(position);
    }

    public void addFragments(Fragment fragment, String title) {
        Fragments.add(fragment);
        FragmentTitles.add(title);
    }
}
