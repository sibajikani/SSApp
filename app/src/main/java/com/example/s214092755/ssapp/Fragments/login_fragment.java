package com.example.s214092755.ssapp.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s214092755.ssapp.Controllers.userController;
import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.R;

import java.io.IOException;


public class login_fragment extends Fragment
    {
        private DatabaseHelper dbh;
        private SQLiteDatabase sdb;
        private userController userController;

        Button Login;
        EditText edtUserName;
        EditText edtPassWord;

        public login_fragment()
        {
            // Required empty public constructor

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        public static login_fragment newInstance() {

            Bundle args = new Bundle();

            login_fragment fragment = new login_fragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.login, container, false);


            //extract views for Forgot Password, Not Registered and Login
            TextView ForgotPassword = (TextView)view.findViewById(R.id.txtForgotPassword);
            TextView NotRegistered = (TextView)view.findViewById(R.id.txtNotRegistered);
            Login = (Button)view.findViewById(R.id.btnLogin);

            //Extract username and password views

            edtUserName = (EditText)view.findViewById(R.id.edtUserName);

            edtPassWord = (EditText)view.findViewById(R.id.edtPassword);
            edtPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());

            dbh = new DatabaseHelper(getContext());
            try {
                dbh.updateDataBase();
            } catch (IOException mIOException) {
                throw new Error("UnableToUpdateDatabase");
            }

            userController = new userController(dbh,getContext());

            //create onclick listeners for extracted views
            ForgotPassword.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Change over to the ForgotPassword fragment
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frag_container,new forgotpassword_fragment()).commit();

                }
            });

            NotRegistered.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Change over to the Not Registered fragment
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frag_container,new register_fragment()).commit();
                }
            });

            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Login.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    String UserName = edtUserName.getText().toString();
                    String Password = edtPassWord.getText().toString();
                    //Check if user exists
                    if (UserName.length() > 0 && Password.length() > 0) {
                        if (userController.checkUserExists(UserName)) {
                            //Found, so check if the user has provided the correct password
                            if (userController.checkLogin(UserName, Password) != null) {
                                //User has provided correct username, password combination
                                //Log the user in and transfer to the order fragment
                                Toast.makeText(getContext(), "The username, password combination provided, check the username and password", Toast.LENGTH_LONG).show();
                                android.support.v4.app.FragmentManager manager = getFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction().replace(R.id.frag_container, new product_fragment());
                                transaction.commit();

                            }
                            //display that an incorrect username, password combination was provided
                            else {
                                Toast.makeText(getContext(), "The username, password combination provided is incorrect, check the username and password", Toast.LENGTH_LONG).show();
                            }

                        }
                        //display that the username provided was not found
                        //Should have a dialog which gives you the option to either go back or register
                        //The toast could say go back and check username or click on the "Not registered" link
                        else {
                            Toast.makeText(getContext(), "The user does not exist, check the username provided", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getContext(), "Not all fields have been completed, ensure that all the fields are completed", Toast.LENGTH_SHORT).show();
                }
            });




        }

        //checks if the username exists within the database
        public boolean UserFound(String UserName)
        {
            boolean found = false;

            //Run through database, checking if user exists


            return found;
        }

        //checks if the username and password combination provided is correct
        public boolean CorrectCredentials(String UserName, String Password)
        {
            boolean found = false;

            //Run through database, checking if the username, password combination is correct


            return found;
        }


        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }
}
