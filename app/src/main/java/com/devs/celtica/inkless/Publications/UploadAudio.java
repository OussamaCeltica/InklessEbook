package com.devs.celtica.inkless.Publications;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.devs.celtica.inkless.Activities.Accueil;
import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.R;

public class UploadAudio extends AppCompatActivity {

    AudioUploadAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_audio);
        if (savedInstanceState != null) {
            //region Revenir a au Login ..
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //endregion
        }else {

            //region Configuration de recyclervew ..
            final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.div_upload_audio);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(UploadAudio.this);
            mRecyclerView.setLayoutManager(mLayoutManager);


            // specify an adapter (see also next example)
            mAdapter = new AudioUploadAdapter(UploadAudio.this);

            mRecyclerView.setAdapter(mAdapter);
            ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();
                    AudioUploadAdapter.audios.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            });
            itemTouchHelper.attachToRecyclerView(mRecyclerView);

            int i=0;
            while (i != 6){
                AudioUploadAdapter.audios.add(new AudioForUpload());
                i++;

            }
            mAdapter.notifyDataSetChanged();
            //endregion

            ((TextView)findViewById(R.id.uploadAudio_addPlus)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AudioUploadAdapter.audios.add(new AudioForUpload());
                    mAdapter.notifyItemInserted(mAdapter.getItemCount());
                    mRecyclerView.scrollToPosition(AudioUploadAdapter.audios.size()-1);

                }
            });

        }

    }
}
