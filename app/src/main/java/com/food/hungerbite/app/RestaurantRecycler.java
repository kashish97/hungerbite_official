package com.food.hungerbite.app;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class RestaurantRecycler extends RecyclerView.Adapter<RestaurantRecycler.ProductViewHolder> {

   private String aa;

   String openclose;


    private Context mCtx;
    private List<Restaurant> restaurantList,restaurantListfiltered;

    public RestaurantRecycler(Context mCtx, List<Restaurant> restaurantList, String aa) {
        this.mCtx = mCtx;
        this.restaurantList = restaurantList;
        this.aa = aa;

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_restau, null);
        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Restaurant restaurant = restaurantList.get(position);
        String url = "http://hungerbite.com/admin/uploads/";
        String url2 = restaurant.getImgurl();
        String url3 = url+url2;


        if(url2!="" || url2!=null) {
            Glide.with(mCtx)
            .load(url3)
            .into(holder.imageView);
}



else if(url2.equals("") || url2.equals(null)) { //loading the image
   // Toast.makeText(mCtx,"else",Toast.LENGTH_LONG).show();
        Glide.with(mCtx)
                .load(url+"pizzadeal.jpg")
                .into(holder.imageView);
    }

        String a = restaurant.getRestname();

       String b = a.substring(0,1).toUpperCase();
       String c =  a.substring(1).toLowerCase();
        String time = restaurant.getTime();
        holder.textViewTitle.setText(b+c);
        String discount=restaurant.getRestlocname().toString().trim();
        holder.textViewShortDesc.setText(discount+ "% Off");

        int dis=Integer.parseInt(discount);
        String l="";
        if(dis<=0){
            l="l";
        }
        if(l.equals("l")){
            holder.textViewShortDesc.setVisibility(View.INVISIBLE);
            holder.imageView2.setVisibility(View.INVISIBLE);}
            else{
            holder.textViewShortDesc.setText(discount+ "% Off");

        }

        if(restaurant.getTime().equals(null) || restaurant.getTime().equals("")) {
            openclose="yes";
            holder.tvtime.setVisibility(View.INVISIBLE);
            holder.tvtime.setText(" open");
    }
        else if(restaurant.getTime().equals("close")){
            holder.tvtime.setText("Closed");
            //holder.itemView.setBackgroundResource(R.color.grey);
            openclose="no";
            }

            else{
            holder.tvtime.setText(restaurant.getTime()+""+" Mins To close");
            openclose="yes";
        }


        holder.tvdelivery1.setText(restaurant.getDelivery());

        holder.textViewRating.setText(restaurant.getRestcityname());
        holder.textViewPrice.setText(String.valueOf(restaurant.getMinorder() )+" "+"Min Order");


        if(openclose.equals("no")){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    return;
                }
            });
        }
        else{

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mCtx,MenuActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                final String name=restaurant.getLocid().toString().trim();
                String a1= restaurant.getDelivery();
                String a2= restaurant.getImgurl();
                String a3=restaurant.getLocid();
                String a4=restaurant.getMinorder();
                String a5= restaurant.getResid();
                String a6= restaurant.getRestcityname();
                String a7=restaurant.getRestlocname();
                String a8=restaurant.getRestname();
                String a9=restaurant.getTime();
                String a91=restaurant.getBackgound();
             //   Toast.makeText(mCtx, a91, Toast.LENGTH_LONG).show();
                i.putExtra("resn", name);
                i.putExtra("del", a1);
                i.putExtra("img", a2);
                i.putExtra("locid", a3);
                i.putExtra("minorder", a4);
                i.putExtra("resid", a5);
                i.putExtra("rescity", a6);
                i.putExtra("locname", a7);
                i.putExtra("resname", a8);
                i.putExtra("restime", a9);
                i.putExtra("locc", aa);
                i.putExtra("background", a91);
                i.putExtra("opc", openclose);
                mCtx.startActivity(i);

            }
        });
    }}




    @Override
    public int getItemCount() {
        return restaurantList.size();

    }
    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    restaurantListfiltered = restaurantList;
                } else {
                    List<Restaurant> filteredList = new ArrayList<>();
                    for (Restaurant row : restaurantList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getRestname().toLowerCase().contains(charString.toLowerCase()) || row.getResid().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    restaurantListfiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = restaurantListfiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                restaurantListfiltered = (ArrayList<Restaurant>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice,tvtime,tvdelivery1;
        ImageView imageView, imageView1, imageView2;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewrname);
            tvdelivery1 = itemView.findViewById(R.id.tvdelivery);

            textViewShortDesc = itemView.findViewById(R.id.textViewrloc);
            tvtime = itemView.findViewById(R.id.textView3);

            textViewRating = itemView.findViewById(R.id.textViewrcity);
            textViewPrice = itemView.findViewById(R.id.textViewrmin);
            imageView1 = itemView.findViewById(R.id.shine);

            imageView2 = itemView.findViewById(R.id.imageView3);

            imageView = itemView.findViewById(R.id.imageViewres);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                    ) {
                imageView.setClipToOutline(true);
            }
            Animation animation = new TranslateAnimation(0, imageView2.getWidth() + imageView1.getWidth(), 0, 0);
            animation.setDuration(550);
            animation.setFillAfter(false);
            animation.getRepeatMode();
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            imageView1.startAnimation(animation);
        }
    }}


