package com.codetribe.coffeshopapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    private ArrayList<String> coffes=new ArrayList<String>();
    private ArrayList<String> prices=new ArrayList<String>();
    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> clearData = new ArrayList<String>();
    private String ordered,totalAmt;
    private ListView lvmain;
    private Dialog dialog;
    private AlertDialog.Builder builder;
    private  double total=0;
    private TextView tvTotal;
    private Button btnDone;
    public ArrayList<String> pricesDeduction= new ArrayList<>();


    public  static final  String COFFEE="coffee";
    public  static final  String PRICE="price";
    private Intent intent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvTotal = (TextView)findViewById(R.id.txtTotalPrice);
        btnDone = (Button)findViewById(R.id.btnSaveOder);
        lvmain=(ListView)findViewById(R.id.lvmain);
        //Coffee
        coffes.add("Latte");
        coffes.add("Mocha Latte");
        coffes.add("Strawberry Latte");
        coffes.add("Cuppaccino");
        coffes.add("Iced Coffee");
        coffes.add("French press");
        coffes.add("Brewed Coffee");
        coffes.add("Espresso");
        coffes.add("Espresso Macchiato");
        coffes.add("Americano");
        coffes.add("Short in the Dark");
        coffes.add("Hot chocolate");
        //Prices
        prices.add("18.43");
        prices.add("16.60");
        prices.add("20.30");
        prices.add("14.30");
        prices.add("10.50");
        prices.add("40.60");
        prices.add("15.30");
        prices.add("20.30");
        prices.add("31.20");
        prices.add("14.36");
        prices.add("24.50");
        prices.add("36.30");

       ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,coffes);
        lvmain.setAdapter(adapter);


        lvmain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                dialog=new Dialog(MainActivity.this);
                builder= new AlertDialog.Builder(MainActivity.this);


                builder.setTitle("Order");
                builder.setIcon(R.drawable.java);
                builder.setMessage(coffes.get(position)+" R"+prices.get(position));


                builder.setNegativeButton("Cancel",null);
                builder.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        double price =Double.parseDouble(prices.get(position));
                        ordered= coffes.get(position)+" R"+prices.get(position);
                        pricesDeduction.add(prices.get(position));
                        data.add(ordered);

                        total+=price;
                        //totalAmt="R"+String.valueOf(total);

                        tvTotal.setText("R"+Double.valueOf(new DecimalFormat("#.##").format(total)));




                    }
                });
                dialog=builder.create();
                dialog.show();


            }
        });



        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvTotal.setText("");

                intent = new Intent(MainActivity.this,SlipActivity.class);
                intent.putStringArrayListExtra(COFFEE,data);
                intent.putStringArrayListExtra(PRICE,pricesDeduction);
               // intent.putExtra(PRICE,String.valueOf(total));
                System.out.println("before intrent is called");
                startActivity(intent);
                System.out.println("|After intent is called");
                total=0;
                data=clearData;
                pricesDeduction=clearData;
            }
        });

    }
}
