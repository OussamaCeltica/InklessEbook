package com.devs.celtica.inkless;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.devs.celtica.inkless.Activities.Accueil;
import com.devs.celtica.inkless.Activities.SearchBook;
import com.devs.celtica.inkless.Users.Profile;


public class HeaderFrag extends Fragment {

    AppCompatActivity c;
    public HeaderFrag() {
        // Required empty public constructor
    }

    public static HeaderFrag newInstance() {
        HeaderFrag fragment = new HeaderFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        c= (AppCompatActivity) getActivity();
        View v= inflater.inflate(R.layout.fragment_header, container, false);
         final ImageView retout_butt=v.findViewById(R.id.retour);
        retout_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.finish();
            }
        });


        return v;
    }


}
