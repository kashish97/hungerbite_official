package com.food.hungerbite.app;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
     * Created by ishantkumar on 20/07/17.
     */

    public class CartAdapter extends ArrayAdapter<Cart> {

      public   ContentResolver resolver;

        ShowCartActivity context;
        int resource;
        int a;
        ArrayList<Cart> cartList1,cartList2;
        private Button mButtonAdd, mButtonRemove;

        Button bdel,badd;
        public CartAdapter(ShowCartActivity context, int resource, ArrayList<Cart> objects) {
            super(context, resource, objects);

            this.context = context;
            this.resource = resource;
            cartList1 = objects;


            cartList2 = new ArrayList<>();
            cartList2.addAll(cartList1);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = null;


            view = LayoutInflater.from(context).inflate(resource,parent,false);





            final TextView qty=(TextView) view.findViewById(R.id.qt);
            TextView txtName = (TextView)view.findViewById(R.id.tvnamef);

            final TextView txtEmail = (TextView)view.findViewById(R.id.tvpricef);

            bdel = (Button)view.findViewById(R.id.button2);
            badd = (Button)view.findViewById(R.id.button5);

            final Cart cart = cartList1.get(position);
            txtName.setText(cart.getNamef());
            txtEmail.setText(cart.getPricef() +""+"X" + cart.getQuantityf()+ ""+"="+cart.getTotal());

            qty.setText(cart.getQuantityf());
            badd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        a = Integer.parseInt(qty.getText().toString().trim());
                  ++a;
resolver= getContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(com.food.hungerbite.app.Util.COL_fNAME, cart.getNamef());
        values.put(com.food.hungerbite.app.Util.COL_SplPrice, cart.getPrices());

        values.put(com.food.hungerbite.app.Util.COL_Price, cart.getPricef());

        values.put(com.food.hungerbite.app.Util.COL_quantity, a);
        values.put(com.food.hungerbite.app.Util.COL_FID, cart.getIdf());
        int pr = Integer.parseInt(cart.getPricef().toString().trim());
        final int r = pr*a;
        values.put(com.food.hungerbite.app.Util.COL_TOTAL, r);

        String where = Util.COL_FID+" = "+cart.getIdf();
        int i = resolver.update(Util.USER_URI,values,where,null);
        if(i>0){
            qty.setText(""+a);
            txtEmail.setText(cart.getPricef() +""+"X" + a+ ""+"="+r);
            Toast.makeText(context,cart.getNamef()+ " updated successfully "+i,Toast.LENGTH_LONG).show();

            context.updateUser();


        }else{
            Toast.makeText(context,cart.getNamef()+ " could not be updated  "+i,Toast.LENGTH_LONG).show();
        }




    }
});

            final View finalView = view;
            bdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a = Integer.parseInt(qty.getText().toString().trim());
                    --a;

                    resolver= getContext().getContentResolver();

                    ContentValues values = new ContentValues();
                    values.put(com.food.hungerbite.app.Util.COL_fNAME, cart.getNamef());
                    values.put(com.food.hungerbite.app.Util.COL_SplPrice, cart.getPrices());

                    values.put(com.food.hungerbite.app.Util.COL_Price, cart.getPricef());

                    values.put(com.food.hungerbite.app.Util.COL_quantity, a);
                    values.put(com.food.hungerbite.app.Util.COL_FID, cart.getIdf());
                    int pr = Integer.parseInt(cart.getPricef().toString().trim());
                    final int r = pr*a;
                    values.put(com.food.hungerbite.app.Util.COL_TOTAL, r);

                    String where = Util.COL_FID+" = "+cart.getIdf();
                    int i = resolver.update(Util.USER_URI,values,where,null);
                    if(i>0){
                        qty.setText(""+a);
                        txtEmail.setText(cart.getPricef() +""+"X" + a+ ""+"="+r);
                        Toast.makeText(context,cart.getNamef()+ " updated successfully "+i,Toast.LENGTH_LONG).show();

                            context.updateUser();
                            if(a==0){

                                finalView.setVisibility(View.INVISIBLE);

                                String where1 = Util.COL_FID+" = "+cart.getIdf();
                                //String where = Util.COL_ID+" = '"+user.getName()+"'";

                                int i1 = resolver.delete(Util.USER_URI,where1,null);


                            }




                    }else{
                        Toast.makeText(context,cart.getNamef()+ " could not be updated  "+i,Toast.LENGTH_LONG).show();
                    }



                }
            });

            return view;
        }

        public void remove(){

            notifyDataSetChanged();
        }
        public void filter(String str){

            cartList1.clear();

            if(str.length()==0){
                cartList1.addAll(cartList2);
            }else{
                for(int i=0;i<cartList2.size();i++){
                    if(cartList2.get(i).getNamef().toLowerCase().contains(str.toLowerCase())){
                        cartList1.add(cartList2.get(i));
                    }
                }
            }


            notifyDataSetChanged();
        }
    }




