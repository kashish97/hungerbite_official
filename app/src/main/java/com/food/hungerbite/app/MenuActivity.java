package com.food.hungerbite.app;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {
    RelativeLayout reltt;
    MenuRecycler mAdapter;
    private RecyclerView mRVFish;
    List<Menu> data;
    String[] str1;
    String location, opc, a;
    ImageView imlogo;
    List<String>  arr1;
    TextView textView;
    String backgroundi;
    TextView tvv;
    Button fab;
    String deli, img,locid, minorder, resid, rescity, locname, resname, restime;
    Dialog listDialog;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    boolean appBarExpanded = true;
    Button button;
    int r;
    ContentResolver resolver;
    View view;
    private android.view.Menu collapsedMenu;
    String name;
    TextView t19;
    String URL_PRODUCTS = "http://hungerbite.com/hungerbite_app/menu1.php";
    RequestQueue requestQueue;
    StringRequest stringRequest;
    LinearLayoutManager manager;
    void Helo(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
//        getActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.foodd);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbar.setTitle(resname);
                collapsingToolbar.setContentScrimColor(R.color.black_semi_transparent);
                collapsingToolbar.setStatusBarScrimColor(R.color.black);
                //collapsingToolbar.setCollapsedTitleGravity(Gravity.LEFT);

            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 220){
                    appBarExpanded = true;
                    collapsingToolbar.setTitle(resname);
                    textView.setVisibility(View.INVISIBLE);
                    imlogo.setVisibility(View.INVISIBLE);

                }else{
                    appBarExpanded = false;
                    collapsingToolbar.setTitle("");
                    textView.setText(resname);
                    textView.setVisibility(View.VISIBLE);
                    imlogo.setVisibility(View.VISIBLE);
                    }
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 1)) {

        } else {

        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void showCart(final String gst, final int items, final int total, final String name){
        int ite=0,pri=0;
        button.setVisibility(View.VISIBLE);
        t19.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                button.setVisibility(View.INVISIBLE);
                CoordinatorLayout.LayoutParams params1 = (CoordinatorLayout.LayoutParams) reltt.getLayoutParams();
                params1.setMargins(0,0,0,0);
                reltt.setLayoutParams(params1);
                t19.setVisibility(View.INVISIBLE);
            }
        },  5000);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) reltt.getLayoutParams();
        params.setMargins(0,0,0,145);
        reltt.setLayoutParams(params);
        ite= ite+items;
        pri=pri+total;
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
                button.setText(+item+ "" + "" +" Items "+"\n"+"\u20B9"+ " "+tota);
            }
            }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MenuActivity.this, ShowCartActivity.class);
                intent.putExtra("Gst", gst);
                intent.putExtra("resid", locid);
                intent.putExtra("loccc", location);
                intent.putExtra("min", minorder);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        int i = resolver.delete(Util.USER_URI,null,null);
        }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title 
        menu.add(0, v.getId(), 0, "SMS");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        reltt = (RelativeLayout) findViewById(R.id.relt);
        resolver=getContentResolver();
        int i = resolver.delete(Util.USER_URI,null,null);
        manager= new LinearLayoutManager(MenuActivity.this);
        Helo();
         textView=(TextView) findViewById(R.id.titleee);
        //textView.setText("ggbh");
        ImageView imageView=(ImageView) findViewById(R.id.food);
         imlogo=(ImageView) findViewById(R.id.imagelogo);

        button= (Button) findViewById(R.id.butn);
        listDialog = new Dialog(MenuActivity.this);
        mRVFish = (RecyclerView) findViewById(R.id.recycle2);
        fab = (Button) findViewById(R.id.fab);
        CoordinatorLayout coordinatorLayout=(CoordinatorLayout) findViewById(R.id.abccc);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Intent rcv = getIntent();
        name = rcv.getStringExtra("resn");
        deli = rcv.getStringExtra("del");
        img = rcv.getStringExtra("img");
        locid = rcv.getStringExtra("locid");
        minorder = rcv.getStringExtra("minorder");
        resid = rcv.getStringExtra("resid");
        rescity = rcv.getStringExtra("rescity");
        locname = rcv.getStringExtra("locname");
        resname = rcv.getStringExtra("resname");
        restime = rcv.getStringExtra("restime");
        location=rcv.getStringExtra("locc");
        backgroundi=rcv.getStringExtra("background");
        opc=rcv.getStringExtra("opc");
        if(opc.equalsIgnoreCase("no")){
            mRVFish.setOnClickListener(null);       }
        t19 = findViewById(R.id.textView19);
        textView.setText(resname);
        String urlLogo = img;

        String url1 = "http://hungerbite.com/admin/uploads/";
        String urlLogo2 = url1 + urlLogo;
        String url21 = backgroundi;

        String url31 = url1+url21;
        Glide.with(MenuActivity.this)
                .load(url31)
                .into(imageView);
        retrieveMenu();

        Glide.with(MenuActivity.this)
            .load(urlLogo2)
                .into(imlogo);
}
        void retrieveMenu(){
        requestQueue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                data = new ArrayList<>();
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    str1 = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        String r1 = json_data.optString("resname");
                        String r2 = json_data.optString("resaddress");
                        String r3 = json_data.optString("food_id");
                        String r4 = json_data.optString("logo");
                        String r5 = json_data.optString("location");
                        String r6 = json_data.optString("minimum");
                        String r7 = json_data.optString("category");
                        String r8 = json_data.optString("foodname");
                        String r9 = json_data.optString("fprice1");
                        String r10 = json_data.optString("fprice2");
                        String r11 = json_data.optString("desc");
                        String r12= json_data.optString("veg");
                        String a = r7;
                        String b = a.substring(0,1).toUpperCase();
                        String c =  a.substring(1).toLowerCase();
                        str1[i]=b+c;
                        Menu mdata = new Menu(r1, r2, r3, r4, r5,r6,r7,r8,r9,r10,r11, r12);
                        data.add(mdata);
                    }
                    mRVFish.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(MenuActivity.this, LinearLayoutManager.VERTICAL, false));
                    mRVFish.setAdapter(new MenuRecycler(MenuActivity.this, data));
                    final RecyclerSectionItemDecoration sectionItemDecoration =
                            new RecyclerSectionItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_section_header_height),
                                    true,
                                    getSectionCallback(data));


                    mRVFish.addItemDecoration(sectionItemDecoration);

                    final List<String> arrList = new ArrayList<String>();
                    arr1 = new ArrayList<String>();
                    int cnt= 0;
                    List<String> lenList = new ArrayList<String>();
                    for(int i=0;i<str1.length;i++){
                        for(int j=i+1;j<str1.length;j++){
                            if(str1[i].equals(str1[j])){
                                cnt+=1;
                            }
                        }
                        if(cnt<1){
                            arrList.add(str1[i]);
                        }
                        cnt=0;
                    }
                    for(int k=0;k<arrList.size();k++){
                        System.out.println("Array without Duplicates: "+arrList.get(k));
                        if(arrList.get(k)!="Null"){
                            arr1.add(arrList.get(k));}
                    }
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listDialog.setTitle("Menu");
                            LayoutInflater li = (LayoutInflater) MenuActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = li.inflate(R.layout.layout, null, false);
                            listDialog.setContentView(v);
                            listDialog.setCancelable(true);
                            ListView list1 = (ListView) listDialog.findViewById(R.id.listview1);
                            list1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.layout_list,arr1));
                            list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    tvv = (TextView) view.findViewById(R.id.text1);
                                    listClick(tvv.getText().toString().trim().toLowerCase());
                                    tvv.setTextColor(getResources().getColor(R.color.g));
                                    //collapsingToolbar.setTitle(tvv.getText());
                                }
                            });
                            final Window window=listDialog.getWindow();
                            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                            window.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                            listDialog.show();
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Exception: "+e.getMessage(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                finally {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("resn",name);
                return map;
            }};
        requestQueue.add(stringRequest);
    }
    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final List<Menu> people) {
        return new RecyclerSectionItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
                return position == 0
                        || people.get(position)
                        .getCategory()
                        .charAt(0) != people.get(position - 1)
                        .getCategory()
                        .charAt(0);
            }
            @Override
            public CharSequence getSectionHeader(int position) {
                return people.get(position)
                        .getCategory();
            }
    };
    }


    void listClick(final String category){
      Loop:  for(Menu menu1 : data){
            if(menu1.getCategory().toString().trim().toLowerCase().contains(category)){
                r = data.indexOf(menu1);
//manager.scrollToPositionWithOffset(r, mRVFish.getTop());
                break Loop;
                }
            }
       // String title = ((TextView) mRVFish.findViewHolderForAdapterPosition(mRVFish.getTop()).itemView.findViewById(R.id.category)).getText().toString();
        mRVFish.smoothScrollToPosition(r);

        listDialog.cancel();
      }

      public String title(String title){
  String string= title;
    return string; }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        int i = resolver.delete(Util.USER_URI,null,null);
    }
}