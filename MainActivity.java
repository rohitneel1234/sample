package com.example.passat.demo;
import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.view.MenuItem;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentTransaction;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.passat.demo.AboutUs;
import com.example.passat.demo.LoginD;
import com.example.passat.demo.Monitoration;
import com.example.passat.demo.PatientEdition;
import com.example.passat.demo.PatientReport;
import com.example.passat.demo.R;
import com.example.passat.demo.Wifi;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //indices to fragments
    private int wantToExit=0;
    String MainClass="";
    MySqliteAdapter mysql;
    Bundle bundle;
    Button view;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Patient Exercise System");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MainClass = "Monitoration";
        Fragment fragment = new Monitoration();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

        try {
            Intent i = getIntent();
            String get1 = i.getStringExtra("PatientReg");
            if(get1==null)
            {

            }
            else {
                if ((get1.equals("PatientReg")) == true) {
                    Fragment f = new AboutUs();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
                }

            }
               }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Error:-"+e,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed()
    {

        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {

            drawer.closeDrawer(GravityCompat.START);
        }
        else if(fragmentManager.findFragmentById(R.id.content_frame).getClass().getSimpleName().equals(MainClass))
        {
            if (wantToExit == 1)
            {
                moveTaskToBack(true);
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                System.exit(0);
            } else
            {
                Toast.makeText(getApplicationContext(), "Press Once again to exit.", Toast.LENGTH_SHORT).show();
            }
            wantToExit++;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    wantToExit = 0;
                }
            }).start();

        }
        else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;
        //initializing the fragment object which is selected
        switch (itemId) {

  /*          case R.id.nav_camera:
                fragment = new Login();
                break;
*/
            case R.id.nav_gallery:
                Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
                toolbar2.setTitle("Monitoration");
                fragment= new Monitoration();
                break;

            /*case R.id.nav_slideshow:
                fragment = new Exercise();
                break;  */
     /*       case R.id.nav_patientdata:
                Toolbar toolbar3 = (Toolbar) findViewById(R.id.toolbar);
                toolbar3.setTitle("Patient Data Edition");
                fragment = new PatientEdition();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).addToBackStack("MainActivity").commit();
                break;   */
            case R.id.nav_list:

                Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
                toolbar1.setTitle("Patient Exercise Report");
                fragment = new PatientReport();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).addToBackStack("MainActivity").commit();


                break;

            case R.id.nav_aboutus:
                fragment=new AboutUs();
                Toolbar toolbar5 = (Toolbar) findViewById(R.id.toolbar);
                toolbar5.setTitle("Patient Details");
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).addToBackStack("MainActivity").commit();
                break;
            case R.id.nav_graph:
                fragment=new Graph();
                break;

            case R.id.nav_logout:
                Intent intent=new Intent(MainActivity.this,LoginD.class);
                startActivity(intent);
                break;
            case R.id.nav_exit:


                moveTaskToBack(true);
                finish();
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                System.exit(0);

                break;

        }

        //replacing the fragment
        if (fragment != null)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }
}