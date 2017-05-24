package com.example.s214092755.ssapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.s214092755.ssapp.MainActivity;
import com.example.s214092755.ssapp.MainSplash;
import com.example.s214092755.ssapp.Models.User;
import com.example.s214092755.ssapp.R;

import java.io.IOException;


public class login_fragment extends Fragment
    {
        private DatabaseHelper dbh;
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.login, container, false);
            ((MainActivity)getActivity()).getSupportActionBar().setTitle("Login");

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
                            if (userController.checkLogin(UserName, Password) != null)
                            {
                                //User has provided correct username, password combination
                                //Log the user in and transfer to the order fragment
                                FragmentManager manager = getFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction().replace(R.id.frag_container, new MainSplash());
                                transaction.commit();

                                //set current user
                                MainActivity main = (MainActivity)getActivity();

                                main.setCurUser(userController.checkLogin(UserName, Password));

                            }
                            //display that an incorrect username, password combination was provided
                            else {
                                Toast.makeText(getContext(), "Incorrect Password. Re-enter password", Toast.LENGTH_SHORT).show();
                            }

                        }
                        //display that the username provided was not found
                        //Should have a dialog which gives you the option to either go back or register
                        //The toast could say go back and check username or click on the "Not registered" link
                        else {
                            Toast.makeText(getContext(), "The user does not exist, Check username or register", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getContext(), "Ensure that all the fields are completed", Toast.LENGTH_SHORT).show();
                }
            });
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
