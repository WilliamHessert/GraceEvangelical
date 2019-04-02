package com.twoorthree.graceevangelical;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by williamhessert on 12/6/18.
 */

public class HighlightsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.prayer_view, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Weekly Highlights");
        Log.i("AHHH", "Here");
        ListView list = v.findViewById(R.id.aList);
        final ArrayList<Highlight> highlights = new ArrayList<>();
        final HighlightAdapter ad = new HighlightAdapter(getContext(), highlights);
        list.setAdapter(ad);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference().child("Highlights");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    try {
                        String key = dataSnapshot.getKey();
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String week = dataSnapshot.child("week").getValue(String.class);
                        String desc = dataSnapshot.child("desc").getValue(String.class);

                        Highlight hig = new Highlight(key, name, week, desc);
                        highlights.add(hig);
                        ad.notifyDataSetChanged();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    try {
                        String key = dataSnapshot.getKey();
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String week = dataSnapshot.child("week").getValue(String.class);
                        String desc = dataSnapshot.child("desc").getValue(String.class);

                        Highlight hig = new Highlight(key, name, week, desc);

                        for(int i=0; i<highlights.size(); i++) {
                            if(highlights.get(i).getId().equals(key)) {
                                highlights.set(i, hig);
                                ad.notifyDataSetChanged();
                                i = highlights.size();
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    try {
                        String key = dataSnapshot.getKey();

                        for(int i=0; i<highlights.size(); i++) {
                            if(highlights.get(i).getId().equals(key)) {
                                highlights.remove(i);
                                ad.notifyDataSetChanged();
                                i = highlights.size();
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                Highlight h = highlights.get(position);

                builder.setTitle(h.getName());
                builder.setMessage(h.getDesc());
                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
        });

        return v;
    }

}
