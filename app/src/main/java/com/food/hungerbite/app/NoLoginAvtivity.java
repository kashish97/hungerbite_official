package com.food.hungerbite.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoLoginAvtivity extends AppCompatActivity {

    Button button;
    String otp;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    EditText ed1,ed2;
    String resid;
    ArrayList<Cart> cart;
    String Total, fact, a, count;
    String URL_LOGIN = "http://hungerbite.com/hungerbite_app/loginwithout.php";
    String URL_OTP = "http://hungerbite.com/hungerbite_app/verifyotp.php";



void verifyOtp(){
    //pd.show();
    requestQueue = Volley.newRequestQueue(this);
    stringRequest = new StringRequest(Request.Method.POST, URL_OTP
            , new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            Toast.makeText(getApplicationContext(),"Response: "+response,Toast.LENGTH_LONG).show();

            if(response.equalsIgnoreCase("no")){
                Toast.makeText(getApplicationContext(),"Otp wrong "+response,Toast.LENGTH_LONG).show();

            }
            else{

                Intent intent = new Intent(NoLoginAvtivity.this, CheckoutFormActivity.class);

                //  intent.putExtra("member", r1);
                intent.putExtra("fname", ed1.getText().toString().trim());
                intent.putExtra("lname", "");
                intent.putExtra("login", ed2.getText().toString().trim());
                intent.putExtra("password", "");

                intent.putExtra("Total", Total);
                intent.putExtra("fact", fact);
                intent.putExtra("a", a);
                intent.putExtra("resid", resid);
                intent.putExtra("count", count);
                intent.putExtra("phone", ed2.getText().toString().trim());

                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)cart);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);


            }

        }


    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(),"Login Error"+error.getMessage(),Toast.LENGTH_LONG).show();

        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String,String> map = new HashMap<>();
            map.put("phone", ed2.getText().toString().trim());
            map.put("otp",otp);
            return map;
        }}
    ;
    requestQueue.add(stringRequest);
}


    void retrieveMenu(){


        //pd.show();
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

             //   Toast.makeText(getApplicationContext(),"Response: "+response,Toast.LENGTH_LONG).show();

                if(response.equalsIgnoreCase("no")){
                    Toast.makeText(getApplicationContext(),"Login Failed "+response,Toast.LENGTH_LONG).show();

                }
                else{




                }

            }




        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Login Error"+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("fname", ed1.getText().toString().trim());
                map.put("mphone",ed2.getText().toString().trim());
                return map;
            }}
        ;
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_login_avtivity);
        button = (Button) findViewById(R.id.button);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        cart = (ArrayList<Cart>) args.getSerializable("ARRAYLIST");
        Total= intent.getStringExtra("nettotal");
        fact= intent.getStringExtra("subtotal" );
        a=intent.getStringExtra("gst");
        resid=intent.getStringExtra("resid");
        count= intent.getStringExtra("count");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               retrieveMenu();
                button.setEnabled(false);
                new MaterialDialog.Builder(NoLoginAvtivity.this)
                        .title("Enter Your OTP")
                        .titleColor(getColor(R.color.black))
                        .backgroundColor(getColor(R.color.white))
                        .icon(getResources().getDrawable(R.drawable.btn_flat_normal))
                        .contentColor(getColor(R.color.black))
                        .inputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        .input("Enter OTP Here",null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
otp=input.toString().trim();
                                Toast.makeText(getApplicationContext(), otp, Toast.LENGTH_LONG).show();
                                verifyOtp();

                            }
                        })
                        .positiveText("Verify Otp")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            }
                        })
                        .show();

            }
        });
    }

}
