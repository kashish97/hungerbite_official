package com.food.hungerbite.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText eemail, epass;
    String s1, resid;
    Error e;
    ArrayList<Cart> cart;
    String Total, fact, a, count;
RequestQueue requestQueue;
StringRequest stringRequest;
Button b4,b3;
String URL_LOGIN = "http://hungerbite.com/hungerbite_app/abe.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        eemail = (EditText) findViewById(R.id.editText);
        epass = (EditText) findViewById(R.id.editText2);
        b4 = (Button) findViewById(R.id.button4);
        b3 = (Button) findViewById(R.id.button3);

       eemail.setText("kashishgup9211@gmail.com");
        epass.setText("kashish9211");

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        cart = (ArrayList<Cart>) args.getSerializable("ARRAYLIST");
       Total= args.getString("nettotal");
        fact= args.getString("subtotal" );
        a=args.getString("gst");
        resid=args.getString("resid");
        count= args.getString("count");
b3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent io = new Intent(LoginActivity.this,RegisterationActivity.class);
        io.putExtra("Total", Total);
        io.putExtra("fact", fact);
        io.putExtra("a", a);
        io.putExtra("resid", resid);
        io.putExtra("count", count);

        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable)cart);
        io.putExtra("BUNDLE",args);
        startActivity(io);
    }
});
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, NoLoginAvtivity.class);
                i.putExtra("Total", Total);
                i.putExtra("fact", fact);
                i.putExtra("a", a);
                i.putExtra("resid", resid);
                i.putExtra("count", count);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)cart);
                i.putExtra("BUNDLE",args);
                startActivity(i);
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

                //Toast.makeText(getApplicationContext(),"Response: "+response,Toast.LENGTH_LONG).show();

if(response.equalsIgnoreCase("no")){
    Toast.makeText(getApplicationContext(),"Login Failed "+response,Toast.LENGTH_LONG).show();

}
else{
    try{

        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json_data = jsonArray.getJSONObject(i);

            String r1 = json_data.optString("memberid");
            String r2 = json_data.optString("FirstName");
            String r3 = json_data.optString("LastName");
            String r4 = json_data.optString("login");
            String r5 = json_data.optString("password");




            Intent intent = new Intent(LoginActivity.this, CheckoutFormActivity.class);

            intent.putExtra("member", r1);
            intent.putExtra("fname", r2);
            intent.putExtra("lname", r3);
            intent.putExtra("login", r4);
            intent.putExtra("password", r5);

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



    }catch (Exception e){
        Toast.makeText(getApplicationContext(),"Login Error "+e.getMessage(),Toast.LENGTH_LONG).show();

        e.printStackTrace();
    }
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
              map.put("username", eemail.getText().toString().trim());
              map.put("password",epass.getText().toString().trim());
                return map;
            }}
        ;
        requestQueue.add(stringRequest);
    }



    @Override
    protected void onResume() {



        super.onResume();
    }

    public void checkLogin(View arg0) {

        // Get text from email and passord field
        final String email = eemail.getText().toString();
        final String password = epass.getText().toString();

        retrieveMenu();

    }


    }