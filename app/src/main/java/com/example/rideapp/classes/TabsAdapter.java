package com.example.rideapp.classes;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.rideapp.R;
import com.example.rideapp.fragments.SignInFragment;
import com.example.rideapp.fragments.SignUpFragment;

public class TabsAdapter extends FragmentPagerAdapter {
    private static  final int tabsCount = 2;
    private static  final int[] TABS_TITLES = new int[]{R.string.sign_up,R.string.sign_in};
    private final Context context;
    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  SignUpFragment.newInstance();
            case 1:
               return SignInFragment.newInstance();
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return tabsCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TABS_TITLES[position]);
    }
}
