package com.example.sonic.whatdoyoudo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.sonic.whatdoyoudo.fragment.HomeFragment;
import com.example.sonic.whatdoyoudo.fragment.TestFragment;
import com.example.sonic.whatdoyoudo.fragment.TrainFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import junit.framework.Test;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements HomeFragment.OnFragmentInteractionListener,
        TrainFragment.OnFragmentInteractionListener,
        TestFragment.OnFragmentInteractionListener {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    Drawer mainDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setLayout(savedInstanceState);
        addNavigationDrawer();
        intentNavigation();
    }

    public void setLayout(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = HomeFragment.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("satu").commit();
        }
    }

    public void addNavigationDrawer() {
        Log.i("debugs", "MainAcitivity addNavigationDrawer");
        PrimaryDrawerItem home = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        PrimaryDrawerItem train = new PrimaryDrawerItem().withIdentifier(2).withName("Train");
        PrimaryDrawerItem test = new PrimaryDrawerItem().withIdentifier(3).withName("Test");
        PrimaryDrawerItem exit = new PrimaryDrawerItem().withIdentifier(4).withName("Exit");
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimaryDark)
                .addProfiles(
                        new ProfileDrawerItem().withName("Sonic Adventure").withEmail("sonic@softart.com").withIcon(getResources().getDrawable(R.mipmap.ic_launcher)).withSelectedTextColor(this.getResources().getColor(R.color.md_white_1000))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        mainDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggle(toggle)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        home,
                        train,
                        test,
                        exit
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Fragment fragment = null;
                        Class fragmentClass = HomeFragment.class;
                        Log.i("debugs", "MainActivity onItemClick position : " + position);
                        switch (position) {
                            case 1://HomeFragment
                                fragmentClass = HomeFragment.class;
                                break;
                            case 2://TrainFragment
                                fragmentClass = TrainFragment.class;
                                break;
                            case 3://TestFragment
                                fragmentClass = TestFragment.class;
                                break;
                            case 4://home
                                break;
                            case 5://home
                                break;
                            case 6://home
                                break;
                            case 7://home
                                break;
                            case 8://home
                                break;
                            case 9://home
                                break;
                        }
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("satu").commit();
                        mainDrawer.closeDrawer();
                        return true;
                    }
                })
                .build();
    }

    public void intentNavigation() {
        Intent intent = getIntent();
        String goto_item = intent.getStringExtra("goto_item");
        if (goto_item != null && !goto_item.isEmpty()) {
            if (goto_item != null && goto_item.equals("Home")) {
                Fragment fragment = null;
                Class fragmentClass = HomeFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("satu").commit();
            } else if (goto_item != null && goto_item.equals("Train")) {
                Fragment fragment = null;
                Class fragmentClass = TrainFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("satu").commit();
            } else if (goto_item != null && goto_item.equals("Test")) {
                Fragment fragment = null;
                Class fragmentClass = TestFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack("satu").commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            try {
                int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
                if (backStackEntryCount == 1) {
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory(Intent.CATEGORY_HOME);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                } else {
                    super.onBackPressed();
                    getFragmentManager().popBackStack();
                    ;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void switchFragment(Fragment fragment, String title, String subTitle) {
        setTitle(title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent, fragment)
                .addToBackStack("satu")
                .commit();
    }
}
