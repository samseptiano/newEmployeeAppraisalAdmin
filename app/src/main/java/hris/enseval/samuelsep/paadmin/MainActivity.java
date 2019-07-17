package hris.enseval.samuelsep.paadmin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hris.enseval.samuelsep.paadmin.Fragments.EmpTableFragment;
import hris.enseval.samuelsep.paadmin.Fragments.Employee.EmployeeDetailFragment;
import hris.enseval.samuelsep.paadmin.Fragments.Form.FormDetailFragment;
import hris.enseval.samuelsep.paadmin.Fragments.HomeFragment;
import hris.enseval.samuelsep.paadmin.Fragments.InputFragment;
import hris.enseval.samuelsep.paadmin.Fragments.SettingFormFragment;
import hris.enseval.samuelsep.paadmin.Fragments.SettingFragment.MailReminderSettingFragment;
import hris.enseval.samuelsep.paadmin.Fragments.SettingWorkflowFragment;
import hris.enseval.samuelsep.paadmin.Helper.RealmHelper.MailReminderRealmHelper;
import hris.enseval.samuelsep.paadmin.Helper.RealmHelper.UserRealmHelper;
import hris.enseval.samuelsep.paadmin.Model.RealmModel.UserRealmModel;
import hris.enseval.samuelsep.paadmin.Session.SessionManagement;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;
    Realm realm;
    SessionManagement session;
    boolean a = true;


    public interface hideBurger{
        void setDrawerEnabled(boolean isEnabled);
    }

    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
        toggle.syncState();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


// Session class instance

        session = new SessionManagement(getApplicationContext());

         int REQUEST_EXTERNAL_STORAGE = 1;
         String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        this,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }


        //======================================================================
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        //======================================================================

        if(session.checkLogin().equals("logout")){
            finish();
        }
        else if(session.isLoggedIn() == true) {

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            TextView navUsername = (TextView) headerView.findViewById(R.id.textViewUser);

            UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());
            List<UserRealmModel> aaa = userRealmHelper.findAllArticle();
            navUsername.setText(aaa.get(0).getUsername());

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            loadHomeFragment();


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);



            toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_frame);

        if(f instanceof FormDetailFragment){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            setDrawerEnabled(true);
            super.onBackPressed();
        }
        else if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
//                Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_frame);

                if (f instanceof HomeFragment) {
                    //super.onBackPressed();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    finish();
                }
                if (f instanceof EmpTableFragment) {
                    //super.onBackPressed();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    finish();
                }
                if (f instanceof InputFragment) {
                    //super.onBackPressed();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    finish();
                }
                if (f instanceof SettingWorkflowFragment) {
                    //super.onBackPressed();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    finish();
                }
                if (f instanceof SettingFormFragment) {
                    //super.onBackPressed();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    finish();
                }
                if (f instanceof MailReminderSettingFragment) {
                    //super.onBackPressed();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    finish();
                }
                if (f instanceof FormDetailFragment) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    setDrawerEnabled(true);
                    super.onBackPressed();
                    //finish();
                }
                if (f instanceof EmployeeDetailFragment) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    setDrawerEnabled(true);
                    super.onBackPressed();
                    //finish();
                }
                //super.onBackPressed();
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

        if (id == R.id.logout) {
            UserRealmHelper userRealmHelper = new UserRealmHelper(getApplicationContext());
            userRealmHelper.deleteAllData();
            MailReminderRealmHelper mailReminderRealmHelper = new MailReminderRealmHelper(getApplicationContext());
            mailReminderRealmHelper.deleteAllData();
            session.logoutUser();
            finish();
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {
            // Handle the camera action
//            setDrawerEnabled(true);
                loadHomeFragment();

        } else if (id == R.id.nav_input) {
//            setDrawerEnabled(true);
            loadInputFragment();

        } else if (id == R.id.nav_setting_form) {
//            setDrawerEnabled(true);
            loadFormFragment();

        } else if (id == R.id.nav_setting_workflow) {
//            setDrawerEnabled(true);
            loadWorkflowFragment();

    } else if (id == R.id.nav_set_reminder) {
//            setDrawerEnabled(true);
            loadMailReminderSettingFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void loadHomeFragment(){
        fr = new HomeFragment();
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//            fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
    }

    private void loadInputFragment(){
        fr = new InputFragment();
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//            fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        Toast.makeText(getApplicationContext(), "Input Area", Toast.LENGTH_SHORT).show();
    }

    private void loadWorkflowFragment(){
        fr = new SettingWorkflowFragment();
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//            fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        Toast.makeText(getApplicationContext(), "Setting WorkFlow Area", Toast.LENGTH_SHORT).show();
    }

    private void loadFormFragment(){
        fr = new SettingFormFragment();
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//            fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        Toast.makeText(getApplicationContext(), "Setting Form Area", Toast.LENGTH_SHORT).show();
    }


    private void loadMailReminderSettingFragment(){
        fr = new MailReminderSettingFragment();
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//            fr.setArguments(bundle);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.detach(fr);
        ft.attach(fr);
        ft.addToBackStack("fragmentHome");
        ft.commit();
        Toast.makeText(getApplicationContext(), "Setting Reminder Area", Toast.LENGTH_SHORT).show();
    }
}
