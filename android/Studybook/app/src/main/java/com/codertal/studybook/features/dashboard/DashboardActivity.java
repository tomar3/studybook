/*
 * Created by Talab Omar on 11/5/17 12:47 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 11/5/17 12:47 PM
 */

package com.codertal.studybook.features.dashboard;

import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codertal.studybook.R;
import com.codertal.studybook.base.BaseTabFragment;
import com.codertal.studybook.features.homework.HomeworkFragment;
import com.codertal.studybook.features.manage.ManageFragment;
import com.f2prateek.dart.HensonNavigable;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.roughike.bottombar.BottomBar;

import timber.log.Timber;

@HensonNavigable
public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setBackgroundDrawable(null);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpDrawer(toolbar);

        BottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(tabId -> {
            BaseTabFragment selectedFragment;

            switch (tabId){
                case R.id.tab_home:
                    selectedFragment = HomeworkFragment.newInstance();
                    break;

                case R.id.tab_manage:
                    selectedFragment = ManageFragment.newInstance();
                    break;

                default:
                    //TODO: Change to null after creating cases for all tabs
                    selectedFragment = ManageFragment.newInstance();
            }

            if(selectedFragment == null){
                throw new RuntimeException("Selected tab has an unchecked res id");
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_dash_fragment, selectedFragment, selectedFragment.getFragmentTag());
            transaction.commit();

            Timber.d("Selected Fragment Tag: " + selectedFragment.getFragmentTag());
        });

        //Manually displaying the first fragment - one time only
        //TODO: Change selected tab to 0th position
        bottomBar.selectTabAtPosition(3, true);
    }

    private void setUpDrawer(Toolbar toolbar) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Item 1").withSelectable(false);
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Item 2").withSelectable(false);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName("Settings").withSelectable(false)
                )
                .withSelectedItem(-1)
                .build();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        FlowingDrawer drawer = findViewById(R.id.drawerlayout);
//        if (drawer.is(GravityCompat.START)) {
//            drawer.close(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

}
