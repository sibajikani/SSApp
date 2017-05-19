package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s214092755.ssapp.R;


public class forgotpassword_fragment extends Fragment
{


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
        final EditText edtRecKey= (EditText)view.findViewById(R.id.edtRecKey);
        final EditText edtNewPass = (EditText)view.findViewById(R.id.edtNewPass);
        final EditText edtConfNewPass = (EditText)view.findViewById(R.id.edtConfNewPass);

        //extract text entered from extracted views above
        final String RecKey = edtRecKey.getText().toString();
        final String NewPass = edtNewPass.getText().toString();
        final String ConfNewPass= edtConfNewPass.getText().toString();

        //extract views for Cancel, Confirm buttons
        Button Cancel = (Button)view.findViewById(R.id.btnfpCancel);
        Button Confirm = (Button)view.findViewById(R.id.btnfpConfirm);

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
                //Check if RecKey exists
                if (RecKeyFound(RecKey))
                {

                    //Change the password in the database

                    //Show that password has been successfully changed
                    Toast.makeText(forgotpassword_fragment.this.getContext(), "Password successfully changed, proceed to login", Toast.LENGTH_SHORT).show();

                    //Replace current fragment with login fragment


                }

                //RecKey was not found, so display
                Toast.makeText(forgotpassword_fragment.this.getContext(), "Recovery key entered was not found, check recovery key and try again", Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }

    //checks if the RecKey exists within the database
    public boolean RecKeyFound(String RecKey)
    {
        boolean found = false;

        //Run through database, checking if user exists


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
