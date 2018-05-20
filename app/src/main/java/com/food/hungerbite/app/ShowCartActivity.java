package com.food.hungerbite.app;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowCartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    ListView listView;
    float a;
    EditText eTxtSearch;
    EditText coupon;
    ContentResolver resolver;
    ArrayList<Cart> userList;
    Button login, couponn;
    String resid;
    int count, minn;
    String locatio;
    CartAdapter adapter;
    Cart cart;
    int pos;
    int fact=0;
    float Total;
    List<Integer> test ;
    TextView t11,t12,t15;
    String paytm;
    double wototl,tot;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    String URL_OTP = "http://hungerbite.com/hungerbite_app/discount.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cart);
        resolver = getContentResolver();
        listView = (ListView) findViewById(R.id.lv1);
        t11 = (TextView) findViewById(R.id.textView11);
        t12 = (TextView) findViewById(R.id.textView12);
        t15 = (TextView) findViewById(R.id.textView15);
        coupon=(EditText) findViewById(R.id.editText3);
        couponn=(Button) findViewById(R.id.button6);

        couponn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");

                String cpu= coupon.getText().toString().toLowerCase().trim();
                if(cpu.equalsIgnoreCase("hunger75")){
                    new MaterialDialog.Builder(ShowCartActivity.this)
                            .title("Enter Your paytm Number")
                            .titleColor(getColor(R.color.black))
                            .backgroundColor(getColor(R.color.white))
                            .icon(getResources().getDrawable(R.drawable.btn_flat_normal))
                            .contentColor(getColor(R.color.black))
                            .inputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                            .input("Enter your Paytm Number Here",null, new MaterialDialog.InputCallback() {
                                @Override
                                public void onInput(MaterialDialog dialog, CharSequence input) {
                                    paytm=input.toString().trim();
                                    //  Toast.makeText(getApplicationContext(), otp, Toast.LENGTH_LONG).show();
                                    verifyOtp();

                                }
                            })
                            .positiveText("Submit")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                }
                            })
                            .show();
                }
            }
        });
        Intent rcv = getIntent();
        String s = rcv.getStringExtra("Gst");
        resid= rcv.getStringExtra("resid");
        locatio=rcv.getStringExtra("loccc");
        final String min= rcv.getStringExtra("min");
        minn= Integer.parseInt(min);
        a = Integer.parseInt(s);
         Button fab = (Button) findViewById(R.id.fab);
         AutoCompleteTextView autoCompleteTextView=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
         autoCompleteTextView.setText(locatio);
        login =(Button) findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(minn>tot){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShowCartActivity.this);

                    // Setting Dialog Title
                    alertDialog.setTitle("Cant place Order");

                    alertDialog.setMessage("Your Total amout is less than minimum amount");
                    // Setting Dialog Message

                    // Setting Icon to Dialog

                    // Setting Positive "Yes" Button
                    alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {

dialog.cancel();                        }
                    });



                    // Showing Alert Message
                    alertDialog.show();
                }
                else {
                    Intent intent = new Intent(ShowCartActivity.this, LoginActivity.class);

                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) userList);
                    args.putString("nettotal", String.valueOf(tot));
                    args.putString("subtotal", String.valueOf(wototl));
                    args.putString("gst", String.valueOf(a));
                    args.putString("count", String.valueOf(count));
                    args.putString("resid", resid);
                    intent.putExtra("BUNDLE", args);


                    //Toast.makeText(getApplicationContext(), "cart" + resid, Toast.LENGTH_LONG).show();

                    startActivity(intent);
                } }
        });

        retrieveUsers();
        listView.setOnItemClickListener(this);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        listView=null;
    }


    void verifyOtp(){
        //pd.show();
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.POST, URL_OTP
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               // Toast.makeText(getApplicationContext(),"Response: "+response,Toast.LENGTH_LONG).show();

                if(response.equalsIgnoreCase("no")){
                    Toast.makeText(getApplicationContext(),"Sorry You cant avail cashback "+response,Toast.LENGTH_LONG).show();

                }
                else{

                    final AlertDialog alertDialog = new AlertDialog.Builder(
                            ShowCartActivity.this).create();
                    alertDialog.setTitle("Cashback Applied");
                    alertDialog.setMessage("Cashback Amount"+Total*0.15);


                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            alertDialog.cancel();
                        }
                    });

                    alertDialog.show();



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
                map.put("phone", paytm);
                return map;
            }}
        ;
        requestQueue.add(stringRequest);
    }


    void retrieveUsers(){
        fact=0;
        String[] projection = {Util.COL_FID,Util.COL_fNAME,Util.COL_Price,Util.COL_SplPrice, Util.COL_quantity, Util.COL_TOTAL};

        Cursor cursor = resolver.query(Util.USER_URI,projection,null,null,null);

        if(cursor!=null){

            userList = new ArrayList<>();

            int id=0; String t="";
            String n="",e="",p="",g="",c="", l="";
            while (cursor.moveToNext()){
                id = cursor.getInt(cursor.getColumnIndex(Util.COL_FID));
                n = cursor.getString(cursor.getColumnIndex(Util.COL_fNAME));
                e = cursor.getString(cursor.getColumnIndex(Util.COL_Price));
                l= cursor.getString(cursor.getColumnIndex(Util.COL_SplPrice));
                p = cursor.getString(cursor.getColumnIndex(Util.COL_quantity));
                t=cursor.getString(cursor.getColumnIndex(Util.COL_TOTAL));


                int b = Integer.parseInt(l);
                int r=Integer.parseInt(p);
                int fc=r*b;
                fact = fact+fc;
                t12.setText(""+fact);
                userList.add(new Cart(id,n,e,l,p, Integer.parseInt(t)));
               Total = fact + fact*a/100;
                t11.setText(""+Total);
            }
            adapter = new CartAdapter(this,R.layout.layout_cart,userList);
            listView.setAdapter(adapter);
            count= listView.getAdapter().getCount();
            gg();

        }
        else {
            Toast.makeText(getApplicationContext(),"nullll",Toast.LENGTH_LONG).show();
        }

    }
void gg(){
    wototl = Double.parseDouble(t12.getText().toString().trim());
    tot    = Double.parseDouble(t11.getText().toString().trim());
    double ggst = tot-wototl;
    t15.setText(""+ggst);
}
    void showUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(cart.getNamef());
        builder.setMessage(cart.toString());
        builder.setPositiveButton("Done",null);
        builder.create().show();
    }


    void askForDeletion(final int id, final String name){
       new FancyGifDialog.Builder(this)
        .setTitle("Delete: "+ name)
        .setMessage("Are you Sure ?")
        .setPositiveBtnText("Delete")
               .setGifResource(R.drawable.gif1)
               .setNegativeBtnText("Cancel")

       .OnPositiveClicked(new FancyGifDialogListener() {
                   @Override
                   public void OnClick() {

                deleteUser(id, name);
            }
        })    .build();

    }
    void updateUser(){

        int item=0;
        int tota=0;
        String[] projection = {Util.COL_FID,Util.COL_fNAME,Util.COL_Price,Util.COL_SplPrice, Util.COL_quantity, Util.COL_TOTAL};
        ContentResolver resolver= getContentResolver();
        Cursor cursor = resolver.query(Util.USER_URI,projection,null,null,null);

        if(cursor!=null) {


            String t = "";
            String p = "";
            while (cursor.moveToNext()) {
                p = cursor.getString(cursor.getColumnIndex(Util.COL_quantity));
                t = cursor.getString(cursor.getColumnIndex(Util.COL_TOTAL));


                int e = Integer.parseInt(p);
                int b = Integer.parseInt(t);
                item= item+e;
                tota= tota+b;

                Total = tota + tota*a/100;
                t11.setText("" + Total);
                t12.setText(""+tota);
gg();
            }

        }
    }

    void deleteUser(final int id, final String name){

        String where = Util.COL_FID+" = "+id;
        //String where = Util.COL_ID+" = '"+user.getName()+"'";

        int i = resolver.delete(Util.USER_URI,where,null);

        if(i>0){
            userList.remove(pos);
            adapter.notifyDataSetChanged();
            Toast.makeText(this,name+" deleted... ", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,name+" not deleted... ", Toast.LENGTH_LONG).show();
        }

    }
    void GSt(){


    }


    void discountP(){


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        cart = userList.get(position);
}




}
