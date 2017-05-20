package com.example.s214092755.ssapp.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s214092755.ssapp.Controllers.userController;
import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.Models.User;
import com.example.s214092755.ssapp.R;

import java.io.IOException;
import java.util.Random;


public class register_fragment extends Fragment
{
    private DatabaseHelper dbh;
    private SQLiteDatabase sdb;
    private userController userController;

    public register_fragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.registration, container, false);

        //extract Name, Surname, Email, Contact Number, Physical Address views
        EditText edtName = (EditText)view.findViewById(R.id.edtRName);
        final EditText edtSurname = (EditText)view.findViewById(R.id.edtRSurname);
        EditText edtEmail = (EditText)view.findViewById(R.id.edtREmail);
        EditText edtContact = (EditText)view.findViewById(R.id.edtRContact);
        EditText edtAddress = (EditText)view.findViewById(R.id.edtRAddress);

        //extract string values from extracted views
        final String Name = edtName.getText().toString();
        final String Surname = edtSurname.getText().toString();
        final String Email = edtEmail.getText().toString();
        final String ContactNum = edtContact.getText().toString();
        final String Address = edtAddress.getText().toString();

        //extract functionality views i.e Cancel or Register buttons
        Button Cancel = (Button)view.findViewById(R.id.btnrCancel);
        Button Register = (Button)view.findViewById(R.id.btnrConfirm);

        dbh = new DatabaseHelper(getContext());
        try {
            dbh.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        userController = new userController(dbh,getContext());

        //create onclick listeners for Cancel and Register buttons
        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //Registration Cancelled, so reload login fragment

            }

        });

        Register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                //Check if all fields are not empty
                if (Name.length() > 0 && Surname.length() > 0 && Email.length() > 0 && ContactNum.length() > 0 && Address.length() > 0)
                {
                    User user = new User("",Name,Surname,Email,ContactNum,Address,"",generateKey());
                    //Fields are non-empty so check if the user has already registered
                    if (userController.checkUserExists(user))
                    {
                        //display that the user has already registered at some previous stage
                        Toast.makeText(register_fragment.this.getContext(),"The account provided already exists, press Cancel to return to the Login page and choose to Login",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        //Add the registration details to the database
                        Toast.makeText(register_fragment.this.getContext(),"You can do this",Toast.LENGTH_SHORT).show();

                        //Go to password fragment with user details

                    }

                }

                else
                {
                    //display that some fields are empty
                    Toast.makeText(register_fragment.this.getContext(), "Not all fields have been completed, ensure that all the fields are completed", Toast.LENGTH_SHORT);
                }
            }

        });


        return view;
    }


    //checks if the user is already registered on the system
    public boolean AlreadyRegistered()
    {

        boolean registered = false;

        //check if user exists on the system


        return registered;

    }
    private String generateKey(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
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
