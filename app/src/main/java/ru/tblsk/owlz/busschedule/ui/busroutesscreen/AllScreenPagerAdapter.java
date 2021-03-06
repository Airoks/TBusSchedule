package ru.tblsk.owlz.busschedule.ui.busroutesscreen;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllScreenPagerAdapter extends FragmentPagerAdapter{

    private final List<Fragment> Fragments = new ArrayList<>();
    private final List<String> FragmentTitles = new ArrayList<>();

    public AllScreenPagerAdapter(FragmentManager fm) {
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
