package com.example.saiteja.myfootballapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static com.example.saiteja.myfootballapp.IndiFixtureFragment.newInstance;

public class MainActivity extends AppCompatActivity
        implements CPFragment.OnFragmentInteractionListener,CompareFragment.OnFragmentInteractionListener,IndiPosFragment.OnFragmentInteractionListener,IndiFixtureFragment.OnFragmentInteractionListener,FixtureFragment.OnFragmentInteractionListener,DreamTeamFragment.OnFragmentInteractionListener,StatsFragment.OnFragmentInteractionListener, TeamFragment.OnFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener {

    private boolean viewIsAtHome;
    public String s1 = "";
    public String s2 = "";
    public String s3 = "";
    public String p1 = "";
    public String p2 = "";
    public int current_id = R.id.nav_camera;
    public int previous_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent = getIntent();
        p1 = intent.getStringExtra("player1");
        p2 = intent.getStringExtra("player2");
        int x = intent.getIntExtra("flag",0);
        if(x==0)
            displayView(R.id.nav_camera);
        else
            displayView(12);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!viewIsAtHome) { //if the current view is not the News fragment
            displayView(previous_id); //display the News fragment
        } else {
            moveTaskToBack(true);  //If view is in News fragment, exit application
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClickFixture(View v){
        s1 = ((TextView)v.findViewById(R.id.homet)).getText().toString();
        s2 = ((TextView)v.findViewById(R.id.awayt)).getText().toString();
        displayView(10);
    }

    public void onClickPosition(View v){
        s3 = ((TextView)v).getText().toString();
        displayView(11);
    }

//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);
        previous_id = current_id;
        current_id = viewId;

        switch (viewId) {
            case R.id.nav_camera:
                fragment = new FixtureFragment();
                title  = "Fixtures";
                viewIsAtHome = true;
                break;
            case R.id.nav_gallery:
                fragment = new TeamFragment();
                title = "Predicted XV";
                viewIsAtHome = false;
                break;
            case R.id.nav_slideshow:
                fragment = new DreamTeamFragment();
                title = "Dream Team";
                viewIsAtHome = false;
                break;
            case R.id.nav_manage:
                fragment = new StatsFragment();
                title = "Positions";
                viewIsAtHome = false;
                break;
            case R.id.nav_pool:
                fragment = new CompareFragment();
                title = "Compare";
                viewIsAtHome = false;
                break;
            case 10:
                fragment = newInstance(s1,s2);
                title = "Top Performers";
                viewIsAtHome = false;
                break;
            case 11:
                fragment = IndiPosFragment.newInstance(s3);
                title = "Top "+s3;
                viewIsAtHome = false;
                break;
            case 12:
                fragment = CPFragment.newInstance(p1,p2);
                title = p1+" VS "+p2;
                viewIsAtHome = false;
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());
        return true;
    }
}
