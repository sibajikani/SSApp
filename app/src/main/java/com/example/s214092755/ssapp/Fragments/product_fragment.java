package com.example.s214092755.ssapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s214092755.ssapp.Controllers.ProductController;
import com.example.s214092755.ssapp.DatabaseHelper;
import com.example.s214092755.ssapp.MainActivity;
import com.example.s214092755.ssapp.Models.Merchandise;
import com.example.s214092755.ssapp.Models.Product;
import com.example.s214092755.ssapp.Models.Supplement;
import com.example.s214092755.ssapp.Models.Transaction;
import com.example.s214092755.ssapp.Models.User;
import com.example.s214092755.ssapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class product_fragment extends Fragment
{


    public product_fragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.product, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Product");


        //extract views for Product Name, Type, Size, Flavor/Color, StockOnHand, UnitPrice, Quantity, Total Price
        final TextView ProductName = (TextView)view.findViewById(R.id.txtProdName);
        TextView Type = (TextView)view.findViewById(R.id.txtType);
        TextView Size = (TextView)view.findViewById(R.id.txtSize);
        final TextView StockOnHand = (TextView)view.findViewById(R.id.txtStock);
        final TextView UnitPrice = (TextView)view.findViewById(R.id.txtUnitPrice);
        final TextView Quantity = (TextView)view.findViewById(R.id.txtQuantity);
        final TextView TotalPrice = (TextView)view.findViewById(R.id.txtTotalPrice);
        TextView FlavourColour = (TextView)view.findViewById(R.id.txtFlavourColour);
        ImageView imgProduct = (ImageView)view.findViewById(R.id.imageVProduct);


        Integer cur_quantity = 1;
        Quantity.setText(String.valueOf(cur_quantity));

        //get product bundle
        final Bundle Prod_bundle = getArguments();

        //Fragment to send product info to and it's corresponding bundle
        final Fragment cur_order_frag =  new cart_list_fragment();
        final Bundle Order_bundle = new Bundle();

        //fill the textviews with the fields of the product passed through in this fragment's bundle
        final Supplement curSup;
        final Merchandise curMerch;

        final Spinner spinFlavCol = (Spinner)view.findViewById(R.id.spinFlavourColour);

        Bundle bundle = getArguments();

        if(bundle!=null){
            String type = bundle.getString("type");
            assert type != null;
            if(type.equals("supp"))
            {
                curSup = (Supplement) bundle.get("curSup");
                assert curSup != null;
                ProductName.setText(curSup.getName());
                Type.setText(curSup.getTypeSup());
                Size.setText(curSup.getSize());

                //flavour/colour spinner
                ProductController controller = new ProductController(new DatabaseHelper(getContext()),getContext());
                Map<Integer,ArrayList<Supplement>> MappedSupps = controller.getAllSupplements();
                ArrayList<Supplement> Supps;
                ArrayList<String> flavours = new ArrayList<>();

                //populate list of values (flavours or colours) for spinner
                for (Map.Entry<Integer,ArrayList<Supplement>> entry : MappedSupps.entrySet())
                {
                    if (entry.getKey().toString().equals(curSup.getID()))
                    {
                        Supps = entry.getValue();
                        for (Supplement s : Supps)
                        {
                            flavours.add(s.getFlavour());
                        }

                        break;
                    }

                }
                //create adapter for spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, flavours);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set adapter for spinner
                spinFlavCol.setAdapter(adapter);
                //set FlavourColour view to display "Flavour"
                FlavourColour.setText("Flavour");
                String supHand = String.valueOf(curSup.getOnHandSup());
                StockOnHand.setText(supHand);
                UnitPrice.setText(String.valueOf(curSup.getUnitPrice()));
                //total price
                Double total = curSup.getUnitPrice() * cur_quantity;
                TotalPrice.setText(total.toString());
                //image of supplement

                int drawableResourceId = this.getResources().getIdentifier(curSup.getPicLink(), "drawable", this.getContext().getPackageName());
                imgProduct.setImageResource(drawableResourceId);
                imgProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Fragment fragment = new pic_fragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("picLink",curSup.getPicLink());
                        fragment.setArguments(bundle);
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.frag_container,fragment).commit();
                    }
                });



            }
            else {
                curMerch = (Merchandise) bundle.get("curMerch");
                assert curMerch != null;
                ProductName.setText(curMerch.getName());
                Type.setText(curMerch.getType());
                Size.setText(curMerch.getSize());
                //flavour/colour spinner
                ProductController controller = new ProductController(new DatabaseHelper(getContext()),getContext());
                Map<Integer,ArrayList<Merchandise>> MappedMerch = controller.getAllMerchandise();
                ArrayList<Merchandise> Merch;
                ArrayList<String> colours = new ArrayList<>();

                //populate list of values (flavours or colours) for spinner
                for (Map.Entry<Integer,ArrayList<Merchandise>> entry : MappedMerch.entrySet())
                {
                    if (entry.getKey().toString().equals(curMerch.getID()))
                    {
                        Merch = entry.getValue();
                        for (Merchandise m : Merch)
                        {
                            colours.add(m.getColour());
                        }
                        break;
                    }

                }
                //create adapter for spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, colours);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set adapter for spinner
                spinFlavCol.setAdapter(adapter);
                //set FlavourColour view to display "Colour"
                FlavourColour.setText("Colour");
                StockOnHand.setText(String.valueOf(curMerch.getOnHand1()));
                UnitPrice.setText(String.valueOf(curMerch.getUnitPrice()));
                //total price
                Double total = curMerch.getUnitPrice() * cur_quantity;
                TotalPrice.setText(String.valueOf(total.toString()));
                //image of merchandise item
                int drawableResourceId = this.getResources().getIdentifier(curMerch.getPicLink(), "drawable", this.getContext().getPackageName());
                imgProduct.setImageResource(drawableResourceId);
                imgProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Fragment fragment = new pic_fragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("picLink",curMerch.getPicLink());
                        fragment.setArguments(bundle);
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.frag_container,fragment).commit();
                    }
                });

            }


        }


        //Extract Add, Subtract imageviews and Cancel, AddToCart buttons
        ImageView Add = (ImageView)view.findViewById(R.id.imgAdd);
        ImageView Subtract = (ImageView)view.findViewById(R.id.imgSubtract);
        Button Cancel = (Button)view.findViewById(R.id.btnpCancel);
        Button AddToCart = (Button)view.findViewById(R.id.btnpAddToCart);


        //create onclick listeners for extracted views

        //Cancel button
        Cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //Return to products fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frag_container,getFragmentManager().findFragmentByTag("home_frag"));
                ft.addToBackStack(null);
                ft.commit();

            }
        });



        //Add to cart button
        AddToCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //create bundle for values and switch to current_order_frag
                //record product name, unit price, quantity, total cost
                FragmentTransaction ft = getFragmentManager().beginTransaction();


                //current user
                User curUser = ((MainActivity)getActivity()).getCurUser();

                //check if the product selected is a supplement or merchandise item
                if(curUser==null){
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frag_container,new login_fragment()).commit();
                    Toast.makeText(getContext(),"Log in first",Toast.LENGTH_SHORT).show();

                }
                else {
                    //Supplement
                    if (Prod_bundle.get("curSup") != null) {

                      Supplement  newSup = (Supplement) Prod_bundle.get("curSup");
                        //create new transaction to record the quantity selected
                        assert newSup != null;
                        Transaction transaction = new Transaction(newSup.getID(), curUser.getID(), Integer.parseInt(Quantity.getText().toString()), 0, "supp", getDate());
                        MainActivity Main = (MainActivity) getActivity();
                        Main.getCurrrent_Order().add(transaction);
                        Order_bundle.putSerializable("newSup", newSup);
                    }

                    //Merchandise
                    else {
                        Merchandise newMerch = (Merchandise) Prod_bundle.get("curMerch");
                        //create new transaction to record the quantity selected
                        assert newMerch != null;
                        Transaction transaction = new Transaction(newMerch.getID(), curUser.getID(), Integer.parseInt(Quantity.getText().toString()), 0, "merch", getDate());
                        MainActivity Main = (MainActivity) getActivity();
                        Main.getCurrrent_Order().add(transaction);
                        Order_bundle.putSerializable("newMerch", newMerch);

                    }


                    cur_order_frag.setArguments(Order_bundle);
                    ft.replace(R.id.frag_container, cur_order_frag);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });


        //Subtract imageview
        Subtract.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Reduce the quantity selected
                Integer cur_quantity = Integer.parseInt(Quantity.getText().toString());

                if (cur_quantity > 1)
                {
                    cur_quantity = cur_quantity -1;
                }

                Quantity.setText(String.valueOf(cur_quantity));

                //Update the total price textview
                Double unit_price = Double.parseDouble(UnitPrice.getText().toString());
                Double total = unit_price* cur_quantity;
                TotalPrice.setText(String.valueOf(total));

            }
        });

        //Add imageview
        Add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //Increase the quantity selected
                Integer cur_quantity = Integer.parseInt(Quantity.getText().toString());
                Integer onHand = Integer.parseInt(StockOnHand.getText().toString());

                if (cur_quantity < onHand)
                {
                    cur_quantity = cur_quantity + 1;
                }

                Quantity.setText(String.valueOf(cur_quantity));

                //Update the total price textview
                Double unit_price = Double.parseDouble(UnitPrice.getText().toString());
                Double total = unit_price* cur_quantity;
                TotalPrice.setText(String.valueOf(total));

            }
        });

        spinFlavCol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //flavour selected
                String flavSelected = spinFlavCol.getSelectedItem().toString();

                //display stock on hand for selected flavour
                Supplement curSupp = (Supplement)Prod_bundle.get("curSup");
                String pID = curSupp.getID();

                for (Supplement s : ((MainActivity)getActivity()).getSupplements())
                {
                    if (s.getID().equals(pID) && s.getFlavour().equals(flavSelected))
                    {
                        StockOnHand.setText(String.valueOf(s.getOnHandSup()));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
    private String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        return dateFormat.format(c.getTime());
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
