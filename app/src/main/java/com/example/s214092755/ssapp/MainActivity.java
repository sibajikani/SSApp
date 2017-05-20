package com.example.s214092755.ssapp;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.s214092755.ssapp.Controllers.userController;

import java.io.IOException;

import com.example.s214092755.ssapp.Fragments.forgotpassword_fragment;
import com.example.s214092755.ssapp.Fragments.login_fragment;
import com.example.s214092755.ssapp.Fragments.product_fragment;
import com.example.s214092755.ssapp.Fragments.register_fragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseHelper dbh;
    private SQLiteDatabase sdb;
    private userController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MainSplash main = new MainSplash();
        FragmentManager fragmentManager=getSupportFragmentManager();
        //add all fragments to fragment manager
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.frag_container,new login_fragment(), "login_frag");
          ft.add(R.id.frag_container,new register_fragment(), "register_frag");
         ft.add(R.id.frag_container,new forgotpassword_fragment(), "forgotpassword_frag");
        ft.add(R.id.frag_container,new product_fragment(), "product_frag");
        ft.add(R.id.frag_container,new product_fragment(), "current_order_frag");
        ft.add(R.id.frag_container,new product_fragment(), "transaction_history_frag");
        ft.replace(R.id.frag_container, new MainSplash());

        ft.commit();

        MainActivity context = MainActivity.this;
        dbh = new DatabaseHelper(context);
        try {
            dbh.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        userController = new userController(dbh, context);

        //getSupportFragmentManager().beginTransaction().add(R.id.frag_container, main).commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager manager = getSupportFragmentManager();
        //Fragment transaction
        FragmentTransaction ft = manager.beginTransaction();
        //Fragment to switch to
        android.support.v4.app.Fragment frag;

        //Products
        if (id == R.id.nav_products) {
            //Switch to products fragment
            //frag = getFragmentManager().findFragmentByTag("home_frag"));
            ft.replace(R.id.frag_container, frag);
            ft.commit();
        }

        //Login
        else if (id == R.id.nav_login) {
            //Switch to login fragment
            //frag = new login_fragment();
            //frag = getFragmentManager().findFragmentById(R.layout.login);
            //frag = (getFragmentManager().findFragmentByTag("login_frag"));
            FragmentTransaction ft2 = manager.beginTransaction();
            ft2.replace(R.id.frag_container,new login_fragment());
            ft2.addToBackStack(null);
            ft2.commit();
        }

        //Register
        else if (id == R.id.nav_register) {
            //Switch to register fragment
            frag = (getFragmentManager().findFragmentByTag("register_frag"));
            ft.replace(R.id.frag_container, frag);
            ft.commit();
        }

        //Current Order
        else if (id == R.id.nav_order) {
            //Switch to order fragment
            frag = (getFragmentManager().findFragmentByTag("current_order_frag"));
            ft.replace(R.id.frag_container, frag);
            ft.commit();


        }

        //Transaction History
        else if (id == R.id.nav_history) {
            //Switch to transaction fragment
            frag = (getFragmentManager().findFragmentByTag("transaction_history_frag"));
            ft.replace(R.id.frag_container, frag);
            ft.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
