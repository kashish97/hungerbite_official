package com.food.hungerbite.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class CheckoutFormActivity extends AppCompatActivity {
StringRequest stringRequest;
RequestQueue requestQueue;
    ArrayList<Cart> cart;
    String arr[];
    AnimatedCircleLoadingView animatedCircleLoadingView;
String fname1,lname1,address1,box1,city1,house1,mobile1,landline1,bill_loc1,mmbr,fn,ln,tt,sttl,gs,cou,
    fid,foodnme,fprice, fqty,fsplprice,ftotal,login,password, resid;
    CircularProgressButton bcheck;
String URL_LOGIN ="http://hungerbite.com/hungerbite_app/placeorder.php";
//    String URL_LOGIN ="";

    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(65);
                        changePercent(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();

    }

    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bcheck.setProgress(percent);
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_form);
        final EditText fname = (EditText) findViewById(R.id.editText6);
        //final EditText lname = (EditText) findViewById(R.id.editText7);
        final EditText address = (EditText) findViewById(R.id.editText8);
       // final EditText box = (EditText) findViewById(R.id.editText9);
       // final EditText city = (EditText) findViewById(R.id.editText10);
        final EditText house = (EditText) findViewById(R.id.editText11);
        final EditText mobile = (EditText) findViewById(R.id.editText12);
        //final EditText landline = (EditText) findViewById(R.id.editText13);
        //final EditText bill_loc = (EditText) findViewById(R.id.editText14);
        Intent i= getIntent();
        Bundle args = i.getBundleExtra("BUNDLE");
        cart = (ArrayList<Cart>) args.getSerializable("ARRAYLIST");
        mmbr = i.getStringExtra("member");
        fn =  i.getStringExtra("fname");
        ln =  i.getStringExtra("lname");
        tt = i.getStringExtra("Total");
        sttl = i.getStringExtra("fact");
        gs = i.getStringExtra("a");
        resid=i.getStringExtra("resid");
        cou = i.getStringExtra("count");
        login = i.getStringExtra("login");
        password = i.getStringExtra("password");
        landline1=i.getStringExtra("phone");

        if(landline1.equals("abcd")){

        }
        else{
            mobile.setText(landline1);
            mobile.setEnabled(false);
        }


      //  Toast.makeText(getApplicationContext(), cou, Toast.LENGTH_LONG).show();
        System.out.println("bhcdhc"+ cou);
        int count = Integer.valueOf(cou);

        fname.setText(fn+ln);
//lname.setText(ln);


        String cart1= cart.toString();

        JSONArray jarr= null;
        try {
            jarr = new JSONArray(cart1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int k=0; k<count; k++ ){
            try {
                JSONObject jsonObject = jarr.getJSONObject(k);

                fid= jsonObject.optString("id");
                foodnme= jsonObject.optString("namef");

               fprice= jsonObject.optString("pricef");
                fsplprice = jsonObject.optString("prices");
                fqty= jsonObject.optString("quantityf");
                 ftotal= jsonObject.optString("total");


                 arr= new String[]{fid, foodnme, fprice, fsplprice, fqty, ftotal};


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        System.out.println(cart1);







            bcheck = (CircularProgressButton) findViewById(R.id.buttoncheckout);
            bcheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // animatedCircleLoadingView.startIndeterminate();

                    fname1 = fname.getText().toString().trim();
                   // lname1 = lname.getText().toString().trim();
                    address1 = address.getText().toString().trim();
                   // box1 = box.getText().toString().trim();
                   // city1 = city.getText().toString().trim();
                    house1 = house.getText().toString().trim();
                    mobile1 = mobile.getText().toString().trim();
                   // landline1 = landline.getText().toString().trim();
                    //bill_loc1 = bill_loc.getText().toString().trim();


                retrieveMenu();
                    bcheck.startAnimation();
                    startPercentMockThread();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent startActivity = new Intent(CheckoutFormActivity.this, CheckoutActivity.class);
                            startActivity(startActivity);
                            finish();
                        }
                    }, 5000);




            }
            });


        }


    void retrieveMenu(){
        //pd.show();
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




        }








        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(),"Couldn't Place order "+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("subtotalres", sttl);
                map.put("MEMBER", mmbr);
                map.put("nettotal", tt);
                map.put("login", login);
                map.put("password", password);
                map.put("fname", fname1);
                map.put("lname", "");
                map.put("sAddress", address1);
                map.put("box", "");
                map.put("city", "Ludhiana");
                map.put("houseno", house1);
                map.put("mNo", mobile1);
                map.put("lNo", "");
                map.put("billing_location", "");
                map.put("vat", gs);
                map.put("shipcharge", "");
                map.put("count", cou);
                map.put("cart",cart.toString());
               // map.put("food_id", fid);
                //map.put("food_price", fprice);
                //map.put("food_specialprice", fsplprice);
                //map.put("quantity_id", fqty);
                //map.put("total", ftotal);
                map.put("restaurants_id",resid);









                return map;
            }}
        ;
        requestQueue.add(stringRequest);
    }


}
