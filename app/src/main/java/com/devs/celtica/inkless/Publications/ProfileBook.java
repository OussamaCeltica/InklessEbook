package com.devs.celtica.inkless.Publications;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devs.celtica.inkless.Activities.Accueil;
import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.R;
import com.squareup.picasso.Picasso;

public class ProfileBook extends AppCompatActivity {

    public static Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_book);


        if (savedInstanceState != null) {
            //region Revenir a au Login ..
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //endregion
        }else {

            if(getIntent().getExtras() != null){
                book= Accueil.selectedBook;
                Log.e("rrr","gooood");
            }else {
                book=AfficherBookAdapter.books.get(AfficherBookAdapter.ItemSelected);
            }
            ((TextView)findViewById(R.id.profileBook_writer)).setText(book.auteur.nom);
            ((TextView)findViewById(R.id.profileBook_nom1)).setText(book.nom1);
            ((TextView)findViewById(R.id.profileBook_nom2)).setText(book.nom2);
            Picasso.get()
                    .load(Login.ajax.url+"/"+book.photo)
                    .placeholder(R.drawable.bg_butt_bleu_fonce)
                    .error(R.drawable.bg_inp)
                    .into(((ImageView)findViewById(R.id.profileBook_photo)));

            ((LinearLayout)findViewById(R.id.profileBook_readButt)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ProfileBook.this,OpenPdf.class));

                }
            });
        }
    }
}
