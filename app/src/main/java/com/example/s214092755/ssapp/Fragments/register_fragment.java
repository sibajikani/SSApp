package com.example.s214092755.ssapp.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.OrientationHelper;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s214092755.ssapp.Controllers.userController;
import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.MainActivity;
import com.example.s214092755.ssapp.Models.User;
import com.example.s214092755.ssapp.R;

import java.io.IOException;
import java.util.Random;


public class register_fragment extends Fragment
{
    private DatabaseHelper dbh;
    private SQLiteDatabase sdb;
    private userController userController;

    EditText edtName;

    EditText edtSurname;
    EditText edtEmail;
    EditText edtContact;
    EditText edtAddress;
    Button Register;
    Button Cancel;

    public register_fragment()
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
        View view = inflater.inflate(R.layout.registration, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Registration");

        //extract Name, Surname, Email, Contact Number, Physical Address views

        edtName = (EditText)view.findViewById(R.id.edtRName);
        edtSurname = (EditText)view.findViewById(R.id.edtRSurname);
         edtEmail = (EditText)view.findViewById(R.id.edtREmail);
         edtContact = (EditText)view.findViewById(R.id.edtRContact);
         edtAddress = (EditText)view.findViewById(R.id.edtRAddress);


        //extract functionality views i.e Cancel or Register buttons
        Cancel = (Button)view.findViewById(R.id.btnrCancel);

        Register = (Button)view.findViewById(R.id.btnrConfirm);

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
        Register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                final String Name = edtName.getText().toString();
                final String Surname = edtSurname.getText().toString();
                final String Email = edtEmail.getText().toString();
                final String ContactNum = edtContact.getText().toString();
                final String Address = edtAddress.getText().toString();

                //Check if all fields are not empty
                if (Name.length() > 0 && Surname.length() > 0 && Email.length() > 0 && ContactNum.length() > 0 && Address.length() > 0)
                {
                    final String key = generateKey();
                    final User user = new User("",Name,Surname,Email,ContactNum,Address,"",key);
                    //Fields are non-empty so check if the user has already registered
                    if (userController.checkUserExists(user.getEmail()))
                    {
                        //display that the user has already registered at some previous stage
                        Toast.makeText(register_fragment.this.getContext(),"The account provided already exists, press Cancel to return to the Login page and choose to Login",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        //Add the registration details to the database
                        //Toast.makeText(register_fragment.this.getContext(),"You can do this",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Password Registration");
                        builder.setMessage("");
                        LinearLayout layout = new LinearLayout(getContext());
                        layout.setOrientation(LinearLayout.VERTICAL);
                        TextView textView = new TextView(getContext());
                        textView.setText("Enter Password");
                        final EditText editText = new EditText(getContext());
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        TextView textView2 = new TextView(getContext());
                        textView2.setText("Enter Password");
                        final EditText editText2 = new EditText(getContext());
                        editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        layout.addView(textView);
                        layout.addView(editText);
                        layout.addView(textView2);
                        layout.addView(editText2);
                        builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(editText.getText().toString().compareTo(editText2.getText().toString())==0) {
                                    user.setPassword(editText.getText().toString());
                                    userController.addUser(user);
                                    sendEmail(key,Email);
                                    Toast.makeText(getContext(),"Registered",Toast.LENGTH_SHORT).show();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frag_container,new login_fragment()).commit();
                                }
                                else
                                    Toast.makeText(getContext(),"Passwords don't match",Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setView(layout);
                        builder.show();
                    }
                }
                else
                {
                    //display that some fields are empty
                    Toast.makeText(getContext(), "Not all fields have been completed, ensure that all the fields are completed", Toast.LENGTH_SHORT).show();
                }
            }

        });
        //create onclick listeners for Cancel and Register buttons
        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //Registration Cancelled, so reload login fragment

            }

        });

    }

    private String generateKey(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
    private void sendEmail(String key,String email){
        Log.i("Send email", "");
        String[] TO = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        String body = "Thank you for registering on the app. We hope you will enjoy the products \n \n"
                .concat("Recovery key: ").concat(key)
                .concat("\n")
                .concat("Regards \n")
                .concat("The ShapeShifters Team");

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ShapeShifters Recovery Key");
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
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
