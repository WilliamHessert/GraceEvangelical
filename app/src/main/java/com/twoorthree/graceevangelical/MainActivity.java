package com.twoorthree.graceevangelical;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

//    private SectionsPagerAdapter mSectionsPagerAdapter;
//    private ViewPager mViewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    ft.replace(R.id.container, new HomeFragment()).commit();
                    return true;
                case R.id.navigation_prayer:
                    ft.replace(R.id.container, new PrayerFragment()).commit();
                    return true;
                case R.id.navigation_allPrayers:
                    ft.replace(R.id.container, new PrayerView()).commit();
                    return true;
                case R.id.navigation_sermons:
                    ft.replace(R.id.container, new SermonsFragment()).commit();
                    return true;
                case R.id.navigation_connect:
                    ft.replace(R.id.container, new ConnectFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.container, new HomeFragment()).commit();
    }
}
