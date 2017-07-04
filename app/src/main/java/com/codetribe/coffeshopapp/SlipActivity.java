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
import android.widget.Toast;

import java.util.ArrayList;

public class SlipActivity extends AppCompatActivity {

    private ListView lvReciet;
    private Button btnOk;

    private ArrayList<String> dataFromMain=new ArrayList<>();
    private ArrayList<String> clearAll = new ArrayList<>();
    private ArrayList<String> clearList = new ArrayList<>();
    private ArrayList<String> clearItem = new ArrayList<>();

    private ArrayList<String> coffes=new ArrayList<String>();
    private ArrayList<String> prices=new ArrayList<String>();

    public static final String CLEAR="clear";

    private ArrayAdapter<String> adapter;
    private TextView total;
    private ArrayList<String>   totalFromMain;
    private double tot=0;
    private double price=0;


    private Dialog dialog;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slip);

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

        lvReciet=(ListView)findViewById(R.id.lvReceipt);
        total=(TextView)findViewById(R.id.txtAmtDue);
        btnOk = (Button)findViewById(R.id.button);


        Intent intent= this.getIntent();
        dataFromMain=intent.getStringArrayListExtra(MainActivity.COFFEE);
        totalFromMain=intent.getStringArrayListExtra(MainActivity.PRICE);


        for(int x=0;x<totalFromMain.size();x++)
        {

            price=Double.parseDouble(totalFromMain.get(x));
            tot+=price;


        }


        dataFromMain=intent.getStringArrayListExtra(MainActivity.COFFEE);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataFromMain);
        lvReciet.setAdapter(adapter);



        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataFromMain.removeAll(dataFromMain);
                totalFromMain.removeAll(totalFromMain);
                adapter=new ArrayAdapter<String>(SlipActivity.this,android.R.layout.simple_list_item_1,dataFromMain);
                lvReciet.setAdapter(adapter);
                Intent intent1 = new Intent(SlipActivity.this,MainActivity.class);
                startActivity(intent1);

                Toast.makeText(SlipActivity.this,"Thanks, Call again", Toast.LENGTH_LONG).show();
                //finish();
            }
        });


        lvReciet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                dialog=new Dialog(SlipActivity.this);
                builder=new AlertDialog.Builder(SlipActivity.this);

                builder.setTitle("Remove");
                builder.setIcon(R.drawable.java);
                builder.setMessage("Remove "+dataFromMain.get(position)+"?");

                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dataFromMain.remove(position);

                        clearItem=dataFromMain;
                        adapter=new ArrayAdapter<String>(SlipActivity.this,android.R.layout.simple_list_item_1,clearItem);
                        lvReciet.setAdapter(adapter);

                        price=Double.parseDouble(totalFromMain.get(position));
                        tot-=price;
                        totalFromMain.remove(position);
                        dialog.dismiss();

                        total.setText("R"+Double.valueOf(new DecimalFormat("#.##").format(tot)));
                    }
                });

                dialog=builder.create();
                dialog.show();


            }
        });

        total.setText("R"+Double.valueOf(new DecimalFormat("#.##").format(tot)));
    }
}
