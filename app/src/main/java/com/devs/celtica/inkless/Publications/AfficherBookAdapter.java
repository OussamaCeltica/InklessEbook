package com.devs.celtica.inkless.Publications;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AfficherBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    AppCompatActivity c;
    public static ArrayList<Book> books=new  ArrayList<Book>();
    public static int ItemSelected;

    public AfficherBookAdapter(AppCompatActivity c) {
        this.c = c;

    }

    public static class BookView extends RecyclerView.ViewHolder  {

        TextView nom1;
        TextView nom2;
        ImageView photo;
        LinearLayout body;
        public BookView(View v) {
            super(v);
            nom1=(TextView) v.findViewById(R.id.div_book_nom1);
            nom2=(TextView)v.findViewById(R.id.div_book_nom2);
            photo=(ImageView)v.findViewById(R.id.div_book_photo);
            //body=(LinearLayout)v.findViewById(R.id.body);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_book,parent,false);

        BookView vh = new BookView(v);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        ((BookView)holder).nom1.setText(books.get(position).nom1+"");
        ((BookView)holder).nom2.setText(books.get(position).nom2+"");
        //Picasso.get().load(books.get(position).photo).into(((BookView)holder).photo);
        Picasso.get()
                .load(Login.ajax.url+"/"+books.get(position).photo)
                .resize(100,200)
                .placeholder(R.drawable.bg_butt_bleu_fonce)
                .error(R.drawable.bg_inp)
                .into(((BookView)holder).photo);

        ((BookView)holder).photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemSelected=position;
                c.startActivity(new Intent(c,ProfileBook.class));
            }
        });





    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
