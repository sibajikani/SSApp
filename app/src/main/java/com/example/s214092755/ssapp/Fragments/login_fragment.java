package com.example.s214092755.ssapp.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s214092755.ssapp.R;


public class login_fragment extends Fragment
    {


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
            container.clearDisappearingChildren();
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.login, container, false);


            //extract views for Forgot Password, Not Registered and Login
            TextView ForgotPassword = (TextView)view.findViewById(R.id.txtForgotPassword);
            TextView NotRegistered = (TextView)view.findViewById(R.id.txtNotRegistered);
            Button Login = (Button)view.findViewById(R.id.btnLogin);

            //Extract username and password views
            EditText edtUserName = (EditText)view.findViewById(R.id.edtUserName);
            EditText edtPassWord = (EditText)view.findViewById(R.id.edtPassword);
            final String UserName = edtUserName.getText().toString();
            final String Password = edtUserName.getText().toString();

            //create onclick listeners for extracted views
            ForgotPassword.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Change over to the ForgotPassword fragment
                }
            });

            NotRegistered.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Change over to the Not Registered fragment
                }
            });

            Login.setOnClickListener(new View.OnClickListener()
            {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v)
                {

                    //Check if user exists
                    if (UserFound(UserName))
                    {
                        //Found, so check if the user has provided the correct password
                        if (CorrectCredentials(UserName,Password))
                        {
                            //User has provided correct username, password combination
                            //Log the user in and transfer to the order fragment
                            FragmentManager manager = getFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.commit();
                            Bundle bundle = new Bundle();

                            //manager.putFragment(bundle,"login",new login_fragment());


                            /*//Building a pop dialog
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Error");
                            builder.setMessage("whatever");
                            builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //go to register
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    builder.setCancelable(true);
                                }
                            });
                            builder.show();*/


                        }

                        //display that an incorrect username, password combination was provided
                        else
                        {
                            Toast.makeText(login_fragment.this.getContext(),"The username, password combination provided is incorrect, check the username and password",Toast.LENGTH_SHORT).show();
                        }

                    }


                    //display that the username provided was not found
                    //Should have a dialog which gives you the option to either go back or register
                    //The toast could say go back and check username or click on the "Not registered" link
                    else
                    {
                        Toast.makeText(login_fragment.this.getContext(),"The user does not exist, check the username provided",Toast.LENGTH_SHORT);
                    }
                }
            });


            return view;
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
