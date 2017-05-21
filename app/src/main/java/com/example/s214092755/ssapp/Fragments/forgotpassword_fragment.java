package com.example.s214092755.ssapp.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s214092755.ssapp.Controllers.userController;
import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.R;

import java.io.IOException;

import static com.example.s214092755.ssapp.R.id.edtConfNewPass;
import static com.example.s214092755.ssapp.R.id.edtNewPass;
import static com.example.s214092755.ssapp.R.id.edtRecKey;


public class forgotpassword_fragment extends Fragment
{
    private DatabaseHelper dbh;
    private SQLiteDatabase sdb;
    private com.example.s214092755.ssapp.Controllers.userController userController;
    String RecKey;
    String NewPass;
    String ConfNewPass;
    EditText edtRecKey;
    EditText edtNewPass;
    EditText edtConfNewPass;
    Button Cancel;
    Button Confirm;

    public forgotpassword_fragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.forgot_password, container, false);


        //extract views for Recovery Key, New Password, Confirm New Password

        edtRecKey = (EditText)view.findViewById(R.id.edtRecKey);
        edtNewPass = (EditText)view.findViewById(R.id.edtNewPass);
        edtConfNewPass = (EditText)view.findViewById(R.id.edtConfNewPass);


        //extract views for Cancel, Confirm buttons

        Cancel = (Button)view.findViewById(R.id.btnfpCancel);
        Confirm = (Button)view.findViewById(R.id.btnfpConfirm);

        dbh = new DatabaseHelper(getContext());
        try {
            dbh.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        userController = new userController(dbh,getContext());


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //create onclick listeners for buttons
        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Replace current fragment with login fragment


            }
        });

        Confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RecKey = edtRecKey.getText().toString();
                NewPass = edtNewPass.getText().toString();
                ConfNewPass = edtConfNewPass.getText().toString();
                if(RecKey.length()>0&&NewPass.length()>0&&ConfNewPass.length()>0) {
                    //Check if RecKey exists
                    if (userController.checkRecKey(RecKey)) {
                        if(NewPass.equals(ConfNewPass)) {
                            //Change the password in the database
                            userController.changePassword(NewPass);

                            //Replace current fragment with login fragment
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction transaction =fragmentManager.beginTransaction().replace(R.id.frag_container,new login_fragment());
                            transaction.commit();
                        }
                        else
                            Toast.makeText(forgotpassword_fragment.this.getContext(), "Passwords dont match", Toast.LENGTH_SHORT).show();


                    }

                    //RecKey was not found, so display
                    Toast.makeText(forgotpassword_fragment.this.getContext(), "Recovery key entered was not found, check recovery key and try again", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(forgotpassword_fragment.this.getContext(), "Not all fields have been completed, ensure that all the fields are completed", Toast.LENGTH_SHORT).show();
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
