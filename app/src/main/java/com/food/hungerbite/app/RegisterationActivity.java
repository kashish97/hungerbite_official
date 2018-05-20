package com.food.hungerbite.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class RegisterationActivity extends AppCompatActivity {

    String s1, resid;
    Error e;
    ArrayList<Cart> cart;
    String Total, fact, a, count;
    Button register, log_in;
    EditText First_Name, Last_Name, Email, Password ,cPass,eknow,Phone1;
    String F_Name_Holder, L_Name_Holder, EmailHolder, PasswordHolder,cPassHolder,PhoneNoHolder,AnswerHolder;
    String finalResult ;
    String HttpURL = "http://hungerbite.com/hungerbite_app/register.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    //ArrayList<Cart> cart;
    String arr[];
    Button bcheck;
StringRequest stringRequest; RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        //Assign Id'S
        First_Name = (EditText)findViewById(R.id.editTextFname);
        Last_Name = (EditText)findViewById(R.id.editTextlname);
        Email = (EditText)findViewById(R.id.ediemail);
        Password = (EditText)findViewById(R.id.edipass);
        Phone1 = (EditText)findViewById(R.id.editTextphone);
        cPass = (EditText)findViewById(R.id.editTextcpass);
        eknow = (EditText)findViewById(R.id.editTextknow);


        register = (Button)findViewById(R.id.buttonsubmit);
        Intent i= getIntent();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        cart = (ArrayList<Cart>) args.getSerializable("ARRAYLIST");
        Total= args.getString("nettotal");
        fact= args.getString("subtotal" );
        a=args.getString("gst");
        resid=args.getString("resid");
        count= args.getString("count");

        //log_in = (Button)findViewById(R.id.Login);

        //Adding Click Listener on button.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    registerUser();
                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(RegisterationActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });

        /*log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
*/
    }

    public void CheckEditTextIsEmptyOrNot(){

        F_Name_Holder = First_Name.getText().toString();
        L_Name_Holder = Last_Name.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        cPassHolder = cPass.getText().toString();

        PhoneNoHolder = Phone1.getText().toString();
        PasswordHolder = Password.getText().toString();
        AnswerHolder = eknow.getText().toString();






        if(TextUtils.isEmpty(F_Name_Holder) || TextUtils.isEmpty(L_Name_Holder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }


    public void registerUser(){

        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.POST, HttpURL
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               // Toast.makeText(getApplicationContext(),"Response: "+response,Toast.LENGTH_LONG).show();

                if(response.equalsIgnoreCase("false")){
                    Toast.makeText(getApplicationContext(),"Failed "+response,Toast.LENGTH_LONG).show();

                }
                else{

                    Intent intent = new Intent(RegisterationActivity.this, CheckoutFormActivity.class);

                    //  intent.putExtra("member", r1);
                    intent.putExtra("fname",F_Name_Holder);
                    intent.putExtra("lname", L_Name_Holder);
                    intent.putExtra("login", EmailHolder);
                    intent.putExtra("password", PasswordHolder);

                    intent.putExtra("Total", Total);
                    intent.putExtra("fact", fact);
                    intent.putExtra("a", a);
                    intent.putExtra("resid", resid);
                    intent.putExtra("count", count);
                    intent.putExtra("phone", "abcd");

                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST",(Serializable)cart);
                    intent.putExtra("BUNDLE",args);
                    startActivity(intent);

                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Failed"+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("fname",F_Name_Holder);

                map.put("lname",L_Name_Holder);

                map.put("login",EmailHolder);

                map.put("phone_no",PhoneNoHolder);
                map.put("password",PasswordHolder);
                map.put("cpassword",cPassHolder);
                map.put("answer",AnswerHolder);
                return map;
            }}
        ;
        requestQueue.add(stringRequest);

    }




}