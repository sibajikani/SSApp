package layout;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;


public class product_fragment extends Fragment
{


    public product_fragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login, container, false);


        //extract views for Product Name, Type, Size, Flavor/Color, StockOnHand, UnitPrice, Quantity, Total Price
        TextView ProductName = (TextView)view.findViewById(R.id.txtProdName);
        TextView Type = (TextView)view.findViewById(R.id.txtType);
        TextView Size = (TextView)view.findViewById(R.id.txtSize);
        TextView FlavColor = (TextView)view.findViewById(R.id.txtFlavourColour);
        TextView StockOnHand = (TextView)view.findViewById(R.id.txtStock);
        TextView UnitPrice = (TextView)view.findViewById(R.id.txtUnitPrice);
        TextView Quantity = (TextView)view.findViewById(R.id.txtQuantity);
        TextView TotalPrice = (TextView)view.findViewById(R.id.txtTotalPrice);
        Button Login = (Button)view.findViewById(R.id.btnLogin);

        //Extract Add, Subtract imageviews and Cancel, AddToCart buttons
        EditText edtUserName = (EditText)view.findViewById(R.id.edtUserName);
        EditText edtPassWord = (EditText)view.findViewById(R.id.edtPassword);
        final String UserName = edtUserName.getText().toString();
        final String Password = edtUserName.getText().toString();

        //create onclick listeners for extracted views



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
