package com.food.hungerbite.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.LinearLayout.HORIZONTAL;


public class RestaurantFragment extends Fragment {

    RecyclerView recyclerView;

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVFish;
    private RestaurantRecycler mAdapter;
    ArrayAdapter<String> ad;
    List<Restaurant> data;
ArrayList<String> nei;
ListView lv;
    View view ;
    String t;
     SearchView searchView;
    int c;
TextView tto,tvtotal;
    String Loc;
    SearchView sv;

    String URL_PRODUCTS="http://hungerbite.com/hungerbite_app/abc.php";
    RecyclerView.Adapter recyclerViewadapter;
ArrayList<String> ar;
    RequestQueue requestQueue;
    AutoCompleteTextView lo;
    StringRequest stringRequest;


    public static RestaurantFragment newInstance(String param1, String param2) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putString("bjjh", param1);
        args.putString("bhbh", param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Loc = getArguments().getString("Lpk");
            ar = getArguments().getStringArrayList("nm");
           // tto.setText(""+c);

            //mParam2 = getArguments().getString(ARG_PARAM2);

        }
       // Toast.makeText(getActivity(), Loc, Toast.LENGTH_LONG).show();
    }





    void retrieveRestaurants(){
        //pd.show();
        requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS
        , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getActivity(),"Response: "+response,Toast.LENGTH_LONG).show();
                data = new ArrayList<>();


                try{

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        // accessing each item in the array
                        String r1 = json_data.optString("name");
                        String r2 = json_data.optString("res_address");
                        String r3 = json_data.optString("city");
                        String r4 = json_data.optString("minimum_order");
                        String r5 = json_data.optString("logo");
                        String r6 = json_data.optString("locid");
                        String r7 = json_data.optString("restid");
                        String r8 = json_data.optString("time");
                        String r9 = json_data.optString("del");
                        String r10 = json_data.optString("background");

                        Restaurant fishData = new Restaurant(r1, r2, r3, r4, r5 ,r6,r7 ,r8,r9, r10);

                        data.add(fishData);
                    }

                    // Setup and Handover data to recyclerview

                    mAdapter = new RestaurantRecycler(getActivity(), data, lo.getText().toString().toLowerCase().trim());

                    mRVFish.setAdapter(mAdapter);
                    t = ""+mAdapter.getItemCount();
                    tvtotal.setText(""+t +" "+"Restaurants");


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("location",lo.getText().toString().toLowerCase().trim());
                return map;
            }
        }
        ;
        requestQueue.add(stringRequest);
    }
    void retriveLocation(){
        requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest = new StringRequest(Request.Method.POST, "http://hungerbite.com/hungerbite_app/searchview.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equalsIgnoreCase("No data found")) {
                    Toast.makeText(getActivity(), "Login Failed " + response, Toast.LENGTH_LONG).show();

                } else {

                    // Toast.makeText(getActivity(), "Response: " + response, Toast.LENGTH_LONG).show();

                    System.out.println(response);
                    JSONArray JA = null;
                    try {
                        JA = new JSONArray(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject json = null;
                    final String[] str1 = new String[JA.length()];

                    for (int i = 0; i < JA.length(); i++) {
                        try {
                            json = JA.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            str1[i] = json.getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    for (int i = 0; i < str1.length; i++) {
                        nei.add(str1[i]);
                    }


                    ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, nei);


                   // lv.setAdapter(ad);
                    }}}

                ,new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }});



        requestQueue.add(stringRequest);
            }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_restaurant, container, false);
            System.out.print("View created");
             searchView=(SearchView) v.findViewById(R.id.sear);
            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.setIconified(false);
                }
            });
            searchView.setQueryHint("Search For Restaurants,Cuisines,etc....");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    processQuery(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    processQuery(newText);

                    return false;
                }
            });
RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl);
rl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        lo.setCursorVisible(true);
        lo.setFocusableInTouchMode(true);
        lo.setInputType(InputType.TYPE_CLASS_TEXT);
        lo.requestFocus(); //to trigger the soft input

    }
});
TextView tvfil = (TextView) v.findViewById(R.id.tvfilter);
/*tvfil.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        new FancyGifDialog.Builder(getActivity())
                .setTitle("Granny eating chocolate dialog box")
                .setMessage("This is a granny eating chocolate dialog box. This library is used to help you easily create fancy gify dialog.")
                .setNegativeBtnText("Cancel")
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Ok")
                .setNegativeBtnBackground("#FFA9A7A8")

                .setGifResource(R.drawable.gif1)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(getActivity(),"Ok",Toast.LENGTH_SHORT).show();
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        Toast.makeText(getActivity(),"Cancel",Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

    }
});*/
            mRVFish = (RecyclerView) v.findViewById(R.id.recycle1);
            tvtotal = (TextView) v.findViewById(R.id.total);
          //  lv = (ListView) v.findViewById(R.id.lvi);
            nei = new ArrayList<String>();
            mRVFish.setHasFixedSize(true);
            mRVFish.setLayoutManager(new LinearLayoutManager(getActivity()));
            DividerItemDecoration itemDecor = new DividerItemDecoration(getActivity(), HORIZONTAL);
            mRVFish.addItemDecoration(itemDecor);
            lo = (AutoCompleteTextView) v.findViewById(R.id.loct);
            lo.setText(Loc);
            lo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)

                {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    retrieveRestaurants();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
             ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, ar);
           // Toast.makeText(getActivity(),""+c,Toast.LENGTH_LONG).show();

//tto.setText(c);
            lo.setThreshold(1);
            lo.setAdapter(ad);
            retrieveRestaurants();
            if (lo.hasFocus() == true) {
                retrieveRestaurants();
            }


            return v;
        }
    private void processQuery(String query) {
        // in real app you'd have it instantiated just once
        List<Restaurant> result = new ArrayList<>();

        // case insensitive search
        for (Restaurant country : data) {
            if (country.getRestname().toLowerCase().contains(query)) {
                result.add(country);
            }
            else if (country.getRestcityname().toLowerCase().contains(query)){
                result.add(country);

            }}

        mAdapter.setRestaurantList(result);
    }

}








