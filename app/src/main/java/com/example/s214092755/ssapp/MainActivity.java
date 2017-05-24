package com.example.s214092755.ssapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.s214092755.ssapp.Fragments.cart_list_fragment;
import com.example.s214092755.ssapp.Fragments.empty_fragment;
import com.example.s214092755.ssapp.Fragments.login_fragment;
import com.example.s214092755.ssapp.Fragments.register_fragment;
import com.example.s214092755.ssapp.Fragments.transaction_list_fragment;
import com.example.s214092755.ssapp.Models.Merchandise;
import com.example.s214092755.ssapp.Models.Supplement;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.Models.User;

import java.util.ArrayList;

/* hey handsome boys*/

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    //List of transactions for current order
    private ArrayList<Transaction> Currrent_Order;

    //List of supplements
    private ArrayList<Supplement> Supplements;

    //List of merchandise
    private ArrayList<Merchandise> Merchandise;

    //Total price of current order
    double totalPrice = 0.0;

    //current user
    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = null;
        Class fragmentClass;


        fragmentClass = MainSplash.class;

        try{
            fragment = (Fragment) fragmentClass.newInstance();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frag_container,fragment).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Currrent_Order = new ArrayList<>();
        curUser = null;

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

    public ArrayList<Transaction> getCurrrent_Order() { return Currrent_Order ;}
    public ArrayList<Supplement> getSupplements() { return Supplements ;}
    public ArrayList<Merchandise> getMerchandise() { return Merchandise ;}

    public void setCurrrent_Order(ArrayList<Transaction> currrent_Order) {Currrent_Order = currrent_Order;}

    public void setSupplements(ArrayList<Supplement> supplements) {Supplements = supplements;}

    public void setMerchandise(ArrayList<Merchandise> merchandise) {Merchandise = merchandise;}

    public void setCurUser(User user) { curUser = user ;}

    public User getCurUser() {return curUser;}

    public double getTotalPrice() {
        return totalPrice;
    }

    public void updateTotalPrice(double totalPrice) {
        this.totalPrice += totalPrice;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        //Products
        if (id == R.id.nav_products) {
            //Switch to products fragment
            fragmentClass= MainSplash.class;
        }

        //Login
        else if (id == R.id.nav_login) {
            //Switch to login fragment
            fragmentClass = login_fragment.class;
        }

        //Register
        else if (id == R.id.nav_register) {
            //Switch to register fragment
            fragmentClass = register_fragment.class;
        }

        //Current Order
        else if (id == R.id.nav_order) {
            //Switch to order fragment
            if(getCurrrent_Order()==null||getCurrrent_Order().size()==0) {
                fragmentClass = empty_fragment.class;
            }
            else {
                fragmentClass = cart_list_fragment.class;
            }
        }

        //Transaction History
        else if (id == R.id.nav_history) {
            //Switch to transaction fragment
            fragmentClass = transaction_list_fragment.class;

        }
        try {
            assert fragmentClass != null;
            fragment = (Fragment) fragmentClass.newInstance();
            //getSupportActionBar().setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frag_container,fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
