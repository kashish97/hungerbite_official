package com.food.hungerbite.app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;


import static android.content.ContentValues.TAG;


public class MenuRecycler extends RecyclerView.Adapter<MenuRecycler.ProductViewHolder1> {


    ContentValues values;
    ContentResolver resolver;
    boolean updateMode;
    private MenuActivity mCtx;
    private List<Menu> menuList;
    int i=0;
    int q=0;
    int quan;
    int pr;


    public MenuRecycler(MenuActivity mCtx, List<Menu> menuList) {
        this.mCtx = mCtx;
        this.menuList = menuList;
    }

    @Override
    public ProductViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_menurecycler, null);
        resolver = mCtx.getContentResolver();
        return new ProductViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder1 holder, int position) {
        resolver = mCtx.getContentResolver();
        final Menu menu = menuList.get(position);
        int veg = Integer.parseInt(menu.getVeg());
        String veg1 = "https://www.image.amazerecipe.com/2016/06/vegetarian-symbol.png";
        String nonveg = "http://www.iec.edu.in/app/webroot/img/Icons/84246.png";
        String drinks = "http://www.eatlogos.com/food_and_drinks/png/vector_orange_drinks_logo.png";
        if (veg == 0) {
            Glide.with(mCtx)
                    .load(veg1)
                    .into(holder.Logo);
        } else if (veg == 1) {
            Glide.with(mCtx)
                    .load(nonveg)
                    .into(holder.Logo);
        } else {
            Glide.with(mCtx)
                    .load(drinks)
                    .into(holder.Logo);
        }
        holder.textViewFdescription.setText(menu.getFooddescription().toString());


// list

        String url = "http://hungerbite.com/admin/uploads/";
        String url2 = menu.getLogo();
        String url3 = url + url2;
        holder.textViewFoodname.setText(menu.getFoodname());
        holder.textViewForiginal.setText(menu.getFpriceoriginal());
        if (String.valueOf(menu.getFpriceoriginal().toString().trim().toLowerCase()).equals(String.valueOf(menu.getFpricediscounted().toString().trim().toLowerCase()))) {
            holder.textViewForiginal.setVisibility(View.INVISIBLE);
            holder.textViewrupee.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewFpricediscou.setPaintFlags(holder.textViewFpricediscou.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

        holder.textViewFpricediscou.setText(menu.getFpricediscounted());
        holder.bcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quan++;
                q=0;
                holder.bdec.setVisibility(View.VISIBLE);
                holder.qty.setVisibility(View.VISIBLE);
                holder.bcart.setVisibility(View.INVISIBLE);
                holder.binc.setVisibility(View.VISIBLE);
                ++q;

                final String s = menu.getFoodname();
                String s1 = menu.getFpricediscounted();
                String s2 = menu.getFpriceoriginal();
                String id = menu.getCity();
                int a = Integer.parseInt(s2);
                int r = a * q;
                pr=pr+a;
                values = new ContentValues();
                values.put(Util.COL_fNAME, s);
                values.put(Util.COL_SplPrice, s1);
                values.put(Util.COL_Price, s2);
                values.put(Util.COL_quantity, q);
                values.put(Util.COL_FID, id);
                values.put(Util.COL_TOTAL, r);
                final String b= menu.getLogo();
                Uri uri = resolver.insert(Util.USER_URI, values);
                //Toast.makeText(MenuActivity.this, " registered successfully with id" + s, Toast.LENGTH_LONG).show();
                (mCtx).showCart(b,quan, pr, s);
                // mCtx.showCart(b);
                holder.binc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quan++;
                        ++q;
                        final String s = menu.getFoodname();
                        String s1 = menu.getFpricediscounted();
                        String s2 = menu.getFpriceoriginal();
                        String id = menu.getCity();
                        int a = Integer.parseInt(s2);
                        int r = a * q;
                        pr=pr+a;
                        values = new ContentValues();
                        values.put(Util.COL_fNAME, s);
                        values.put(Util.COL_SplPrice, s1);
                        values.put(Util.COL_Price, s2);
                        values.put(Util.COL_quantity, q);
                        values.put(Util.COL_FID, id);
                        values.put(Util.COL_TOTAL, r);
                        holder.qty.setText(""+q);
                        String where = Util.COL_FID+" = "+menu.getCity();
                        int i = resolver.update(Util.USER_URI,values,where,null);
                        if(i>0){
                            // Toast.makeText(MenuActivity.this,menu.getFoodname()+ " updated successfully "+i,Toast.LENGTH_LONG).show();
                            (mCtx).showCart(b,quan, pr, s);

                        }else{
                            Toast.makeText(mCtx,menu.getFoodname()+ " could not be updated successfully "+i,Toast.LENGTH_LONG).show();
                        }

                    }
                });

                   /* if (itemHolder.bcart.getText() == "+") {
                        ++q;
                    }
                    else {
                        q=1;
                    }*/
                   holder.qty.setText(""+q);
                   holder.bdec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(q>0){
                            --quan;
                            --q;
                            final String s = menu.getFoodname();
                            String s1 = menu.getFpricediscounted();
                            String s2 = menu.getFpriceoriginal();
                            String id = menu.getCity();
                            int a = Integer.parseInt(s2);
                            int r = a * q;
                            pr=pr-a;
                            values = new ContentValues();
                            values.put(Util.COL_fNAME, s);
                            values.put(Util.COL_SplPrice, s1);
                            values.put(Util.COL_Price, s2);
                            values.put(Util.COL_quantity, q);
                            values.put(Util.COL_FID, id);
                            values.put(Util.COL_TOTAL, r);
                            holder.qty.setText(""+q);
                            String where = Util.COL_FID+" = "+menu.getCity();
                            int i = resolver.update(Util.USER_URI,values,where,null);
                            if(i>0){
                                // Toast.makeText(MenuActivity.this,menu.getFoodname()+ " updated successfully "+i,Toast.LENGTH_LONG).show();
                                (mCtx).showCart(b,quan, pr, s);
                                }else{
                                Toast.makeText(mCtx,menu.getFoodname()+ " could not be updated successfully "+i,Toast.LENGTH_LONG).show();
                            }
                            holder.qty.setText(""+q);
                            if(q==0){
                                holder.bdec.setVisibility(View.INVISIBLE);
                                holder.qty.setVisibility(View.INVISIBLE);
                                holder.bcart.setText("Add");
                                holder.bcart.setVisibility(View.VISIBLE);
                                holder.binc.setVisibility(View.INVISIBLE);
                                String where1 = Util.COL_FID+" = "+menu.getCity();
                                //String where = Util.COL_ID+" = '"+user.getName()+"'";

                                int i1 = resolver.delete(Util.USER_URI,where1,null);

                                if(i1>0){

                                    Toast.makeText(mCtx,menu.getFoodname()+" deleted... ", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(mCtx,menu.getFoodname()+" not deleted... ", Toast.LENGTH_LONG).show();
                                }


                            }

                        }
                    }
                });


                //  String[] projection = {Util.COL_FID,Util.COL_fNAME,Util.COL_Price,Util.COL_SplPrice, Util.COL_quantity, Util.COL_TOTAL};

                // String where1 = Util.COL_FID+" = "+menu.getCity();

                //     Cursor cursor = resolver.query(Util.USER_URI,projection,where1,null,null);


                //   if(cursor!=null){
                //     Toast.makeText(mCtx,"exists", Toast.LENGTH_LONG).show();
                //}



                holder.qty.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {


                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }


        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();    }

    class ProductViewHolder1 extends RecyclerView.ViewHolder {

        private final View rootView;
        TextView textViewFoodname,textViewFpricediscou,textViewForiginal,qty, textViewrupee, category;
        ImageView Logo;
        ExpandableTextView textViewFdescription;
        Button bcart;
        Button bdec,binc;

        ProductViewHolder1(View view) {
            super(view);
            rootView = view;
            textViewFoodname = itemView.findViewById(R.id.textViewFoodname);
            textViewFpricediscou = itemView.findViewById(R.id.textViewFpricediscou);
            textViewForiginal = itemView.findViewById(R.id.textViewForiginal);
            //textViewFdescription = itemView.findViewById(R.id.textViewFdescription);
            textViewrupee = itemView.findViewById(R.id.textView5);
            //category= itemView.findViewById(R.id.category);
            Logo = itemView.findViewById(R.id.imageView4);
            bcart = itemView.findViewById(R.id.buttoncart);
            qty = itemView.findViewById(R.id.textView);
            bdec = itemView.findViewById(R.id.buttondec);
           // btt = itemView.findViewById(R.id.btog);
          textViewFdescription = itemView.findViewById(R.id.textViewFdescription);
            binc = itemView.findViewById(R.id.buttoninc);

        }
    }
}